package de.mopla.connector.request.Vroom;

import de.mopla.connector.request.Job;
import de.mopla.connector.request.Shipment;
import de.mopla.connector.request.Vehicle;

import java.util.List;

public interface RoutingStrategy {

    VroomQuery buildQuery(List<Vehicle> vehicles, List<Job> jobs, List<Shipment> shipments);
}
