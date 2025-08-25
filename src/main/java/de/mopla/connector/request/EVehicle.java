package de.mopla.connector.request;

public class EVehicle implements VehicleComponent{

    private final Vehicle baseVehicle;
    private final double batteryKm;     // max capacity
    private double currentCharge;       // curr consumption
    private final double consumptionPerKm;
    private final double chargingSpeedPerMin;

    public EVehicle(Vehicle baseVehicle, double batteryKm,
                    double consumptionPerKm, double chargingSpeedPerMin) {

        this.baseVehicle = baseVehicle;
        this.batteryKm = batteryKm;
        this.consumptionPerKm = consumptionPerKm;
        this.chargingSpeedPerMin = chargingSpeedPerMin;
    }

    @Override
    public Vehicle getVehicle() {
        return this.baseVehicle;
    }

    public double getBatteryKm() { return batteryKm; }
    public double getConsumptionPerKm() { return consumptionPerKm; }
    public double getChargingSpeedPerMin() { return chargingSpeedPerMin; }


    public void consume(double distanceKm) {
        currentCharge -= distanceKm * consumptionPerKm;
    }

    public void charge(double kWh) {
        currentCharge = Math.min(batteryKm, currentCharge + kWh);
    }

}
