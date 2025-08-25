package de.mopla;

import de.mopla.connector.VroomRemoteService;
import de.mopla.connector.VroomRemoteServiceImpl;
import de.mopla.connector.request.*;
import de.mopla.connector.request.RouteValidation.EVRouteValidator;
import de.mopla.connector.request.RouteValidation.RouteValidator;
import de.mopla.connector.request.RouteValidation.ValidationResult;
import de.mopla.connector.request.VehicleFactory.EVFactory;
import de.mopla.connector.request.VehicleFactory.StandardVehicleFactory;
import de.mopla.connector.request.VehicleFactory.VehicleFactory;
import de.mopla.connector.request.Vroom.*;
import de.mopla.connector.response.Route;
import de.mopla.connector.response.Step;
import de.mopla.connector.response.UnassignedTask;
import de.mopla.connector.response.VroomOutput;
import de.mopla.connector.service.BatteryStateTracker;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main {


    static BatteryStateTracker tracker;

    public static void main(String[] args) throws IOException {

        // Welcome to the world of logistics and journey optimization! In this exercise, you'll dive into the exciting
        // realm of route planning. Your mission is to orchestrate the most efficient journeys for a fleet of vehicles
        // servicing customer trip requests.

        // 0: For the calculation of vehicle routes, you can use the VroomRemoteService.
        //    Read the documentation of VROOM on how to use this service: https://github.com/VROOM-Project/vroom/blob/master/docs/API.md

        // 1. Begin by extracting information from the "trip_requests.csv" file.
        //    This file includes time-windows of the start and goal, passenger count and the start and goal coordinates of
        //    250 fictional route requests for one day.
        //    to note: for entering or exiting a vehicle passengers don't need any time (setup = 0, service = 0)
        final List<Shipment> shipments = readShipments();

        // 2. Your available fleet consists of 20 vehicles in total, distributed in two different cities.
        //    Create 10 vehicles for each of the towns, Köthen and Gräfenheinichen.
        //    10 vehicles should start in Köthen (51.74726291640497, 11.955642553636217)
        //    - 5 of those work in between 3:00 and 13:00
        //    - 5 of those work in between 12:00 and 22:00
        //    10 vehicles should start in Gräfenheinichen (51.72542241470092, 12.459856484026506)
        //    - 5 of those work in between 3:00 and 13:00
        //    - 5 of those work in between 12:00 and 22:00
        //    for all vehicles:
        //    - they do not need a break
        //    - they have a capacity of 7 passengers
        //    - they need to return to their start location at the end of the day
        //    - the speed-factor is 1

        final List<Vehicle> vehicles = createVehicles();

        tracker = new BatteryStateTracker(); // this is to track state
        // in real life
        // 1. if we commit to this solution we need to use its functionality
        // similar to evict cache (update when anything affects, add to pool, remove from pool)
        // 2. a stream and data messaging management would be the better choice

        final RouteValidator evRouteValidator = new EVRouteValidator(
                tracker.getVehicleBatteryStates(),
                0.2, // hypothetical
                20  // should be enough to reach a charging station from anywhere
        );

        // 3. Use the VroomRemoteService to calculate optimized routes. Understand how the service efficiently
        //    combines vehicle details and customer requests to generate practical routes.
        //    Calculating this can take up to 30 seconds, make sure to have a internet connection when calling this
        VroomRemoteService vroom = new VroomRemoteServiceImpl();

        RoutingStrategy strategy = new EVRoutingStrategy(
                List.of(
                new Location(51.76487, 12.639522),
      new Location(51.80051, 12.741775)

        ));
        VroomQuery query = strategy.buildQuery(vehicles,List.of(), shipments);



       // VroomQuery query = new VroomQuery(vehicles, List.of(), shipments);
        final var result = vroom.query(query);



        // 4. Log out the vroom result in a human-readable way (you can ignore e.g. the 'geometry' value of each route
        result.ifPresent(Main::logResult);

        if(result.isPresent()) {
            System.out.println("-------  modified result -----");
            System.out.println("-------  broadcast this to vehicles -----");
            System.out.println("-------  and replan the rest based on trigger -----");
            var modifiedResult = validate(result.get(), evRouteValidator);
            logResult(modifiedResult);
        }

        // BONUS: Visualize the routes using the vroom demo server http://map.vroom-project.org/
        //      - Look into the log output when running your code, you should see a line that says "Asking vroom (https://vroom.test.mopla.solutions/) with query: ..."
        //      - Copy json file printed out there into a file and upload it to the vroom demo server: http://map.vroom-project.org/
        //      - The demo server only accepts up to 100 tasks (one shipment is 2 tasks), so you have to limit the number of shipments to 50

        // BONUS: Troubleshoot Unsuccessful Scheduling:
        //    - Notice that some requests may not be scheduled successfully.
        //    - Consider what changes could be made to the setup to ensure those trips are assigned.
        //    - Be creative and explore solutions that might not be feasible in reality.
        //    - Revisit the Vroom API documentation for potential tweaks and adjustments.

    }

    private static void logResult(VroomOutput result) {
        System.out.println("Vroom Output:");
        System.out.println("Code: " + result.getCode());
        System.out.println("Error: " + result.getError());

        if (result.getRoutes() != null) {
            for (Route route : result.getRoutes()) {
                System.out.println("\nRoute Details:");
                System.out.println("Vehicle: " + route.getVehicle());
                System.out.println("Cost: " + route.getCost());
                System.out.println("Setup: " + route.getSetup());
                System.out.println("Duration: " + route.getDuration());
                System.out.println("Waiting Time: " + route.getWaitingTime());
                System.out.println("Distance: " + route.getDistance());
                System.out.println("Priority: " + route.getPriority());
                System.out.println("Description: " + route.getDescription());

                // Print steps details
                System.out.println("Steps:");
                for (Step step : route.getSteps()) {

                    System.out.println("  " + step.getLocation() + " " + step.getType() +
                            ("break".equals(step.getType()) ?  " (service " + step.getDuration() + "s)" : "") );
                    // Add more details if needed
                }
            }
        }

        System.out.println("\n Unassigned: " + result.getUnassigned().size());
        for (UnassignedTask task : result.getUnassigned()){
             System.out.println(task.id() + " " +  task.description() + " " + task.type());
        }

    }

    private static ArrayList<Vehicle> createVehicles() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        VehicleFactory normalFactory = new StandardVehicleFactory();
        VehicleFactory evFactory = new EVFactory();


        // Create 10 vehicles starting in Köthen
        for (int i = 1; i <= 10; i++) {
            Location startLocation = new Location(51.74726291640497, 11.955642553636217);

            TimeWindow timeWindow;
            if (i <= 5) {
                timeWindow = new TimeWindow(Instant.parse("2024-02-20T03:00:00Z")
                        .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond(),
                        Instant.parse("2024-02-20T13:00:00Z")
                                .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond());
            } else {
                timeWindow = new TimeWindow(Instant.parse("2024-02-20T12:00:00Z")
                        .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond(),
                        Instant.parse("2024-02-20T22:00:00Z")
                                .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond());
            }

            Vehicle vehicle = (i % 2 == 0)
                    ? evFactory.createVehicle(i, startLocation, timeWindow)
                    : normalFactory.createVehicle(i, startLocation, timeWindow);

//            Vehicle vehicle = new Vehicle(i, "Vehicle " + i, startLocation, startLocation,
//                    List.of(7), List.of(), timeWindow, List.of(), 1d);
            vehicles.add(vehicle);
        }

        // Create 10 vehicles starting in Gräfenheinichen
        for (int i = 11; i <= 20; i++) {
            Location startLocation = new Location(51.72542241470092, 12.459856484026506);

            TimeWindow timeWindow;
            if (i <= 15) {
                timeWindow = new TimeWindow(Instant.parse("2024-02-20T03:00:00Z")
                        .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond(),
                        Instant.parse("2024-02-20T13:00:00Z")
                                .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond());
            } else {
                timeWindow = new TimeWindow(Instant.parse("2024-02-20T12:00:00Z")
                        .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond(),
                        Instant.parse("2024-02-20T22:00:00Z")
                                .atZone(ZoneId.of("Europe/Berlin")).toEpochSecond());
            }

            Vehicle vehicle = (i % 2 == 0)
                    ? evFactory.createVehicle(i, startLocation, timeWindow)
                    : normalFactory.createVehicle(i, startLocation, timeWindow);

//            Vehicle vehicle = new Vehicle(i, "Vehicle " + i, startLocation, startLocation,
//                    List.of(7), List.of(), timeWindow, List.of(), 1d);
            vehicles.add(vehicle);
        }

        return vehicles;
    }

    private static ArrayList<Shipment> readShipments() {
        int id = 0;
        final var shipments = new ArrayList<Shipment>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("trip_requests.csv");
             InputStreamReader reader = new InputStreamReader(inputStream);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                String startTimeWindowStartStr = csvRecord.get("start_timewindow_start");
                String startTimeWindowEndStr = csvRecord.get("start_timewindow_end");
                String endTimeWindowStartStr = csvRecord.get("end_timewindow_start");
                String endTimeWindowEndStr = csvRecord.get("end_timewindow_end");

                // Parse date-time strings to LocalDateTime using custom formatter
                LocalDateTime startTimeWindowStart = LocalDateTime.parse(startTimeWindowStartStr, formatter);
                LocalDateTime startTimeWindowEnd = LocalDateTime.parse(startTimeWindowEndStr, formatter);
                LocalDateTime endTimeWindowStart = LocalDateTime.parse(endTimeWindowStartStr, formatter);
                LocalDateTime endTimeWindowEnd = LocalDateTime.parse(endTimeWindowEndStr, formatter);

                final var passengerCount = Integer.parseInt(csvRecord.get("passenger_count"));
                final var startLatitude = Double.parseDouble(csvRecord.get("from_location_latitude"));
                final var startLongitude = Double.parseDouble(csvRecord.get("from_location_longitude"));
                final var startLabel = csvRecord.get("from_location_label");
                final var goalLatitude = Double.parseDouble(csvRecord.get("to_location_latitude"));
                final var goalLongitude = Double.parseDouble(csvRecord.get("to_location_longitude"));
                final var goalLabel = csvRecord.get("to_location_label");

                final var pickup = new ShipmentStep(
                        id++,
                        0,
                        0,
                        new Location(startLatitude, startLongitude),
                        List.of(new TimeWindow(startTimeWindowStart, startTimeWindowEnd)),
                        startLabel
                );
                final var delivery = new ShipmentStep(
                        id++,
                        0,
                        0,
                        new Location(goalLatitude, goalLongitude),
                        List.of(new TimeWindow(endTimeWindowStart, endTimeWindowEnd)),
                        goalLabel
                );

                shipments.add(
                        new Shipment(
                                List.of(passengerCount,0),
                                List.of(),
                                1,
                                pickup,
                                delivery
                        ));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return shipments;
    }

    private static VroomOutput validate (VroomOutput result , RouteValidator evRouteValidator){
        List<Route> newRoutes = new ArrayList<>();
        List<UnassignedTask> unassignedTasks = new ArrayList<>();
        for(Route route : result.getRoutes()){
                ValidationResult validationResult = evRouteValidator.insertChargingStops(
                       route);
                newRoutes.add(validationResult.getRoute());
                unassignedTasks.addAll(validationResult.getUnassignedTaskList());
        }
        VroomOutput newOutput = new VroomOutput(
                result.getCode(), // should be modified to somthing declartive that it has been modified
                result.getError(), // same applies for things like summary
                result.getSummary(),
                unassignedTasks,
                newRoutes
        );
        return newOutput;
    }
}