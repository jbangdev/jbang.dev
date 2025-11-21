///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 17+
// Update the Quarkus version to what you want here or run jbang with
// `-Dquarkus.version=<version>` to override it.
//DEPS io.quarkus:quarkus-bom:${quarkus.version:3.15.1}@pom
//DEPS io.quarkus:quarkus-picocli
//DEPS io.quarkus:quarkus-rest-client-jackson
//Q:CONFIG quarkus.banner.enabled=false
//Q:CONFIG quarkus.log.level=WARN
//Q:CONFIG quarkus.rest-client.analytics-engine.url=https://api.cloudflare.com/client/v4/accounts/

//Q:CONFIG quarkus.rest-client.logging.scope=request-response
//Q:CONFIG quarkus.rest-client.logging.body-limit=50
// Q:CONFIG quarkus.log.category."org.jboss.resteasy.reactive.client.logging".level=DEBUG
//JAVAC_OPTIONS -parameters


import java.nio.file.Files;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.LinkedHashMap;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;

import io.quarkus.rest.client.reactive.NotBody;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import picocli.CommandLine;

@CommandLine.Command
public class statsquery2 implements Runnable {

     @CommandLine.Option(names={"--out"},defaultValue = "public/assets/data/jbang-versionchecks.csv")
    java.nio.file.Path out;

    @CommandLine.Option(names={"--token"})
    String token;

    @CommandLine.Option(names={"--account"})
    String accountid;

    @RestClient
    private AnalyticsEngine analyticsEngine;

    @CommandLine.Option(names={"--leaderboards"}, defaultValue = "data/leaderboards")
    java.nio.file.Path leaderboards;

    // just aggregating to leaderboard.yaml because qute does not support nested data (yet)
    // see https://github.com/quarkiverse/quarkus-roq/issues/664
    @CommandLine.Option(names={"--leaderboard"}, defaultValue = "data/leaderboard.yaml")
    java.nio.file.Path leaderboard;

    // Helper method to transform version string
    String transformVersion(String version) {
        if (version == null || version.isEmpty()) {
            return "Unknown";
        }
        String[] parts = version.split("\\.");
        if (parts.length == 0) {
            return "Unknown";
        } else if (parts.length == 1) {
            return parts[0] + ".0";
        } else {
            return parts[0] + "." + parts[1];
        }
    }

