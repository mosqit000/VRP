package de.mopla.connector.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

    @JsonProperty private final Integer vehicle;
    @JsonProperty private final List<Step> steps;
    @JsonProperty private final Integer cost;
    @JsonProperty private final Integer setup;
    @JsonProperty private final Integer duration;
    @JsonProperty("waiting_time") private final Integer waitingTime;
    @JsonProperty private final Integer priority;
    @JsonProperty private final List<Violation> violations;
    @JsonProperty private final String description;
    @JsonProperty private final Integer distance; // optional


    @JsonCreator
    public Route(@JsonProperty("vehicle") Integer vehicle,
                 @JsonProperty("steps") List<Step> steps,
                 @JsonProperty("cost") Integer cost,
                 @JsonProperty("setup") Integer setup,
                 @JsonProperty("duration") Integer duration,
                 @JsonProperty("waiting_time") Integer waitingTime,
                 @JsonProperty("distance") Integer distance,
                 @JsonProperty("priority") Integer priority,
                 @JsonProperty("description") String description,
                 @JsonProperty("violations") List<Violation> violations) {
        this.vehicle = vehicle;
        this.steps = steps;
        this.cost = cost;
        this.setup = setup;
        this.duration = duration;
        this.waitingTime = waitingTime;
        this.priority = priority;
        this.violations = violations;
        this.distance = distance;
        this.description = description;
    }

    public Integer getVehicle() {
        return vehicle;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getSetup() {
        return setup;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public Integer getDistance() {
        return distance;
    }

    public String getDescription() {
        return description;
    }
}
