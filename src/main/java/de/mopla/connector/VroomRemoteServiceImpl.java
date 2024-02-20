package de.mopla.connector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.mopla.connector.request.VroomQuery;
import de.mopla.connector.response.VroomOutput;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
            final String data = mapper
                    .disable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED)
                    .writeValueAsString(query);
            System.out.println("Asking vroom (" + url + ") with query: \n" + data);

            // Create an instance of HttpClient
            CloseableHttpClient httpClient = HttpClients.createDefault();

            // Create a GET request object
            HttpGet request = new HttpGet(url);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(request);

            // Extract the response body as a string
            String responseBody = EntityUtils.toString(response.getEntity());

            // Print the response body
            System.out.println(responseBody);


            // Close the HttpClient and release any system resources it holds
            httpClient.close();

            final var result = mapper.readValue(responseBody, VroomOutput.class);
            return Optional.of(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
