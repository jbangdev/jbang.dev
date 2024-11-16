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

     @CommandLine.Option(names={"--out"},defaultValue = "assets/data/jbang-versionchecks.csv")
    java.nio.file.Path out;

    @CommandLine.Option(names={"--token"})
    String token;

    @CommandLine.Option(names={"--account"})
    String accountid;

    @RestClient
    private AnalyticsEngine analyticsEngine;

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
       
       // Create a new AnalyticsResponse with the transformed data
       var result2 = new AnalyticsResponse<Leaderboard>(null,newData,0,0);
       
       saveLeaderboard(result2, Paths.get("_data/leaderboard/java_versions.yaml"), Function.identity());
      
       result = getLeaderboard("blob13");
       saveLeaderboard(result, Paths.get("_data/leaderboard/jbang_vendors.yaml"), Function.identity());
       
       var vendorCount = getCount("DISTINCT blob13", "vendors");
       var countries = getCount("DISTINCT blob3", "countries");
       var uniques = getCount("", "uniques");
       Map<String, Long> numbers = new HashMap<>();
       var d = vendorCount.data.get(0);
       numbers.put(d.name, d.count);
       d = countries.data.get(0);
       numbers.put(d.name, d.count);
       d = uniques.data.get(0);
       numbers.put(d.name, d.count);
       System.out.println(numbers);
       saveNumbers(numbers, Paths.get("_data/leaderboard/jbang_numbers.yaml"), null);
       result = getLeaderboard("blob8");
       saveLeaderboard(result, Paths.get("_data/leaderboard/jbang_versions.yaml"), Function.identity());
       
       result = getLeaderboard("blob3");
       saveLeaderboard(result, Paths.get("_data/leaderboard/countries.yaml"), code -> {
           try {
               Locale locale = new Locale("", code);
               String countryName = locale.getDisplayCountry(Locale.ENGLISH);
               return countryName.equals(code) ? code : countryName;
           } catch (Exception e) {
               return code;
           }
       });
       
    }

    
    private void saveLeaderboard(AnalyticsResponse<Leaderboard> result, java.nio.file.Path out, java.util.function.Function<String, String> nameMapper) {
    
        long totalCount = 0;
        for (var dp : result.data) {
            totalCount += dp.count;
        }

        System.out.println("Writing results to " + out);
        try (PrintWriter pw = new PrintWriter(new FileWriter(out.toFile()))) {
            pw.println("# JBang versions usage data total count: " + totalCount);
            for (var dp : result.data) {
                String mappedName = nameMapper != null ? nameMapper.apply(dp.name) : dp.name;
                pw.printf("- name: %s%n", mappedName);
                pw.printf(Locale.US, "  percentage: %.1f%n", (double) dp.count / totalCount * 100);
            }
        } catch (IOException e) {
            System.err.println("Error writing to " + out);
            e.printStackTrace();
        }
    }

    private void saveNumbers(Map<String, Long> result, java.nio.file.Path out, java.util.function.Function<String, String> nameMapper) {
    

        System.out.println("Writing results to " + out);
        try (PrintWriter pw = new PrintWriter(new FileWriter(out.toFile()))) {
            pw.println("# JBang data");
            for (var dp : result.entrySet()) {
                pw.printf("  %s: %s\n", dp.getKey(), dp.getValue());
            }
        } catch (IOException e) {
            System.err.println("Error writing to " + out);
            e.printStackTrace();
        }
    }

    private statsquery2.AnalyticsResponse<statsquery2.Leaderboard> getLeaderboard(String column) {
        var result = analyticsEngine.Leaderboard(accountid,
        """
            Select count(DISTINCT format('{}-{}',double2,double3)) as count, $column as name 
            FROM JBANG_METRICS
            WHERE blob1='https://www.jbang.dev/releases/latest/download/version.txt'
            GROUP BY name
            ORDER BY count DESC
        """.replace("$column", column), token);
        return result;
    }

    private statsquery2.AnalyticsResponse<statsquery2.Count> getCount(String column, String name) {
        var result = analyticsEngine.count(accountid,
        """
            Select count($column) as count, '$name' as name 
            FROM JBANG_METRICS
            WHERE blob1='https://www.jbang.dev/releases/latest/download/version.txt'
            ORDER BY count DESC
        """.replace("$column", column).replace("$name", name), token);
        return result;
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

