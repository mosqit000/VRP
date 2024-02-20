package de.mopla.connector.request.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import de.mopla.connector.request.Location;

import java.io.IOException;

public class CustomLocationDeserializer extends JsonDeserializer<Location> {
    @Override
    public Location deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        final var tree = jsonParser.getCodec().readTree(jsonParser);
        final var start = (Integer) ((IntNode) tree.get(0)).numberValue();
        final var end = (Integer) ((IntNode) tree.get(1)).numberValue();
        return new Location(start.doubleValue(), end.doubleValue());
    }
}
