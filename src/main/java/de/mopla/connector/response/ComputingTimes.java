package de.mopla.connector.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Optional;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComputingTimes {

    @JsonProperty
    private final Integer loading;

    @JsonProperty
    private final Integer solving;

    @JsonProperty
    private final Integer routing;

    public ComputingTimes(@JsonProperty("loading") Integer loading,
                          @JsonProperty("solving") Integer solving,
                          @JsonProperty("routing") Integer routing) {
        this.loading = loading;
        this.solving = solving;
        this.routing = routing;
    }

    public Integer getLoading() {
        return Optional.ofNullable(loading).orElse(0);
    }

    public Integer getSolving() {
        return Optional.ofNullable(solving).orElse(0);
    }

    public Integer getRouting() {
        return Optional.ofNullable(routing).orElse(0);
    }

    public Integer getTotal() {
        return getLoading() + getSolving() + getRouting();
    }
}
