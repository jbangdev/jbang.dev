///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 11+
import static java.lang.System.*;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class main {

    public static void main(String... args) throws IOException, InterruptedException {
        out.println("Welcome to jbang.dev\n");
        out.println("This is a default jbang application hosted a https://jbang.dev/.");
        out.println("It is not really how jbang.dev is best viewed, but congratulations");
        out.println("on having jbang installed.\n");
        out.println("All the best from jbang.dev and its awesome contributors!\n");
        out.println("https://github.com/jbangdev/jbang/blob/HEAD/CONTRIBUTORS.md\n\n");

        URL u = new URL("https://raw.githubusercontent.com/jbangdev/jbang/HEAD/CONTRIBUTORS.md");

        try (Scanner scanner = new Scanner(u.openStream(), StandardCharsets.UTF_8.toString())) {

            
            scanner.useDelimiter("\n");
            scanner.findAll("<td align=\"center\"><a href=\"(.*?)\"><img src=\"(.*)\" width=\".*<b>(.*?)</b>.*<a.*title=\"(.*)\".*</td>")
                    .forEach(x -> {
                        out.println(x.group(3) + " for " + x.group(4) + " - " + x.group(1));
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                           //ignore
                        }
         });

         out.println("\n\nThank you all for the contributions!");
         
        
     }
    }
}
