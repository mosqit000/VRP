# 🚗 Routing Optimization – Take-Home Task

This is a take-home assignment designed to evaluate your backend development, architectural thinking, and ability to deal with realistic constraints.

There are **two levels** of this task:

---

## 🧑‍💻 Junior Track: Routing Basics

### Goal
Assign as many trip requests as possible to a fleet of 20 vehicles using the [VROOM](https://github.com/VROOM-Project/vroom) optimizer.

### Input
- A CSV file (`trip_requests.csv`) with 250 ride requests
- A fleet of 20 vehicles split between Köthen and Gräfenhainichen
    - Two shifts (3:00–13:00 and 12:00–22:00)
    - Each vehicle has capacity for 7 passengers
    - All must return to start location

### Task
- Implement:
    - `readShipments()` – parse CSV into shipment objects
    - `createVehicles()` – create the base fleet with correct shifts & locations
    - `logResult()` – human-readable output showing which vehicle served which steps

Use the provided VROOM client to execute a routing optimization and evaluate unassigned requests.

---

## 🧠 Senior Track: E-Mobility Extension (Conceptional)

Let' assume, sSome of the vehicles are electric, and your task is to **design extensions** to make routing EV-aware.

### Scenario

*Context*


In our routing solution, we've so far treated all vehicles as if they could drive indefinitely — a reasonable assumption for combustion engines (e.g. diesel). Now, we want to **extend the system to support electric vehicles (EVs)**, which require **periodic charging** due to their limited battery range.

### EV-Specific Parameters (per vehicle):

- `battery_km` – Maximum distance before the vehicle needs to recharge (e.g. `180 km`)
- `consumption_kwh_per_km` – Energy consumption per kilometer (e.g. `0.2 kWh/km`)
- `charging_speed_kwh_per_min` – Charging speed at a station (e.g. `1.0 kWh/min`)

---
### Your Mission

> 💡 You do **not** have to implement everything. Try to focus on the most important parts of the problem or what 
> you think is the most interesting to work on. Instead of a full implementation, you can describe your ideas 
> in comments, stubs, or in a README. 


- How you would:
    - Validate: Ensure trips assigned to an EV are feasible given its battery constraints
    - Insert charging stops: How would you detect the need for charging? How would you find and insert charging stops? Would you modify the trip incrementally or replan from scratch?
    - Track: How would you model and track the current battery state?
    - Design Trade-offs & Simplifications: What simplifications would you make to avoid overengineering? What real-world complexity would you postpone or ignore for now?

- How you would model this cleanly (e.g. new services, strategy pattern, configuration layer) to accomodate for EVs
- Hint: read the Vroom API to determine what possibilities are available
- Hint: there is probably no 'perfect' solution for the ev problem; you can try to find the best trade-off

We're particularly interested in how you balance practical trade-offs and structure your thinking.

---

## 🚀 Getting Started

1. Clone the project
2. Make sure Java 21+ and Gradle/Maven is available
3. Run `Main.java` (your entry point)

---

## 🧪 Bonus Ideas (Optional for all)

- Export the query result JSON and visualize it on http://map.vroom-project.org/
- Add metrics (avg. passengers/vehicle, total cost, total energy used)
- Make vehicle and trip data JSON-configurable
- Expose logic via REST instead of CLI
- ...

---

## ✅ Evaluation Criteria

| Category               | What We Look For                                                             |
|------------------------|------------------------------------------------------------------------------|
| Code Quality           | Clean structure, modularization, testability                                 |
| Functional Correctness | Trip assignment logic works correctly with VROOM                             |
| Conceptual Thinking    | For senior candidates: architecture/logic ideas, trade-offs, simplifications |
| Communication          | Clear explanation of assumptions and decisions                               |
| Bonus Value            | EV metrics, visualizations, REST, or modular design                          |

---

## 🙌 Good Luck
We’re looking forward to seeing how you approach this!
