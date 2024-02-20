package de.mopla.connector.request.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.mopla.connector.request.Location;

import java.io.IOException;

public class CustomLocationSerializer extends JsonSerializer<Location> {

    @Override
    public void serialize(Location value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // the expected order for all coordinates arrays is [lng, lat]“ in https://github.com/VROOM-Project/vroom/blob/master/docs/API.md
        double[] arr = new double[2];
        arr[0] = value.getLongitude();
        arr[1] = value.getLatitude();
        gen.writeArray(arr, 0 , 2);
    }

}
