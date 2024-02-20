package de.mopla.connector.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Step {

    @JsonProperty private final String type; // start, job, pickup, delivery, break or end
    @JsonProperty private final Long arrival;
    @JsonProperty private final Integer duration;
    @JsonProperty private final Integer setup;
    @JsonProperty private final Integer service;
    @JsonProperty("waiting_time") private final Integer waitingTime;
    @JsonProperty private final List<Violation> violations;
    @JsonProperty private final String description; // optional
    @JsonProperty private final int distance; // optional
    @JsonProperty private final List<Integer> load; // optional
    @JsonProperty private List<Double> location; // optional
    @JsonProperty private final Integer id; // optional

    @JsonCreator
    public Step(@JsonProperty("type") String type,
                @JsonProperty("arrival") Long arrival,
                @JsonProperty("duration") Integer duration,
                @JsonProperty("setup") Integer setup,
                @JsonProperty("load") List<Integer> load,
                @JsonProperty("service") Integer service,
                @JsonProperty("waiting_time") Integer waitingTime,
                @JsonProperty("violations") List<Violation> violations,
                @JsonProperty("description") String description,
                @JsonProperty("id") Integer id,
                @JsonProperty("distance") int distance,
                @JsonProperty("location") List<Double> location) {
        this.type = type;
        this.arrival = arrival;
        this.duration = duration;
        this.setup = setup;
        this.service = service;
        this.waitingTime = waitingTime;
        this.violations = violations;
        this.description = description;
        this.distance = distance;
        this.load = load;
        this.location = location;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public List<Double> getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public Long getArrival() {
        return arrival;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getSetup() {
        return setup;
    }

    public Integer getService() {
        return service;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public List<Violation> getViolations() {
        return violations;
    }

    public String getDescription() {
        return description;
    }

    public List<Integer> getLoad() {
        return load;
    }

    public int getDistance() {
        return distance;
    }

    public boolean isEnd() {
        return getType().trim().equalsIgnoreCase("end");
    }

    public boolean isStart() {
        return getType().trim().equalsIgnoreCase("start");
    }

    public boolean isBreak() {
        return getType().trim().equalsIgnoreCase("break");
    }

    public boolean isBreakWithPassengers(){
        return getLoad().size() > 0 && getLoad().get(0) > 0 && isBreak();
    }

    public boolean isDelivery() {
        return getType().trim().equalsIgnoreCase("delivery");
    }

    public boolean isPickup() {
        return getType().trim().equalsIgnoreCase("pickup");
    }

    public Instant toLeaveStepTime(){
        return Instant.ofEpochSecond(getArrival() + getWaitingTime() + getService() + getSetup());
    }

    public Instant toArriveAtStepTime(){
        return Instant.ofEpochSecond(getArrival());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return Objects.equals(type, step.type) && Objects.equals(arrival, step.arrival) && Objects.equals(duration, step.duration) && Objects.equals(setup, step.setup) && Objects.equals(service, step.service) && Objects.equals(waitingTime, step.waitingTime) && Objects.equals(violations, step.violations) && Objects.equals(description, step.description) && Objects.equals(distance, step.distance) && Objects.equals(load, step.load) && Objects.equals(location, step.location) && Objects.equals(id, step.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, arrival, duration, setup, service, waitingTime, violations, description, distance, load, location, id);
    }

    public boolean isJob() {
        return type.trim().equalsIgnoreCase("job");
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }
}
