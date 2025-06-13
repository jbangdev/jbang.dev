package dev.jbang.site;

import io.quarkiverse.roq.frontmatter.runtime.model.Page;
import io.quarkus.qute.TemplateExtension;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.List;

@TemplateExtension
public class PageHeader {
    public String overlay_color;
    public String overlay_filter;
    public String overlay_image;
    public String image_description;
    public boolean has_overlay;
    public List<PageAction> actions = new ArrayList<>();

    @Override
    public String toString() {
        return "PageHeader{" +
            "overlay_color='" + overlay_color + '\'' +
            ", overlay_filter='" + overlay_filter + '\'' +
            ", overlay_image='" + overlay_image + '\'' +
            ", image_description='" + image_description + '\'' +
            '}';
    }

    public PageHeader(Page page) {
        var data = page.data();

        image_description = page.title();

        if (data.containsKey("header")) {
            JsonObject header = data.getJsonObject("header");
            overlay_color = header.getString("overlay_color", null);
            overlay_filter = header.getString("overlay_filter", null);
            overlay_image = header.getString("overlay_image", null);
            image_description = header.getString("image_description", image_description); // Keep page title default

            handleOverlayFilter();
            handleActions(header);
        }

        has_overlay = overlay_image != null || overlay_color != null;
    }

    private void handleActions(JsonObject header) {
        JsonArray array = header.getJsonArray("actions", new JsonArray());
        for (int i = 0; i < array.size(); i++) {
            JsonObject action = array.getJsonObject(i);
            actions.add(new PageAction(action.getString("label"), action.getString("url")));
        }
    }

    private void handleOverlayFilter() {
        if (overlay_filter != null) {
            if (overlay_filter.contains("rgba")) {
                overlay_filter = "linear-gradient(" + overlay_filter + ", " + overlay_filter + ")";
            } else {
                var temp = "rgba(0, 0, 0, " + overlay_filter + ")";
                overlay_filter = "linear-gradient(" + temp +", " + temp +")";
            }
        }
    }

    public record PageAction(String label, String url) {

    }
}
