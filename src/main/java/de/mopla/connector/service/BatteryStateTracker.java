package de.mopla.connector.service;

import de.mopla.connector.request.EVehicle;
import de.mopla.connector.request.Vroom.EVehicleRegistry;
import de.mopla.connector.response.Route;
import de.mopla.connector.response.Step;
import java.util.HashMap;
import java.util.Map;

// to keep track of battery state
public class BatteryStateTracker {

    private final Map<Integer, Double> vehicleBatteryStates = new HashMap<>();

    public BatteryStateTracker() {
        initializeBatteryStates();
    }

    public void initializeBatteryStates() {
        EVehicleRegistry.getInstance().getAllEVehicles().forEach((id, ev) -> {
            vehicleBatteryStates.put(id, ev.getCurrentChargeKwh());
            System.out.println("amer : "+ id + " " + ev.getCurrentChargeKwh());
        });
    }


    public void updateBatteryState(Route route) {
        int vehicleId = route.getVehicle();

        if (!EVehicleRegistry.getInstance().isEVehicle(vehicleId)) {
            return;
        }

        EVehicle ev = EVehicleRegistry.getInstance().getEVehicle(vehicleId).orElse(null);
        if (ev == null) return;

        double totalDistance = route.getDistance() / 1000.0;
        double consumption = totalDistance * ev.getConsumptionKwhPerKm();


        double currentBattery = vehicleBatteryStates.getOrDefault(vehicleId, ev.getBatteryCapacityKwh());
        double newBattery = Math.max(0, currentBattery - consumption);


        for (Step step : route.getSteps()) {
            if (step.getType().equals("job") &&
                    step.getId() != null &&
                    String.valueOf(step.getDescription()).contains("charging")) {

                newBattery = Math.min(ev.getBatteryCapacityKwh(), newBattery + 30);
            }
        }

        vehicleBatteryStates.put(vehicleId, newBattery);
    }

    public Map<Integer,Double> getVehicleBatteryStates(){
        return vehicleBatteryStates;
    }
}