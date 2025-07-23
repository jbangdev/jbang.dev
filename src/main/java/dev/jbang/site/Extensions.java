package dev.jbang.site;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import io.quarkiverse.qute.web.markdown.runtime.MdConverter;
import io.quarkiverse.roq.frontmatter.runtime.model.DocumentPage;
import io.quarkiverse.roq.frontmatter.runtime.model.Page;
import io.quarkiverse.roq.frontmatter.runtime.model.RoqCollection;
import io.quarkus.arc.Arc;
import io.quarkus.qute.TemplateExtension;

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
