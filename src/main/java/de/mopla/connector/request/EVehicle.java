package de.mopla.connector.request;

public class EVehicle implements VehicleComponent {
    private final Vehicle baseVehicle;
    private final double batteryCapacityKwh;
    private final double maxRangeKm;
    private final double consumptionKwhPerKm;
    private final double chargingSpeedKwhPerMin;
    private double currentChargeKwh;

    public EVehicle(Vehicle baseVehicle, double maxRangeKm,
                    double consumptionKwhPerKm, double chargingSpeedKwhPerMin) {
        this.baseVehicle = baseVehicle;
        this.maxRangeKm = maxRangeKm;
        this.consumptionKwhPerKm = consumptionKwhPerKm;
        this.chargingSpeedKwhPerMin = chargingSpeedKwhPerMin;
        this.batteryCapacityKwh = maxRangeKm * consumptionKwhPerKm;
        this.currentChargeKwh = batteryCapacityKwh;
    }

    @Override
    public Vehicle getVehicle() {
        return this.baseVehicle;
    }

    public double getBatteryCapacityKwh() { return batteryCapacityKwh; }
    public double getMaxRangeKm() { return maxRangeKm; }
    public double getConsumptionKwhPerKm() { return consumptionKwhPerKm; }
    public double getChargingSpeedKwhPerMin() { return chargingSpeedKwhPerMin; }
    public double getCurrentChargeKwh() { return currentChargeKwh; }


    public double getRemainingRangeKm() {
        return currentChargeKwh / consumptionKwhPerKm;
    }

    public double getBatteryPercentage() {
        return (currentChargeKwh / batteryCapacityKwh) * 100;
    }

    public boolean canTravelDistance(double distanceKm) {
        return getRemainingRangeKm() >= distanceKm;
    }

    public void consumeEnergy(double distanceKm) {
        double consumption = distanceKm * consumptionKwhPerKm;
        currentChargeKwh = Math.max(0, currentChargeKwh - consumption);
    }

    public void addCharge(double kWh) {
        currentChargeKwh = Math.min(batteryCapacityKwh, currentChargeKwh + kWh);
    }

    public void chargeFully() {
        currentChargeKwh = batteryCapacityKwh;
    }

    public double getChargingTimeToFull() {
        double neededCharge = batteryCapacityKwh - currentChargeKwh;
        return neededCharge / chargingSpeedKwhPerMin;
    }

    public double getChargingTimeForRange(double targetRangeKm) {
        double targetCharge = targetRangeKm * consumptionKwhPerKm;
        double neededCharge = Math.max(0, targetCharge - currentChargeKwh);
        return neededCharge / chargingSpeedKwhPerMin;
    }
}