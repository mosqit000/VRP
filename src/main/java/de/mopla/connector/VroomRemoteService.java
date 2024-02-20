package de.mopla.connector;

import de.mopla.connector.request.VroomQuery;
import de.mopla.connector.response.VroomOutput;

import java.util.Optional;

/**
 * Facade to access vroom functionality via RestTemplate
 */
public interface VroomRemoteService {

    /**
     * Queries the vroom server to schedule the given shipments.
     *
     * @param query the query
     * @return vroom output
     */
    Optional<VroomOutput> query(VroomQuery query);


}
