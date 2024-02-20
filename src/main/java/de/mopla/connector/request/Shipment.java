package de.mopla.connector.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.Transient;
import java.util.List;
import java.util.Objects;

public final class Shipment {

    private final List<Integer> amount;
    private final List<Integer> skills;
    private final ShipmentStep pickup;
    private final ShipmentStep delivery;
    private final Integer priority;

    @JsonCreator
    public Shipment(@JsonProperty("amount") List<Integer> amount,
                    @JsonProperty("skills") List<Integer> skills,
                    @JsonProperty("priority") Integer priority,
                    @JsonProperty("pickup") ShipmentStep pickup,
                    @JsonProperty("delivery") ShipmentStep delivery) {
        this.amount = amount;
        this.skills = skills;
        this.pickup = pickup;
        this.delivery = delivery;
        this.priority = priority;
    }

    public List<Integer> getAmount() {
        return amount;
    }

    public List<Integer> getSkills() {
        return skills;
    }

    public ShipmentStep getPickup() {
        return pickup;
    }

    public ShipmentStep getDelivery() {
        return delivery;
    }

    public Integer getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return Objects.equals(amount, shipment.amount) && Objects.equals(skills, shipment.skills) && Objects.equals(pickup, shipment.pickup) && Objects.equals(delivery, shipment.delivery) && Objects.equals(priority, shipment.priority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, skills, pickup, delivery, priority);
    }

    @Override
    public String toString() {
        return "Shipment{" +
            "amount=" + amount +
            ", skills=" + skills +
            ", pickup=" + pickup +
            ", delivery=" + delivery +
            ", priority=" + priority +
            '}';
    }

    @Transient
    public Shipment withNewIds(int pickupId, int deliveryId) {
        final var newPickup = pickup.withNewId(pickupId);
        final var newDelivery = delivery.withNewId(deliveryId);
        return new Shipment(getAmount(), getSkills(), getPriority(), newPickup, newDelivery);
    }

    @Transient
    public Long safeFirstPickupTimeWindowStart() {
        if(pickup == null) return 0L;
        if(pickup.timeWindows().isEmpty() || pickup.timeWindows().get(0) == null) return 0L;
        return pickup.timeWindows().get(0).getStart() == null ? 0L : pickup.timeWindows().get(0).getStart();
    }
}
