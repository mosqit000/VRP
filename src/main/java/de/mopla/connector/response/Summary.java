package de.mopla.connector.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Summary {


    @JsonProperty private final Integer cost;
    @JsonProperty private final Integer routes;
    @JsonProperty private final Integer unassigned;
    @JsonProperty private final Integer setup;
    @JsonProperty private final Integer service;
    @JsonProperty private final Integer duration;
    @JsonProperty("waiting_time") private final Integer waitingTime;
    @JsonProperty private final Integer priority;
    @JsonProperty private final List<Violation> violations;
    @JsonProperty private final Integer[] pickup; // optional
    @JsonProperty private final Integer[] delivery; // optional
    @JsonProperty private final Integer[] amount; // optional
    @JsonProperty private final Integer distance; // optional
    @JsonProperty private final ComputingTimes computingTimes;

    public Summary(@JsonProperty("cost") Integer cost,
                   @JsonProperty("routes") Integer routes,
                   @JsonProperty("unassigned") Integer unassigned,
                   @JsonProperty("setup") Integer setup,
                   @JsonProperty("service") Integer service,
                   @JsonProperty("duration") Integer duration,
                   @JsonProperty("waiting_time") Integer waitingTime,
                   @JsonProperty("priority") Integer priority,
                   @JsonProperty("violations") List<Violation> violations,
                   @JsonProperty("pickup") Integer[] pickup,
                   @JsonProperty("delivery") Integer[] delivery,
                   @JsonProperty("amount") Integer[] amount,
                   @JsonProperty("distance") Integer distance,
                   @JsonProperty("computing_times") ComputingTimes computingTimes) {
        this.cost = cost;
        this.routes = routes;
        this.unassigned = unassigned;
        this.setup = setup;
        this.service = service;
        this.duration = duration;
        this.waitingTime = waitingTime;
        this.priority = priority;
        this.violations = violations;
        this.pickup = pickup;
        this.delivery = delivery;
        this.amount = amount;
        this.distance = distance;
        this.computingTimes = computingTimes;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getRoutes() {
        return routes;
    }

    public Integer getUnassigned() {
        return unassigned;
    }

    public Integer getSetup() {
        return setup;
    }

    public Integer getService() {
        return service;
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

    public ComputingTimes getComputingTimes() { return computingTimes;}
}
