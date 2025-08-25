package de.mopla.connector.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.mopla.connector.response.Step;

import java.beans.Transient;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class Vehicle {

    @JsonProperty private final Integer id;
    @JsonProperty private final String description;
    @JsonProperty private final Location start;
    @JsonProperty private final Location end;
    @JsonProperty private final List<Integer> capacity;
    @JsonProperty private final List<Integer> skills;
    @JsonProperty("time_window") private final TimeWindow timeWindow;
    @JsonProperty private final List<Break> breaks;
    @JsonProperty("speed_factor") private final Double speedFactor;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty private final List<Step> steps;



    @JsonCreator
    public Vehicle(@JsonProperty("id") Integer id,
                   @JsonProperty("description") String description,
                   @JsonProperty("start") Double[] start,
                   @JsonProperty("end") Double[] end,
                   @JsonProperty("capacity") List<Integer> capacity,
                   @JsonProperty("skills") List<Integer> skills,
                   @JsonProperty("time_window") Long[] timeWindow,
                   @JsonProperty("breaks") List<Break> breaks,
                   @JsonProperty("speed_factor") Double speedFactor) {
        this(id, description,
            new Location(start[1], start[0]), // careful: vroom takes longitude first
            new Location(end[1], end[0]),     // careful: vroom takes longitude first
            capacity, skills,
            new TimeWindow(timeWindow[0], timeWindow[1]), breaks,
                speedFactor,
                null);
    }

    public Vehicle(Integer id, String description, Location start,
                   Location end, List<Integer> capacity,
                   List<Integer> skills, TimeWindow timeWindow,
                   List<Break> breaks, Double speedFactor) {
        this.id = id;
        this.description = description;
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.skills = skills;
        this.timeWindow = timeWindow;
        this.breaks = breaks;
        this.speedFactor = speedFactor;
        this.steps = List.of();
    }

    public Vehicle(Integer id, String description, Location start,
                        Location end, List<Integer> capacity,
                        List<Integer> skills, TimeWindow timeWindow,
                        List<Break> breaks, Double speedFactor, List<Step> steps) {
        this.id = id;
        this.description = description;
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.skills = skills;
        this.timeWindow = timeWindow;
        this.breaks = breaks;
        this.speedFactor = speedFactor;
        this.steps = steps;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vehicle) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.start, that.start) &&
                Objects.equals(this.end, that.end) &&
                Objects.equals(this.capacity, that.capacity) &&
                Objects.equals(this.skills, that.skills) &&
                Objects.equals(this.timeWindow, that.timeWindow) &&
                Objects.equals(this.breaks, that.breaks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, start, end, capacity, skills, timeWindow, breaks);
    }

    @Override
    public String toString() {
        return "Vehicle[" +
                "id=" + id + ", " +
                "description=" + description + ", " +
                "start=" + start + ", " +
                "end=" + end + ", " +
                "capacity=" + capacity + ", " +
                "skills=" + skills + ", " +
                "timeWindow=" + timeWindow + ", " +
                "breaks=" + breaks + ']';
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
    }

    public List<Integer> getCapacity() {
        return capacity;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public TimeWindow getTimeWindow() {
        return timeWindow;
    }

    public Double getSpeedFactor() {
        return speedFactor;
    }

    @JsonIgnore
    public Instant getStartTime(){
        return Instant.ofEpochSecond(timeWindow.getStart());
    }

    @JsonIgnore
    public Instant getEndTime(){
        return Instant.ofEpochSecond(timeWindow.getEnd());
    }

    public List<Break> getBreaks() {
        return breaks;
    }

    @Transient
    public Vehicle withNewId(int id) {
        return new Vehicle(id, description, start, end, capacity, skills, timeWindow, breaks, speedFactor, steps );
    }

    @Transient
    public Vehicle withNewSteps(List<Step> steps) {
        return new Vehicle(id, description, start, end, capacity, skills, timeWindow, breaks, speedFactor, steps);
    }

    @Transient
    public Long safeTimeWindowStart() {
        return timeWindow.getStart() == null ? 0L : timeWindow.getStart();
    }
}
