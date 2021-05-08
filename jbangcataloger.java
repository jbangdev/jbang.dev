///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.5.0
//DEPS org.kohsuke:github-api:1.128
//DEPS com.google.code.gson:gson:2.8.6

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import org.kohsuke.github.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

/**
 * To run this script, set two environment variables GH_USER and GH_TOKEN. These are used to avoid github rate limiting.
 */
@Command(name = "jbangcataloger", mixinStandardHelpOptions = true, version = "jbangcataloger 0.1",
        description = "jbangcataloger made with jbang")
class jbangcataloger implements Callable<Integer> {

  @Option(names = {"-d", "--destDir"}, defaultValue = "./assets/data/", description = "Destination dir to generate cataloger")
  private Path destinationDir;

  @Option(names = {"-ghUser", "--githubUser"}, description = "Github user", defaultValue="${env:GITHUB_USER}")
  private String ghUser;

  @Option(names = {"-ghToken", "--githubToken"}, description = "Github user token", defaultValue="${env:GITHUB_TOKEN}")
  private String ghToken;

  public static void main(String... args) {
    var exitCode = new CommandLine(new jbangcataloger()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    var gitHub = GitHub.connect(ghUser,ghToken);
    System.out.println(gitHub.getRateLimit().toString());
    var gson = new Gson();
    List<CatalogerItem> catalogerItems = new ArrayList<>();
    PagedSearchIterable<GHContent> ghContents = gitHub.searchContent().filename("jbang-catalog.json").extension(".json").list().withPageSize(500);
    for (GHContent content: ghContents) {
      System.out.println("Processing - " + content.getOwner().getFullName());
      var catalogContent = toJsonElement(gson, content);
      if(catalogContent != null) {
        catalogContent.aliases
                .entrySet().stream().map(entry -> toCatalogerItem(entry, content))
                .forEach(catalogerItems::add);
      }
    }
    List<CatalogerItem> sorted = catalogerItems.stream().sorted(Comparator.comparing(catalogerItem -> catalogerItem.repoOwner)).collect(Collectors.toList());
    var cataloger = new Cataloger(sorted);
    String catalogerContent = gson.toJson(cataloger);
    destinationDir.toFile().mkdirs();
    var path = destinationDir.resolve("jbang-cataloger.json");
    Files.writeString(path, catalogerContent);
    System.out.println("Generated file - " + path);
    return 0;
  }

  private CatalogerItem toCatalogerItem(Map.Entry<String, Alias> entry, GHContent ghContent){
    var item = new CatalogerItem();
    item.alias = entry.getKey();
    item.scriptRef = entry.getValue().scriptRef;
    item.description = entry.getValue().description;
    item.repoOwner = ghContent.getOwner().getOwnerName();
    item.repoName =  ghContent.getOwner().getName();
    item.command = item.alias + "@" + item.repoOwner + (item.repoName.equalsIgnoreCase("jbang-catalog") ? "" : "/".concat(item.repoName));
    item.link =  ghContent.getOwner().getHtmlUrl().toString();
    return item;
  }

  private Catalog toJsonElement(Gson gson, GHContent catalogContent) throws IOException {
    if(catalogContent == null) return null;
    Catalog json = null;
    try (InputStream stream = catalogContent.read(); InputStreamReader streamR = new InputStreamReader(stream)) {
      try {
        json = gson.fromJson(streamR, Catalog.class);
      } catch (JsonParseException e) {
        e.printStackTrace();
        json = null;
      }
    }
    return json;
  }
}

class Catalog {
  public Map<String, Alias> aliases = new HashMap<>();
}
class Alias {
  @SerializedName("script-ref")
  public String scriptRef;
  public String description;
}
class Cataloger {
  public final int itemCount;
  public final String generatedAt = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
  public final List<CatalogerItem> items;

  public Cataloger(List<CatalogerItem> items) {
    this.items = items;
    itemCount = items.size();
  }
}
class CatalogerItem {
  public String repoOwner;
  public String repoName;
  public String alias;
  public String description;
  public String scriptRef;
  public String command;
  public String link;
}
