package de.mopla.connector.request.VehicleFactory;

import de.mopla.connector.request.EVehicle;
import de.mopla.connector.request.Location;
import de.mopla.connector.request.TimeWindow;
import de.mopla.connector.request.Vehicle;
import java.util.List;

public class EVFactory implements VehicleFactory{

    @Override
    public Vehicle createVehicle(int id, Location start, TimeWindow timeWindow) {
        Vehicle base = new Vehicle(id, "EV " + id,
                start, start,
                List.of(7,180),
                List.of(),
                timeWindow,
                List.of(),
                1d,
                180_000);


        return new EVehicle(base,
                180.0,
                0.2,
                1.0).getVehicle();
    }
}
