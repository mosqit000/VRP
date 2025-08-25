package de.mopla.connector.request.RouteValidation;


import de.mopla.connector.request.Vehicle;
import de.mopla.connector.response.Step;

import java.util.List;

public interface RouteValidator {

    boolean validate(Vehicle vehicle, List<Step> steps);
}

