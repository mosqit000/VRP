package de.mopla.connector.request.VehicleFactory;

import de.mopla.connector.request.Location;
import de.mopla.connector.request.TimeWindow;
import de.mopla.connector.request.Vehicle;

import java.util.List;

public class StandardVehicleFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(int id, Location start, TimeWindow timeWindow) {
        return new Vehicle(id, "Vehicle " + id,
                start, start,
                List.of(7,0), List.of(), timeWindow, List.of(),
                1d,
                Integer.MAX_VALUE);
    }
}
