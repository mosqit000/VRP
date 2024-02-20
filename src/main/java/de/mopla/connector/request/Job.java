package de.mopla.connector.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.Transient;
import java.util.List;

public class Job {

    private final int id;
    private final List<Integer> pickup;
    private final List<Integer> delivery;
    private final String description;
    private final Double[] location;
    @JsonProperty("time_windows") private final Long[][] timeWindows;
    private final Integer priority;
    private final List<Integer> skills;

    @JsonCreator
    public Job(@JsonProperty("id") int id,
               @JsonProperty("pickup") List<Integer> pickup,
               @JsonProperty("delivery") List<Integer> delivery,
               @JsonProperty("description") String description,
               @JsonProperty("skills") List<Integer> skills,
               @JsonProperty("location") Double[] location,
               @JsonProperty("time_windows") Long[][] timeWindows,
               @JsonProperty("priority") Integer priority) {
        this.id = id;
        this.pickup = pickup;
        this.delivery = delivery;
        this.description = description;
        this.location = location;
        this.timeWindows = timeWindows;
        this.priority = priority;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getPickup() {
        return pickup;
    }

    public List<Integer> getDelivery() {
        return delivery;
    }

    public String getDescription() {
        return description;
    }

    public Double[] getLocation() {
        return location;
    }

    public Long[][] getTimeWindows() {
        return timeWindows;
    }

    @Transient
    public Long safeFirstTimeWindowStart(){
        if(timeWindows.length < 1) return 0L;
        if(timeWindows[0].length < 1) return 0L;
        return timeWindows[0][0] == null ? 0L : timeWindows[0][0];
    }

    public Integer getPriority() {
        return priority;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    @Transient
    public Job withNewId(int id){
        return new Job(id, getPickup(), getDelivery(), getDescription(), getSkills(), getLocation(), getTimeWindows(),
            getPriority());
    }
}
