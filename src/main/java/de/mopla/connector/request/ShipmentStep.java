package de.mopla.connector.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.Transient;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class ShipmentStep {

    @JsonProperty
    private final Integer id;
    @JsonProperty
    private final Integer service;
    @JsonProperty
    private final Integer setup;
    @JsonProperty
    private final Location location;
    @JsonProperty("time_windows")
    private final List<TimeWindow> timeWindows;
    @JsonProperty
    private final String description;

    @JsonCreator
    public ShipmentStep(@JsonProperty("id") Integer id,
                        @JsonProperty("setup") Integer setup,
                        @JsonProperty("service") Integer service,
                        @JsonProperty("location") Double[] location,
                        @JsonProperty("time_windows") Long[][] timeWindows,
                        @JsonProperty("description") String description) {
        this(id, setup, service,
            new Location(location[1], location[0]), // careful: vroom takes longitude first
            Arrays.stream(timeWindows).map(tw -> new TimeWindow(tw[0], tw[1]))
                .collect(Collectors.toList()), description);
    }

    public ShipmentStep(Integer id, Integer setup, Integer service, Location location, List<TimeWindow> timeWindows,
                        String description) {
        this.id = id;
        this.setup = setup;
        this.service = service;
        this.location = location;
        this.timeWindows = timeWindows;
        this.description = description;
    }

    public Integer id() {
        return id;
    }

    public Integer service() {
        return service;
    }

    public Location location() {
        return location;
    }

    public List<TimeWindow> timeWindows() {
        return timeWindows;
    }

    public String description() {
        return description;
    }

    public Integer setup() {
        return setup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipmentStep that = (ShipmentStep) o;
        return Objects.equals(id, that.id) && Objects.equals(service, that.service) && Objects.equals(setup, that.setup) &&
            Objects.equals(location, that.location) && Objects.equals(timeWindows, that.timeWindows) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, service, setup, location, timeWindows, description);
    }

    @Override
    public String toString() {
        return "ShipmentStep{" +
            "id=" + id +
            ", service=" + service +
            ", setup=" + setup +
            ", location=" + location +
            ", timeWindows=" + timeWindows +
            ", description='" + description + '\'' +
            '}';
    }

    @Transient
    public ShipmentStep withNewId(int pickupId) {
        return new ShipmentStep(pickupId, setup(), service(), location(), timeWindows(), description());
    }
}
