package dev.jbang.site;

import io.quarkiverse.roq.data.runtime.annotations.DataMapping;

import java.util.List;

@DataMapping(value = "testimonials")
public record Testimonial(
        List<Item> list
) {
    public record Item(
            String author,
            String handle,
            String date,
            String url,
            String text
    ) {
    }
}
