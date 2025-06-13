package dev.jbang.site;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkiverse.roq.data.runtime.annotations.DataMapping;

import java.util.List;

@DataMapping(value="leaderboard")
public record Leaderboard(
        List<Percentage> countries,
        @JsonProperty("java_versions")
        List<Percentage> javaVersions,
        @JsonProperty("jbang_numbers")
        Numbers jbangNumbers,
        @JsonProperty("jbang_vendors")
        List<Percentage> jbangVendors,
        @JsonProperty("jbang_versions")
        List<Percentage> jbangVersions
        ) {
    public record Percentage(
            String name,
            Double percentage
    ) { }
    public record Numbers(
            Long timezones,
            Long cities,
            Long uniques,
            Long countries,
            Long vendors,
            Long continents
    ) { }
}
