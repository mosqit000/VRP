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
    -