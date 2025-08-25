package de.mopla.connector.request.Vroom;

import de.mopla.connector.request.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EVRoutingStrategy implements RoutingStrategy{


    private final AtomicInteger idGenerator = new AtomicInteger(10000);
    private final List<Location> chargingStations;

    public EVRoutingStrategy(List<Location> chargingStations) {
        this.chargingStations = chargingStations;
    }

    @Override
    public VroomQuery buildQuery(List<Vehicle> vehicles, List<Job> jobs, List<Shipment> shipments) {
        List<Vehicle> modifiedVehicles = new ArrayList<>();

        for (Vehicle v : vehicles) {
            if (v.getDescription().startsWith("EV")) {
                Location station = chargingStations.getFirst();


//                Break chargingBreak = new Break(
//                        v.getId(),  // this should be another id (break id)
//                        List.of(v.getTimeWindow()),
//                        60 * 20L,
//                        v.getCapacity(),
//                        List.of(station.getLongitude(),station.getLatitude())
//                );

                Vehicle ev = new Vehicle(
                        v.getId(),
                        v.getDescription(),
                        v.getStart(),
                        v.getEnd(),
                        v.getCapacity(),
                        v.getSkills(),
                        v.getTimeWindow(),
                       // List.of(chargingBreak),
                        List.of(),
                        v.getSpeedFactor(),
                        v.getMaxDistance()
                );

                modifiedVehicles.add(ev);
            } else {
                modifiedVehicles.add(v);
            }
        }

//        for (Vehicle v : vehicles) {
//            if (v.getDescription().startsWith("EV")) {
//                List<Integer> newCapacity = new ArrayList<>(v.getCapacity());
//                if (newCapacity.size() == 1) {
//                    newCapacity.add(180); // max battery km
//                } else {
//                    newCapacity.set(1, 180);
//                }
//
//                Vehicle ev = new Vehicle(
//                        v.getId(),
//                        v.getDescription(),
//                        v.getStart(),
//                        v.getEnd(),
//                        newCapacity,
//                        v.getSkills(),
//                        v.getTimeWindow(),
//                        v.getBreaks(),
//                        v.getSpeedFactor()
//                );
//
//                modifiedVehicles.add(ev);
//            } else {
//                modifiedVehicles.add(v);
//            }
//        }
//
//        List<Shipment> chargingShipments = new ArrayList<>();
//        for (Location station : chargingStations) {
//            // Pickup at charging station (0 consumption)
//            ShipmentStep pickup = new ShipmentStep(
//                    idGenerator.getAndIncrement(),
//                    0,
//                    0,
//                    station,
//                    List.of(new TimeWindow(0L, 86400L)),
//                    "Charging Start"
//            );
//
//            // Delivery gives back battery (full charge or partial)
//            ShipmentStep delivery = new ShipmentStep(
//                    idGenerator.getAndIncrement(),
//                    0,
//                    0,
//                    station,
//                    List.of(new TimeWindow(0L, 86400L)),
//                    "Charging End"
//            );
//
//            chargingShipments.add(new Shipment(
//                    List.of(0, 0),           // pickup capacity: passengers = 0, battery = 0
//                    List.of(0, 180),         // delivery capacity: passengers = 0, battery = full charge
//                    1,
//                    pickup,
//                    delivery
//            ));
//        }
//
//        // Combine normal shipments with charging shipments
//        List<Shipment> allShipments = new ArrayList<>(shipments);
//        allShipments.addAll(chargingShipments);

        // Add charging shipments for each charging station
//        List<Shipment> chargingShipments = new ArrayList<>();
//        for (int i = 0; i < 20 ; i++ ) {
//            Location station = chargingStations.get(i % chargingStations.size());
//            ShipmentStep pickup = new ShipmentStep(
//                    idGenerator.getAndIncrement(),
//                    0,
//                    0,
//                    station,
//                    List.of(new TimeWindow(0L, 86400L)), // available all day
//                    "Charging Start"
//            );
//
//            ShipmentStep delivery = new ShipmentStep(
//                    idGenerator.getAndIncrement(),
//                    0,
//                    0,
//                    station,
//                    List.of(new TimeWindow(0L, 86400L)),
//                    "Charging End"
//            );
//
//            chargingShipments.add(new Shipment(
//                    List.of(0, 0),      // pickup: no passengers, no battery
//                    List.of(0, 180),    // delivery: recharge battery dimension
//                    1,
//                    pickup,
//                    delivery
//            ));
//        }
//
//        List<Shipment> allShipments = new ArrayList<>(shipments);
//        allShipments.addAll(chargingShipments);



        return new VroomQuery(modifiedVehicles, List.of(), shipments);
    }


}
