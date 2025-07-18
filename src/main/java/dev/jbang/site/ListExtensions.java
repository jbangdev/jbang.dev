package dev.jbang.site;

import io.quarkus.qute.TemplateExtension;

import java.util.List;

@TemplateExtension
public class ListExtensions {
    public static <T> List<T> limit(List<T> list, int limit) {
        if (limit <= list.size()) {
            return list.subList(0, limit);
        } else {
            return list;
        }
    }

    public static <T> List<T> offset(List<T> list, int offset) {
        if (offset <= list.size()) {
            return list.subList(offset, list.size() - 1);
        } else {
            return list;
        }
    }

    public static <T> T random(List<T> list) {
        long index = Math.round(Math.random() * (list.size()-1));

        return list.get((int) index);
    }
}
