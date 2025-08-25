package de.mopla.connector.request.RouteValidation;

import de.mopla.connector.response.Route;
import de.mopla.connector.response.UnassignedTask;

import java.util.List;

public class ValidationResult {

    public ValidationResult(Route route, List<UnassignedTask> unassignedTaskList) {
        this.route = route;
        this.unassignedTaskList = unassignedTaskList;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<UnassignedTask> getUnassignedTaskList() {
        return unassignedTaskList;
    }

    public void setUnassignedTaskList(List<UnassignedTask> unassignedTaskList) {
        this.unassignedTaskList = unassignedTaskList;
    }

    private Route route;
    private List<UnassignedTask> unassignedTaskList;

}
