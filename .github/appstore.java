///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS info.picocli:picocli:4.5.0
//DEPS org.kohsuke:github-api:1.128
//DEPS com.google.code.gson:gson:2.8.6
//DEPS org.commonmark:commonmark:0.17.1
//DEPS org.commonmark:commonmark-ext-gfm-tables:0.17.1
//DEPS org.commonmark:commonmark-ext-autolink:0.17.1
//DEPS org.commonmark:commonmark-ext-gfm-strikethrough:0.17.1
//DEPS org.commonmark:commonmark-ext-task-list-items:0.17.1

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.Callable;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.kohsuke.github.*;

import picocli.CommandLine;
import picocli.CommandLine.*;

/**
 * To run this script, set two environment variables GH_USER and GH_TOKEN. These
 * are used to avoid github rate limiting.
 */
@Command(name = "appstore", mixinStandardHelpOptions = true, version = "appstore for JBang 0.1", description = "appstore made with jbang")
class appstore implements Callable<Integer> {

  final static Set<String> excludedCatalogs = new HashSet<>();

  static {
    excludedCatalogs.add("jbangdev/jbang/itests/jbang-catalog.json");
    // excludedCatalogs.add("jbangdev/jbang/src/main/resources/jbang-catalog.json");
    // // todo: treat special to just have it be jbang -t xxx ?
  }

  @Option(names = { "-d",
      "--destDir" }, defaultValue = "./assets/data/", description = "Destination dir to generate cataloger")
  private Path destinationDir;

  @Option(names = {
      "--token" }, description = "Github user token", defaultValue = "${env:GITHUB_TOKEN}", required = true)
  private String ghToken;

  private GitHub gitHub;

  Map<String, Integer> starCache = new HashMap<>();

  public static void main(String... args) {
    var exitCode = new CommandLine(new appstore()).execute(args);
    System.exit(exitCode);
  }

  @Override
  public Integer call() throws Exception {
    gitHub = GitHub.connect("", ghToken);
    System.out.println(gitHub.getRateLimit().toString());
    var gson = new GsonBuilder().setPrettyPrinting().create();
    List<CatalogItem> aliasItems = new ArrayList<>();
    List<CatalogItem> templateItems = new ArrayList<>();
    PagedSearchIterable<GHContent> ghContents = gitHub.searchContent().filename("jbang-catalog.json").extension(".json")
        .list().withPageSize(500);
    for (GHContent content : ghContents) {
      String location = content.getOwner().getFullName() + "/" + content.getPath();
      if (excludedCatalogs.contains(location)) {
        System.out.println("Excluded - " + location);
      } else {
        System.out.println("Processing - " + location);
        var catalogContent = toJsonElement(gson, content);
        if (catalogContent != null) {
          catalogContent.aliases.entrySet().stream().map(entry -> toCatalogerItem(entry, content))
              .forEach(aliasItems::add);

          catalogContent.templates.entrySet().stream().map(entry -> templateToItem(entry, content))
              .forEach(templateItems::add);
        }
      }
    }

    List<CatalogItem> sortedItems = new ArrayList<>();

    sortedItems.addAll(aliasItems);
    sortedItems.addAll(templateItems);
    sortedItems.sort(Comparator.comparing((CatalogItem item) -> item.link )
                    //.thenComparing(catalogerItem -> -catalogerItem.stars)
                    );

    var cataloger = new Cataloger(sortedItems);
    String catalogerContent = gson.toJson(cataloger);
    destinationDir.toFile().mkdirs();
    var path = destinationDir.resolve("jbang-appstore.json");
    Files.writeString(path, catalogerContent);
    System.out.println("Generated file - " + path);
    return 0;
  }

  private void setupGeneralInfo(GHContent ghContent, CatalogItem item) {

    item.repoOwner = ghContent.getOwner().getOwnerName();
    item.repoName = ghContent.getOwner().getName();

    item.link = ghContent.getHtmlUrl().toString();
    try {
      item.icon_url = ghContent.getOwner().getOwner().getAvatarUrl();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // work around https://github.com/hub4j/github-api/issues/1140
    try {
      Integer stars = starCache.get(ghContent.getOwner().getFullName());
      if (stars == null) {
        stars = gitHub.getRepository(ghContent.getOwner().getFullName()).getStargazersCount();
        starCache.put(ghContent.getOwner().getFullName(), stars);
      }
      item.stars = stars;
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private CatalogItem templateToItem(Map.Entry<String, Template> entry, GHContent ghContent) {
    var item = new CatalogItem();
    item.type = "template";
    item.alias = entry.getKey();
    item.scriptRef = null;
    item.description = entry.getValue().description;

    if (item.description != null) {
      item.description = md2html(item.description);
    }

    setupGeneralInfo(ghContent, item);

    StringBuffer cmd = aliasToCommand(ghContent, item.alias, item.repoName, item.repoOwner);

    item.command = cmd.toString();
    item.fullcommand = "jbang init -t " + item.command + " app.java";

    return item;
  }

  
  private CatalogItem toCatalogerItem(Map.Entry<String, Alias> entry, GHContent ghContent) {
    var item = new CatalogItem();
    item.type = "alias";
    item.alias = entry.getKey();
    item.scriptRef = entry.getValue().scriptRef;
    item.description = entry.getValue().description;

    if (item.description != null) {
      item.description = md2html(item.description);
    }

    setupGeneralInfo(ghContent, item);

    StringBuffer cmd = aliasToCommand(ghContent, item.alias, item.repoName, item.repoOwner);

    item.command = cmd.toString();
    item.fullcommand = "jbang init -t " + item.command + " app.java";

    return item;
  }

  private StringBuffer aliasToCommand(GHContent ghContent, String alias, String repoName, String repoOwner) {
    StringBuffer cmd = new StringBuffer(alias);
    if (repoName.equalsIgnoreCase("jbang-catalog")) {
      cmd.append("@" + repoOwner);
    } else {
      cmd.append("@" + repoOwner + "/" + repoName);
    }

    if (!ghContent.getPath().equals("jbang-catalog.json")) {
      cmd.append("~" + ghContent.getPath().substring(0, ghContent.getPath().length() - "/jbang-catalog.json".length()));
    }
    return cmd;
  }

  private String md2html(String markdown) {
    List<Extension> extensions = Arrays.asList(TablesExtension.create(), AutolinkExtension.create(),
        StrikethroughExtension.create(), TaskListItemsExtension.create());
    Parser parser = Parser.builder().extensions(extensions).build();
    var document = parser.parse(markdown);
    HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).sanitizeUrls(true).escapeHtml(true).build();
    var html = renderer.render(document);
    // System.out.println(item.description + "=>" + html);
    return html;
  }

  private Catalog toJsonElement(Gson gson, GHContent catalogContent) throws IOException {
    if (catalogContent == null)
      return null;
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
  public Map<String, Template> templates = new HashMap<>();

  @Override
  public String toString() {

    return aliases.toString() + " - " + templates.toString();

  }
}

class Alias {
  @SerializedName("script-ref")
  public String scriptRef;
  public String description;
}

class Template {
  public String description;
}

class Cataloger {
  public final int aliasCount;
  public final List<CatalogItem> aliases;

  public Cataloger(List<CatalogItem> items) {
    this.aliases = items;
    aliasCount = items.size();
  }
}

class CatalogItem {
  public String url;
  public String type;
  public int stars;
  public String icon_url;
  public String repoOwner;
  public String repoName;
  public String alias;
  public String description;
  public String scriptRef;
  public String command;
  public String fullcommand;
  public String link;
}
