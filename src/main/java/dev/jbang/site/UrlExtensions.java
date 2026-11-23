package dev.jbang.site;

import java.nio.file.Path;

import io.quarkiverse.roq.frontmatter.runtime.model.Page;
import io.quarkiverse.roq.frontmatter.runtime.model.RoqUrl;
import io.quarkus.qute.TemplateExtension;

/**
 * Jekyll-style {@code relativize_url} for Roq: makes a link relative to the current page so the
 * generated site works under any base path (artifact.ci, file://, sub-path hosting) with no
 * {@code site.path-prefix} configuration.
 *
 * <pre>
 *   {page.rel('/assets/js/app.js')}   -> ../assets/js/app.js   (on /ide/)
 *   {page.rel(link.url)}              -> external URLs pass through unchanged
 * </pre>
 *
 * Roq emits pages as {@code <path>/index.html} served with a trailing slash, so the page URL is
 * the base directory. Pages served as files (e.g. {@code /404.html}) use their parent directory.
 */
@TemplateExtension
public class UrlExtensions {

    public static String rel(Page page, String target) {
        return relativize(target, page == null || page.url() == null ? "/" : page.url().path());
    }

    public static String rel(Page page, RoqUrl target) {
        if (target == null) {
            return null;
        }
        if (target.isExternal()) {
            return target.toString();
        }
        return rel(page, target.path());
    }

    /** For values coming from data files (JsonObject), e.g. {@code page.rel(link.url)}. */
    public static String rel(Page page, Object target) {
        if (target == null) {
            return null;
        }
        if (target instanceof RoqUrl url) {
            return rel(page, url);
        }
        return rel(page, target.toString());
    }

    static String relativize(String target, String fromUrl) {
        if (target == null || isExternalOrAnchor(target) || !target.startsWith("/")) {
            return target;
        }
        String base = baseDir(fromUrl);
        // split off ?query / #fragment so they survive relativization
        int cut = indexOfAny(target, '?', '#');
        String path = cut < 0 ? target : target.substring(0, cut);
        String suffix = cut < 0 ? "" : target.substring(cut);

        String r = Path.of(base).relativize(Path.of(path)).toString().replace('\\', '/');
        if (r.isEmpty()) {
            r = ".";
        }
        if ((path.endsWith("/")) && !r.endsWith("/")) {
            r += "/";
        }
        return r + suffix;
    }

    /** Directory that the browser resolves relative links against, without trailing slash. */
    private static String baseDir(String fromUrl) {
        String from = (fromUrl == null || fromUrl.isEmpty()) ? "/" : fromUrl;
        int q = indexOfAny(from, '?', '#');
        if (q >= 0) {
            from = from.substring(0, q);
        }
        if (!from.endsWith("/")) {
            String last = from.substring(from.lastIndexOf('/') + 1);
            if (last.contains(".")) {
                from = from.substring(0, from.lastIndexOf('/') + 1); // file page -> parent dir
            } else {
                from = from + "/";
            }
        }
        return from.length() > 1 ? from.substring(0, from.length() - 1) : from;
    }

    private static int indexOfAny(String s, char a, char b) {
        int i = s.indexOf(a), j = s.indexOf(b);
        return i < 0 ? j : (j < 0 ? i : Math.min(i, j));
    }

    private static boolean isExternalOrAnchor(String s) {
        return s.startsWith("http://") || s.startsWith("https://") || s.startsWith("//")
                || s.startsWith("mailto:") || s.startsWith("tel:") || s.startsWith("data:")
                || s.startsWith("#");
    }
}
