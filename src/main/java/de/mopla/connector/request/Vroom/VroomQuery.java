package de.mopla.connector.request.Vroom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.mopla.connector.request.Job;
import de.mopla.connector.request.Shipment;
import de.mopla.connector.request.Vehicle;

import java.util.List;

public class VroomQuery {

    private final List<Vehicle> vehicles;
    private final List<Shipment> shipments;
    private final List<Job> jobs;

    @JsonCreator
    public VroomQuery(@JsonProperty("vehicles") List<Vehicle> vehicles,
                      @JsonProperty("jobs") List<Job> jobs,
                      @JsonProperty("shipments") List<Shipment> shipments) {
        this.vehicles = vehicles;
        this.shipments = shipments;
        this.jobs = jobs;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public List<Job> getJobs() {
        return jobs;
    }
}
