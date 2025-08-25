package de.mopla.connector.request.Vroom;

import de.mopla.connector.request.*;
import de.mopla.connector.request.RouteValidation.RouteValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EVRoutingStrategy implements RoutingStrategy{


//    private final AtomicInteger idGenerator = new AtomicInteger(10000);
    private final List<Location> chargingStations;

    public EVRoutingStrategy(List<Location> chargingStations) {
        // for another attempt
        this.chargingStations = chargingStations;
    }

    @Override
    public VroomQuery buildQuery(List<Vehicle> vehicles, List<Job> jobs, List<Shipment> shipments) {
        List<Vehicle> modifiedVehicles = new ArrayList<>();

        for (Vehicle v : vehicles) {
            if (v.getDescription().startsWith("EV")) {
                Vehicle ev = new Vehicle(
                        v.getId(),
                        v.getDescription(),
                        v.getStart(),
                        v.getEnd(),
                        v.getCapacity(),
                        v.getSkills(),
                        v.getTimeWindow(),
                       // List.of(chargingBreak), // for static breaks
                        List.of(),
                        v.getSpeedFactor()
                );

                modifiedVehicles.add(ev);
            } else {
                modifiedVehicles.add(v);
            }
        }
        return new VroomQuery(modifiedVehicles, List.of(), shipments);
    }


}
