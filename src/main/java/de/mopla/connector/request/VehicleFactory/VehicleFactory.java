package de.mopla.connector.request.VehicleFactory;

import de.mopla.connector.request.TimeWindow;
import de.mopla.connector.request.Vehicle;
import de.mopla.connector.request.Location;

public interface VehicleFactory {

    Vehicle createVehicle(int id, Location start, TimeWindow timeWindow);
}

