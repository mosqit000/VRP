package de.mopla.connector.request.RouteValidation;

import de.mopla.connector.request.Vehicle;
import de.mopla.connector.response.Step;

import java.util.List;
import java.util.Map;

public class EVRouteValidator implements RouteValidator{

    private final Map<Integer, Double> batteryMap;
    private final double consumptionRate;
    private final double minBatteryThreshold;

    public static final double MAX_BATTERY = 250.0;
    public static final double CHARGING_KWH = 50.0;


    public EVRouteValidator(Map<Integer, Double> batteryMap, double consumptionRate, double minBatteryThreshold) {
        this.batteryMap = batteryMap;
        this.consumptionRate = consumptionRate;
        this.minBatteryThreshold = minBatteryThreshold;
    }

    @Override
    public boolean validate(Vehicle vehicle, List<Step> steps) {
        if (!vehicle.getDescription().startsWith("EV")) return true;

        double remainingBattery = batteryMap.get(vehicle.getId());
        for (Step step : steps) {
            if (!step.isBreak()) {
                double distance = (double) step.getDistance() / 1000;
                remainingBattery -= distance * consumptionRate;

                if (remainingBattery < minBatteryThreshold) {
                    return false;
                }
            } else {
                remainingBattery = Math.min(remainingBattery + CHARGING_KWH, MAX_BATTERY);
            }
        }
        return true;
    }
}
