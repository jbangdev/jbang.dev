package dev.jbang.site;

import static io.quarkiverse.roq.frontmatter.runtime.RoqTemplateExtension.numberOfWords;
import static io.quarkiverse.roq.frontmatter.runtime.RoqTemplateExtension.stripHtml;

import io.quarkiverse.qute.web.markdown.runtime.MdConverter;
import io.quarkiverse.roq.frontmatter.runtime.model.DocumentPage;
import io.quarkiverse.roq.frontmatter.runtime.model.Page;
import io.quarkiverse.roq.frontmatter.runtime.model.RoqCollection;
import io.quarkus.arc.Arc;
import io.quarkus.qute.TemplateExtension;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@TemplateExtension
public class Extensions {

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String markdown(String rawText) {
        MdConverter converter = Arc.container().beanInstanceSupplier(MdConverter.class).get().get();
        String html = converter.html(rawText).strip();
        if (html.toLowerCase().startsWith("<p>")) {
            html = html.substring(3);
        }
        if (html.toLowerCase().endsWith("</p>")) {
            html = html.substring(0, html.length() - 4);
        }
        return html;
    }

    public static String wordLimit(String text, int limit) {
        String[] words = text.split("\\s+");
        if (words.length < limit) {
            return text;
        }
        return String.join(" ", Arrays.copyOfRange(words, 0, limit)) + "...";
    }

    public static Map<Integer, List<DocumentPage>> getPostsInYear(RoqCollection posts) {
        TreeMap<Integer, List<DocumentPage>> collect = new TreeMap<>(Comparator.reverseOrder());
        collect.putAll(posts.stream()
                .filter(p -> !p.hidden())
                .collect(Collectors.groupingBy(p -> p.date().getYear())));
        return collect;
    }

    public static PageHeader header(Page page) {
        return new PageHeader(page);
    }
}
