package de.mopla.connector.request.Vroom;

import de.mopla.connector.request.EVehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// to track eVehicles
public class EVehicleRegistry {
    private static final EVehicleRegistry INSTANCE = new EVehicleRegistry();
    private final Map<Integer, EVehicle> evVehicles = new HashMap<>();

    private EVehicleRegistry() {}

    public static EVehicleRegistry getInstance() {
        return INSTANCE;
    }

    public void registerEVehicle(EVehicle evVehicle) {
        evVehicles.put(evVehicle.getVehicle().getId(), evVehicle);
    }

    public Optional<EVehicle> getEVehicle(int vehicleId) {
        return Optional.ofNullable(evVehicles.get(vehicleId));
    }

    public boolean isEVehicle(int vehicleId) {
        return evVehicles.containsKey(vehicleId);
    }

    public void clear() {
        evVehicles.clear();
    }

    public Map<Integer, EVehicle> getAllEVehicles() {
        return new HashMap<>(evVehicles);
    }
}
