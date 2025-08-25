package de.mopla.connector.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

public final class Break {

    @JsonProperty private final Integer id;
    @JsonProperty("time_windows") private final List<TimeWindow> timeWindows;
    @JsonProperty private final Long service;
    @JsonProperty("max_load") private final List<Integer> maxLoad;
    @JsonProperty private final List<Double> location;

    @JsonCreator
    public Break(@JsonProperty("id") Integer id,
                 @JsonProperty("time_windows") List<TimeWindow> timeWindows,
                 @JsonProperty("service") Long service,
                 @JsonProperty("max_load") List<Integer> maxLoad,
                 @JsonProperty("location") List<Double> location) {
        this.id = id;
        this.timeWindows = timeWindows;
        this.service = service;
        this.maxLoad = maxLoad;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public List<TimeWindow> getTimeWindows() {
        return timeWindows;
    }

    public Long getService() {
        return service;
    }

    public List<Integer> getMaxLoad() {
        return maxLoad;
    }

    public List<Double> getLocation() { return location;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Break aBreak = (Break) o;
        return Objects.equals(id, aBreak.id) && Objects.equals(timeWindows, aBreak.timeWindows) && Objects.equals(service, aBreak.service);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timeWindows, service);
    }

    @Override
    public String toString() {
        return "Break{" +
               "id=" + id +
               ", timeWindows=" + timeWindows +
               ", service=" + service +
               '}';
    }
}
