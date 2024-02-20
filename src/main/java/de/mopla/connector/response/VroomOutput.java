package de.mopla.connector.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class VroomOutput {

    private final Integer code;
    private final String error;
    private final Summary summary;
    private final List<UnassignedTask> unassigned;
    private final List<Route> routes;

    @JsonCreator
    public VroomOutput(@JsonProperty("code") Integer code,
                       @JsonProperty("error") String error,
                       @JsonProperty("summary") Summary summary,
                       @JsonProperty("unassigned") List<UnassignedTask> unassigned,
                       @JsonProperty("routes") List<Route> routes) {
        this.code = code;
        this.error = error;
        this.summary = summary;
        this.unassigned = unassigned;
        this.routes = routes;
    }

    public Integer getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    public Summary getSummary() {
        return summary;
    }

    public List<UnassignedTask> getUnassigned() {
        return unassigned;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
