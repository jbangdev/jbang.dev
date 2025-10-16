package dev.jbang.site;

import io.quarkiverse.roq.data.runtime.annotations.DataMapping;
import java.util.List;

@DataMapping(value = "trysamples")
public record TrySamples(
        List<Item> items
) {
    public record Item(
            String value,
            String label,
            Badge badge,
            String category,
            List<SubItem> items,
            List<Group> groups
    ) {
    }

    public record SubItem(
            String value,
            String label,
            Badge badge
    ) {
    }

    public record Group(
            String group,
            List<SubItem> items
    ) {
    }

    public record Badge(
            String text,
            String variant,
            Boolean pill
    ) {
    }
}


