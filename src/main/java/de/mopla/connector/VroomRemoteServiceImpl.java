package de.mopla.connector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.mopla.connector.request.VroomQuery;
import de.mopla.connector.response.VroomOutput;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

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

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(jsonBody));
            HttpResponse response = httpClient.execute(httpPost);
            String responseBody = EntityUtils.toString(response.getEntity());
            httpClient.close();

            System.out.println("Vroom answered (" + url + "): \n" + responseBody);

            final var result = mapper.readValue(responseBody, VroomOutput.class);
            System.out.println(result);
            return Optional.of(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