    void add(Map<String, Long> transformedData, Count count) {
        System.out.println("Adding " + count.name + " " + count.count);
        transformedData.put(count.name, count.count);
    }
    @Override
    public void run() {
       extractGlobeData();

       var result = getLeaderboard("blob12");
       // Transform and recalculate the result
       Map<String, Long> transformedData = new HashMap<>();
       long totalCount = 0;
       
       for (Leaderboard item : result.data) {
           String version = item.name;
           long count = item.count;
           
           // Transform version to x.y format
           String transformedVersion = transformVersion(version);
           
           // Aggregate counts for the same x.y version
           transformedData.merge(transformedVersion, count, Long::sum);
           totalCount += count;
       }
       
       // Create new list of Leaderboard items with transformed data
       List<Leaderboard> newData = transformedData.entrySet().stream()
           .map(entry -> new Leaderboard(entry.getKey(), entry.getValue()))
           .sorted((a, b) -> Long.compare(b.count, a.count))
           .collect(java.util.stream.Collectors.toList());
       
        Map<String, String> rawData = new LinkedHashMap<>();

        result = getLeaderboard("blob3");
       rawData.put("countries", saveLeaderboard(result, leaderboards.resolve("countries.yaml"), code -> {
           try {
               Locale locale = new Locale("", code);
               String countryName = locale.getDisplayCountry(Locale.ENGLISH);
               return countryName.equals(code) ? code : countryName;
           } catch (Exception e) {
               return code;
           }
       }));

       // Create a new AnalyticsResponse with the transformed data
       var result2 = new AnalyticsResponse<Leaderboard>(null,newData,0,0);
       
       rawData.put("java_versions", saveLeaderboard(result2, leaderboards.resolve("java_versions.yaml"), Function.identity()));
      
       Map<String, Long> numbers = new HashMap<>();

       add(numbers,getCount("DISTINCT blob13", "vendors"));
       add(numbers,getCount("DISTINCT blob3", "countries"));
       add(numbers,getCount("DISTINCT blob2", "cities"));
       add(numbers,getCount("DISTINCT blob4", "continents"));
       add(numbers,getCount("DISTINCT blob7", "timezones"));


       add(numbers,getCount("", "uniques"));

       rawData.put("jbang_numbers", saveNumbers(numbers, leaderboards.resolve("jbang_numbers.yaml"), null));
       
       result = getLeaderboard("blob13");
       rawData.put("jbang_vendors", saveLeaderboard(result, leaderboards.resolve("jbang_vendors.yaml"), Function.identity()));
       
       result = getLeaderboard("blob8");
       rawData.put("jbang_versions", saveLeaderboard(result, leaderboards.resolve("jbang_versions.yaml"), Function.identity()));
       
       
       
      // System.out.println(rawData);

      StringBuffer sb = new StringBuffer();
        for (var dp : rawData.entrySet()) {
            sb.append(dp.getKey() + ":\n " + dp.getValue());
        }
        try {
            // Indent the value 2 spaces for each key-value pair
            StringBuilder formatted = new StringBuilder();
            for (var dp : rawData.entrySet()) {
                formatted.append(dp.getKey()).append(":\n  ")
                         .append(dp.getValue().replace("\n", "\n  ").trim())
                         .append("\n");
            }
            Files.writeString(leaderboard, formatted.toString());
        } catch (IOException e) {
            System.err.println("Error writing to " + leaderboard);
            e.printStackTrace();
        }
    }

    
    private String saveLeaderboard(AnalyticsResponse<Leaderboard> result, java.nio.file.Path out, java.util.function.Function<String, String> nameMapper) {
    
        long totalCount = 0;
        for (var dp : result.data) {
            totalCount += dp.count;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("# JBang versions usage data total count: ").append(totalCount).append("\n");
        for (var dp : result.data) {
            String mappedName = nameMapper != null ? nameMapper.apply(dp.name) : dp.name;
            sb.append(String.format("- name: %s%n", mappedName));
            sb.append(String.format(Locale.US, "  percentage: %.1f%n", (double) dp.count / totalCount * 100));
        }
        String content = sb.toString();

        System.out.println("Writing results to " + out);

        
        try {
            Files.writeString(out, content);
        } catch (IOException e) {
            System.err.println("Error writing to " + out);
            e.printStackTrace();
        }
        
        return content;
    }

    private String saveNumbers(Map<String, Long> result, java.nio.file.Path out, java.util.function.Function<String, String> nameMapper) {
    
        StringBuilder sb = new StringBuilder();
        sb.append("# JBang data\n");
        for (var dp : result.entrySet()) {
            sb.append(String.format("  %s: %s\n", dp.getKey(), dp.getValue()));
        }
        String content = sb.toString();

        System.out.println("Writing results to " + out);
        try {
            Files.writeString(out, content);
        } catch (IOException e) {
            System.err.println("Error writing to " + out);
            e.printStackTrace();
        }
        
        return content;
    }

    /// https://github.com/jbangdev/cloudflare-worker-ga4/blob/main/src/analytics.ts#L57
    /// const dataPoint = {
    //     'blobs': [
    //         request.url, // 1
    //         cfProperties.city as string, // 2
    //         cfProperties.country as string, // 3
    //         cfProperties.continent as string, // 4
    //         cfProperties.region as string, // 5
    //         cfProperties.regionCode as string, // 6
    //         cfProperties.timezone as string, // 7
    //         jbangVersion as string, // 8
    //         osName as string, // 9
    //         osVersion as string, // 10
    //         osArch as string, // 11
    //         javaVersion as string, // 12
    //         javaVendor as string, // 13
    //         ip as string, // 14
    //         cfProperties.asOrganization as string, // 15
    //         ua as string // 16
    //     ],
    //     'doubles': [
    //         cfProperties.metroCode as number,
    //         cfProperties.longitude as number,
    //         cfProperties.latitude as number
    //     ],
    //     'indexes': [
    //         index as string
    //     ]
    // };
    private statsquery2.AnalyticsResponse<statsquery2.Leaderboard> getLeaderboard(String column) {
        var result = analyticsEngine.Leaderboard(accountid,
        """
            Select count(DISTINCT format('{}-{}',double2,double3)) as count, $column as name 
            FROM JBANG_METRICS
            WHERE blob1='https://www.jbang.dev/releases/latest/download/version.txt'
            GROUP BY name
            ORDER BY count DESC
        """.replace("$column", column), token);
        System.out.println("Got leaderboard " + column + " with " + result.data.size() + " items");
        return result;
    }

    private statsquery2.Count getCount(String column, String name) {
        var result = analyticsEngine.count(accountid,
        """
            Select count($column) as count, '$name' as name 
            FROM JBANG_METRICS
            WHERE blob1='https://www.jbang.dev/releases/latest/download/version.txt'
            ORDER BY count DESC
        """.replace("$column", column).replace("$name", name), token);
        return result.data.get(0);
    }


    private void extractGlobeData() {
        AnalyticsResponse<DataPoint> result = analyticsEngine.sql(accountid, "Select count() as count, double2 as longitude, double3 as latitude from JBANG_METRICS group by longitude,latitude", token);

           Map<latlong, Long> hits = new HashMap<>();

            for (DataPoint dp : result.data) {
                var key = new latlong(dp.latitude, dp.longitude);
                Long count = hits.get(key);
                if (count == null) count = 0L;
                count += dp.count;
                hits.put(key, count);
            }

            try(PrintWriter pw = new PrintWriter(out.toFile())) {
                    System.out.println("Writing resultsxx to " + out);
                    pw.println("lat,lng,count");
                    for (Map.Entry<latlong, Long> entry : hits.entrySet()
                         ) {
                        pw.printf("%d,%d,%s\n", Math.round(entry.getKey().latitude()), Math.round(entry.getKey().longitude()), entry.getValue());
                    }
            } catch (FileNotFoundException e) {
                System.out.println("Error writing to " + out);
                e.printStackTrace();
            }
    }

    @RegisterRestClient(configKey = "analytics-engine")
    @Path("/{account}/analytics_engine")
    interface AnalyticsEngine {

        @POST
        @Path("/sql")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        @ClientHeaderParam(name = "Authorization", value = "Bearer {token}")
        AnalyticsResponse<DataPoint> sql(@RestPath String account, String query, @NotBody String token);

        @POST
        @Path("/sql")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        @ClientHeaderParam(name = "Authorization", value = "Bearer {token}")
        AnalyticsResponse<Leaderboard> Leaderboard(@RestPath String account, String query, @NotBody String token);
  
        @POST
        @Path("/sql")
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        @ClientHeaderParam(name = "Authorization", value = "Bearer {token}")
        AnalyticsResponse<Count> count(@RestPath String account, String query, @NotBody String token);
  
    }

    static record latlong( double  latitude, double  longitude) {};

    public static record Meta(String name, String type) {}
    public static record Leaderboard(String name, long count) {}
    public static record Count(String name, long count) {}
    public static record DataPoint(long count, double longitude, double latitude) {}
    public static record AnalyticsResponse<DP>(List<Meta> meta, List<DP> data, int rows, int rows_before_limit_at_least) {}

}

