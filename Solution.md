# initial understanding:
we query Vroom with Vehicles, shipments, and jobs to reach, which in turn will return the route that 
the vehicle should follow

# Goal:
add EV vehicles to the mix

# Steps:
1. adding EV to Vehicles -> new family of objects -> abstract factory pattern
2. new state must be added but Vehicle class is final -> decorator pattern
3. now we've reached a working stage where new products or different types can be added while complying with Vroom
4. for the more important part : charging stops 
    - there are (i think .. ) 4 methods of facing this, Vroom handles the assignment of stops (shipments, jobs, breaks,..) to vehicles
      - first: to add stops before vroom (pre-processing)
        - easy (by logic not technically)
        - very UNPRODUCTIVE !!
        - static stops - waste of vehicle time and through put
        - idea: consider vehicles always operate and consume max capacity (fuel, battery, etc..), and the worst case scenario is to add a charging stop each - for example- 2 hours
        - this will lead to time wasted and vehicle being idle
        
      - second: have Vroom handle it (logical since its the one doing the assignment)
        - also easy (logically :) )
        - implementation: 
          - can be done through max_distance, which is unique to each vehicle (this saves us the trouble of tracking everything)
          - and after the expiry of the first vehicle, we can re-plan again.
          - there is also an interesting aspect in Vroom, multidimensional capacities   ----> my opinion, this the BEST solution 
          - for example [passenger_count, battery_capacity] for all vehicles (this condition is from vroom)
          - and manipulate it through amounts,pickups,and deliveries 
          - i.e. a charging job with high priority but has a time window availability of all day
          - this will force vroom into allocating it to the vehicles that need it
          -  negative amounts (stand for refills) 
        - technical difficulty
        - not always feasible because when u have multi-dimensions, vroom expects you to have it on all vehicles and all types
        
      - third: post-processing, handling the schedule after vroom
        - can be done by manual tracking of which vehicles consume some type of capacity (in our case EVs and battery)
        - and here we can update the route dynamically ( which i believe is a bad idea )
          - bcz if we add new stops to a route, it wont be necessarily close to wherever it is now (might be unproductive to charge at a faraway station and return to the same route)
          - and the timing can be mixed up (shipment time window closes in charging time)
        - or we can re-plan (more adaptive and productive) - better
        
      - fourth: real time (queue or messaging)
        - more complex
        - higher resources
        - but real time communications
        - could require different resources, hardware battery trackers 
        - best productivity

5. trade-offs and simplicity: 
    - depending on the scope, perhaps a service that checks the type and does its functionality are sufficient
    - for example, a dynamic structure that's abstracted between different types of vehicles
    - i.e. consumables can stop a journey
    - so the standard way can be a bit complex, but gets the job done while being able to scale 
    - i believe that the fourth approach above is the best to manage these situations, but can be complex and demanding
    - which is why I would go with the max_distance and re-plan, or the multidimensional or manual approaches
    - these keep productivity to an acceptable degree and are easy to implement and maintain