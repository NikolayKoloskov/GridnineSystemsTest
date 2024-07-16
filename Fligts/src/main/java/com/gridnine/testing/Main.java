package com.gridnine.testing;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FilterFlight filter = new FilterFlight();
        List<Flight> filteredFlights = new ArrayList<>();
        filteredFlights = filter.filterWrongDate(flights);
        filteredFlights = filter.filterLongTimeOnGround(filteredFlights, 120);
        filteredFlights = filter.filterWrongDepartureTime(filteredFlights);

        for (Flight flight : filteredFlights) {
            System.out.println(flight.toString());
        }
    }
}


