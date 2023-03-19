///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.google.cloud:libraries-bom:26.8.0@pom
//DEPS com.google.cloud:google-cloud-bigquery
//DEPS info.picocli:picocli:4.5.0
//JAVA 17

import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryException;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(name = "bigquery", mixinStandardHelpOptions = true, version = "bigquery 0.1",
        description = "bigquery made with jbang")
class statsquery implements Callable<Integer> {

    @CommandLine.Option(names={"out"},defaultValue = "assets/data/jbang-versionchecks.csv")
    Path out;

    public static void main(String... args) {
        int exitCode = new CommandLine(new statsquery()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception { // your business logic goes here...
        // TODO(developer): Replace these variables before running the sample.
        String query = """
                SELECT cfv.latitude as lat, cfv.longitude as lng, count(*) as count
                         FROM `jbangdev.jbangdevlogs.7a33f69e_2225_4fc5_b99e_1396988b6e8e`,
                                UNNEST(metadata) m,
                                UNNEST(m.request) req,
                                UNNEST(req.cf) cfv,
                                UNNEST(req.headers) h
                                WHERE DATE(timestamp) >= "2022-07-01"
                                AND req.url = "https://www.jbang.dev/releases/latest/download/version.txt"
                                GROUP BY lat, lng
                                LIMIT 10000
                                """;

        query(query, out, "lat", "lng", "count");
        return 0;
    }

    record latlong( String  latitude, String  longitude) {};

    public void query(String query, Path file, String... columns) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            System.out.println("Building and running query...");
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

            TableResult results = bigquery.query(queryConfig);

            Map<latlong, Long> hits = new HashMap<>();

            for (FieldValueList row : results.iterateAll()) {
                var key = new latlong(row.get("lat").getNumericValue().setScale(0, RoundingMode.HALF_DOWN).toString(), row.get("lng").getNumericValue().setScale(0,RoundingMode.HALF_DOWN).toString());
                Long count = hits.get(key);
                if (count == null) count = 0L;
                System.out.println(row.get("count") + " " + count);
                count += row.get("count").getLongValue();
                hits.put(key, count);
            }
            
            
            try(PrintWriter pw = new PrintWriter(file.toFile())) {
                System.out.println("Writing resultsxx to " + file);
                pw.println("lat,lng,count");
                for (Map.Entry<latlong, Long> entry : hits.entrySet()
                     ) {
                    pw.printf("%s,%s,%s\n", entry.getKey().latitude(),entry.getKey().longitude(),entry.getValue());
                }
            }
        } catch (BigQueryException | InterruptedException | FileNotFoundException e) {
            System.out.println("Query not performed \n" + e.toString());
            e.printStackTrace();
        }
    }

}
