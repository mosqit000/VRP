package de.mopla.connector.request.RouteValidation;

import de.mopla.connector.request.Vehicle;
import de.mopla.connector.request.Vroom.EVehicleRegistry;
import de.mopla.connector.response.Route;
import de.mopla.connector.response.Step;
import de.mopla.connector.response.UnassignedTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EVRouteValidator implements RouteValidator{

    private final Map<Integer, Double> batteryMap;
    private final double consumptionRate;
    private final double minBatteryThreshold;
    private final EVehicleRegistry registry = EVehicleRegistry.getInstance();

    public static final double MAX_BATTERY = 250.0;
    public static final double CHARGING_KWH = 50.0;



    public EVRouteValidator(Map<Integer, Double> batteryMap, double consumptionRate, double minBatteryThreshold) {
        this.batteryMap = batteryMap;
        this.consumptionRate = consumptionRate;
        this.minBatteryThreshold = minBatteryThreshold;
    }

    @Override
    public boolean validate(Vehicle vehicle, List<Step> steps) {   // this is not used // could check the same logic as below for another flow
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

    public ValidationResult insertChargingStops(Route route){
        List<UnassignedTask> unassignedTasks = new ArrayList<>();
        List<Step> newSteps = new ArrayList<>();
        if(registry.isEVehicle(route.getVehicle())){
            double remainingBattery = batteryMap.get(route.getVehicle());
            for (Step step : route.getSteps()) {
                if (!step.isBreak()) {
                    System.out.println("check this distance: "+  (double) step.getDistance() );
                    double distance = (double) step.getDistance() / 1000;
                    remainingBattery -= distance * consumptionRate;

                    if (remainingBattery < minBatteryThreshold) {
                        // should redirect to closest charging station
                        // this can be done this way
                        // or having each car track its own consumption

                        // to also add a charging stop
                        // set timewindow for now and priority max and send it directly to vehicle
                        // without passing it again to vroom


                        // for example below
                        // move all remaining steps into unassigned
                        unassignedTasks.add(
                                new UnassignedTask(
                                        step.getId(),
                                        step.getLocation(),
                                        step.getType(),
                                        step.getDescription()
                                )
                        );

                        // we also need to remove them from current route
                        // this is done by using a new route

                    }else{
                        newSteps.add(step);
                    }

                } else {
                    // here we assumed that break are to be used for charging
                    // but this is not used or reached in this flow
                    remainingBattery = Math.min(remainingBattery + CHARGING_KWH, MAX_BATTERY);

                }
            }
        }else {
            newSteps.addAll(route.getSteps());
        }
        Route newRoute = new Route(
                route.getVehicle(),
                newSteps,
                route.getCost(),  // from this and down, all need to be changed bcz new route will be less than before
                route.getSetup(),
                route.getDuration(),
                route.getWaitingTime(),
                route.getDistance(),
                route.getPriority(),
                route.getDescription(),
                route.getViolations()
        );
        return new ValidationResult(newRoute,unassignedTasks);
    }
}
