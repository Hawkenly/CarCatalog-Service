#  Car Catalog
The application is a catalog of cars that provides the user with information about a particular make/model of car.
## Info
This app currently in developing and more features will be added later.
## Used
Java and Spring Framework
## How to run an application
1. Download files from a repository
2. Launch CarCatalog.java
3. Enter a request into the browser 
# API Endpoints
1. http://localhost:8080/cars/ - to get information about all cars
2. http://localhost:8080/cars?mark={mark} - to get information about particular car (currently there are only 2 marks: AUDI and AC), if mark not mentioned it returned a default value - AUDI.
# Testing
The result is returned as JSON.

Result for a request: http://localhost:8080/cars/  -  {"response":"{id: AUDI, name: AUDI, popular: true, country: Германия},{id: AC, name: AC, popular:false, country: Великобритания)"}

