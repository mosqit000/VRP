package de.mopla.connector.request.VehicleFactory;

import de.mopla.connector.request.*;
import de.mopla.connector.request.Vroom.EVehicleRegistry;

import java.util.List;

public class EVFactory implements VehicleFactory {

    @Override
    public Vehicle createVehicle(int id, Location start, TimeWindow timeWindow) {


        Vehicle base = new Vehicle(id, "EV " + id,
                start, start,
                List.of(7, 180), // [passenger_capacity, battery_range_km]
                List.of(1), // EV skill
                // both of above capacities are to try to force vroom to handle
                // the allocation of charging jobs
                timeWindow,
                List.of(),
                1d);


        EVehicle evVehicle = new EVehicle(base, 180.0, 3, 1.0);


        EVehicleRegistry.getInstance().registerEVehicle(evVehicle);
        // in case of real life scenarios
        // this will be used for consistency

        return base;
    }
}