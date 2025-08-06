package dev.jbang.site;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.logging.Log;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
@Named("twitterClient")
public class TwitterClient {

    private static final String BASE_OEMBED_URL = "https://publish.twitter.com/oembed";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // Simple in-memory cache using only the tweet URL as key
    private final ConcurrentHashMap<String, String> cache = new ConcurrentHashMap<>();

    /**
     * Fetch embedded tweet HTML.
     *
     * @param tweetUrl Full tweet URL
     * @param useCache Enable or disable caching
     * @param maxWidth Optional max width (e.g., 550)
     * @param align    Optional alignment ("left", "center", "right", "none")
     * @return HTML string for embedding
     * @throws IOException On network or parsing error
     */
    public String getTweetEmbedHtml(String tweetUrl, boolean useCache, Integer maxWidth, String align) throws IOException {
        if (useCache && cache.containsKey(tweetUrl)) {
            return cache.get(tweetUrl);
        }

        int maxAttempts = 3;
        long waitTimeMs = 1000; // 1s start

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            try(HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(buildRequestUrl(tweetUrl, maxWidth, align)))
                        .timeout(Duration.ofSeconds(10))
                        .GET()
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    JsonNode json = MAPPER.readTree(response.body());
                    String html = Optional.ofNullable(json.get("html"))
                            .map(JsonNode::asText)
                            .orElseThrow(() -> new IOException("Missing 'html' in oEmbed response"));

                    if (useCache) {
                        cache.put(tweetUrl, html);
                    }

                    return html;
                } else {
                    throw new IOException("Failed to fetch tweet (HTTP " + response.statusCode() + ")");
                }

            } catch (IOException | InterruptedException e) {
                if (attempt == maxAttempts) {
                    Log.warn("Failed after " + maxAttempts + " attempts: " + e.getMessage(), e);
                    return "<p>Failed to fetch tweet</p>";
                }
                try {
                    Thread.sleep(waitTimeMs);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted during backoff", ie);
                }
                waitTimeMs *= 2; // Exponential backoff
            }
        }

        return "<p>Failed to fetch tweet</p>";
    }

    public void clearCache() {
        cache.clear();
    }

    private String buildRequestUrl(String tweetUrl, Integer maxWidth, String align) {
        StringBuilder sb = new StringBuilder(BASE_OEMBED_URL);
        sb.append("?url=").append(URLEncoder.encode(tweetUrl, StandardCharsets.UTF_8));

        if (maxWidth != null) {
            sb.append("&maxwidth=").append(maxWidth);
        }

        if (align != null && !align.isBlank()) {
            sb.append("&align=").append(URLEncoder.encode(align, StandardCharsets.UTF_8));
        }

        sb.append("&dnt=true&hideConversation=on&hide_thread=true");
        return sb.toString();
    }
}
