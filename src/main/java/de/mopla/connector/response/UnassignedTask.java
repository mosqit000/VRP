package de.mopla.connector.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UnassignedTask(@JsonProperty("id") Integer id,
                             @JsonProperty("location") List<Double> location,
                             @JsonProperty("type") String type,
                             @JsonProperty("description") String description) {

}
