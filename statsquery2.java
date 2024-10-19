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
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestQuery;

import io.quarkus.rest.client.reactive.NotBody;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
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

    @Override
    public void run() {
       var result = analyticsEngine.sql(accountid, "Select count() as count, double2 as longitude, double3 as latitude from JBANG_METRICS group by longitude,latitude", token);

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
        AnalyticsResponse sql(@RestPath String account, String query, @NotBody String token);
    }

    static record latlong( double  latitude, double  longitude) {};

    public static record Meta(String name, String type) {}
    public static record DataPoint(long count, double longitude, double latitude) {}
    public static record AnalyticsResponse(List<Meta> meta, List<DataPoint> data, int rows, int rows_before_limit_at_least) {}

}

