///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.google.cloud:libraries-bom:20.9.0@pom
//DEPS com.google.cloud:google-cloud-bigquery
//DEPS info.picocli:picocli:4.5.0
//JAVA 16

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
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(name = "bigquery", mixinStandardHelpOptions = true, version = "bigquery 0.1",
        description = "bigquery made with jbang")
class bigquery implements Callable<Integer> {

    public static void main(String... args) {
        int exitCode = new CommandLine(new bigquery()).execute(args);
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
                                WHERE DATE(timestamp) >= "2021-07-28"
                                AND req.url = "https://www.jbang.dev/releases/latest/download/version.txt"
                                GROUP BY lat, lng
                                LIMIT 10000
                                """;

        query(query, Path.of("data.csv"), "lat", "lng", "count");
        return 0;
    }

    public void query(String query, Path file, String... columns) {
        try {
            // Initialize client that will be used to send requests. This client only needs to be created
            // once, and can be reused for multiple requests.
            BigQuery bigquery = BigQueryOptions.getDefaultInstance().getService();

            System.out.println("Building and running query...");
            QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).build();

            TableResult results = bigquery.query(queryConfig);

            try(PrintWriter pw = new PrintWriter(file.toFile())) {
                System.out.println("Writing results to " + file);
                pw.println(String.join(",", columns));
                for (FieldValueList row : results.iterateAll()) {
                    pw.println(Arrays.stream(columns).map(col -> row.get(col).getStringValue()).collect(Collectors.joining(",")));

                    //         System.out.println(row.get("lat").getStringValue() + "," + row.get("lng").getStringValue() + "," + row.get("count").getStringValue());
                }
            }
        } catch (BigQueryException | InterruptedException | FileNotFoundException e) {
            System.out.println("Query not performed \n" + e.toString());
        }
    }

}
