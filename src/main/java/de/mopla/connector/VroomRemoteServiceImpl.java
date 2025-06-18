package de.mopla.connector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.mopla.connector.request.VroomQuery;
import de.mopla.connector.response.VroomOutput;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;


import java.io.IOException;
import java.util.Optional;

/**
 * Facade to access vroom functionality via RestTemplate
 */
public class VroomRemoteServiceImpl implements VroomRemoteService {

    private String url = "https://vroom.test.mopla.solutions/";

    private final ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // optional properties in json

    /**
     * Queries the vroom server to schedule the given shipments.
     *
     * @param query the query
     * @return vroom output
     */

    public Optional<VroomOutput> query(VroomQuery query) {
        try {
            final String jsonBody = mapper
                    .disable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
                    .writeValueAsString(query);

            System.out.println("Asking vroom (" + url + ") with query: \n" + jsonBody);

            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Content-Type", "application/json");
                httpPost.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    String responseBody = EntityUtils.toString(response.getEntity());

                    System.out.println("Vroom answered (" + url + "): \n" + responseBody);

                    final var result = mapper.readValue(responseBody, VroomOutput.class);
                    System.out.println(result);
                    return Optional.of(result);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to call VROOM service", e);
        }
    }
}
