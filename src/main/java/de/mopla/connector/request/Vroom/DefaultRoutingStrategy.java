package de.mopla.connector.request.Vroom;

import de.mopla.connector.request.Job;
import de.mopla.connector.request.Shipment;
import de.mopla.connector.request.Vehicle;

import java.util.List;

public class DefaultRoutingStrategy implements RoutingStrategy{

    @Override
    public VroomQuery buildQuery(List<Vehicle> vehicles, List<Job> jobs, List<Shipment> shipments) {

        return new VroomQuery(vehicles, List.of(), shipments);
    }
}
