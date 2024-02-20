package de.mopla.connector.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.mopla.connector.request.serializer.CustomLocationDeserializer;
import de.mopla.connector.request.serializer.CustomLocationSerializer;

import java.util.Objects;

@JsonSerialize(using = CustomLocationSerializer.class)
@JsonDeserialize(using = CustomLocationDeserializer.class)
public final class Location {

    private final Double latitude;
    private final Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Location) obj;
        return Objects.equals(this.latitude, that.latitude) &&
                Objects.equals(this.longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @Override
    public String toString() {
        return "Location[" +
                "latitude=" + latitude + ", " +
                "longitude=" + longitude + ']';
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
