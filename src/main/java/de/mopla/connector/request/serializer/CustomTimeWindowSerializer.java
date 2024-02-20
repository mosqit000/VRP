package de.mopla.connector.request.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.mopla.connector.request.TimeWindow;

import java.io.IOException;

public class CustomTimeWindowSerializer extends JsonSerializer<TimeWindow> {

    @Override
    public void serialize(TimeWindow value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        long[] arr = new long[2];
        arr[0] = value.getStart();
        arr[1] = value.getEnd();
        gen.writeArray(arr, 0 , 2);
    }

}
