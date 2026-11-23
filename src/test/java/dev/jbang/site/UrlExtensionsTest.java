package dev.jbang.site;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UrlExtensionsTest {

    @ParameterizedTest
    @CsvSource({
            "/assets/js/greedy-nav.js, /, assets/js/greedy-nav.js",
            "/assets/js/greedy-nav.js, /ide/, ../assets/js/greedy-nav.js",
            "/static/bundle/app-X.css, /posts/2024/hello/, ../../../static/bundle/app-X.css",
            "/ide/, /, ide/",
            "/, /ide/, ../",
            "/, /, ./",
            "/try/, /ide/, ../try/",
            "/documentation/guide/latest/index.html, /ide/, ../documentation/guide/latest/index.html",
            "/assets/a.css, /404.html, assets/a.css",
            "/ide/#vscode, /try/, ../ide/#vscode",
            "/download?x=1, /ide/, ../download?x=1",
            "/ide, /appstore, ../ide",
    })
    void relativizesAndResolvesBack(String target, String from, String expected) {
        String rel = UrlExtensions.relativize(target, from);
        assertEquals(expected, rel);
        // a browser resolving the relative link against the page must land on the original target
        String pageUrl = from.endsWith("/") || from.contains(".") ? from : from + "/";
        URI resolved = URI.create("https://h" + pageUrl).resolve(rel);
        String back = resolved.getRawPath()
                + (resolved.getRawQuery() != null ? "?" + resolved.getRawQuery() : "")
                + (resolved.getRawFragment() != null ? "#" + resolved.getRawFragment() : "");
        assertEquals(target, back);
    }

    @ParameterizedTest
    @CsvSource({
            "https://github.com/jbangdev",
            "//cdn.example.com/x.js",
            "mailto:hi@jbang.dev",
            "#top",
            "relative/already.png",
    })
    void passesThroughNonRootAbsolute(String target) {
        assertEquals(target, UrlExtensions.relativize(target, "/ide/"));
    }
}
