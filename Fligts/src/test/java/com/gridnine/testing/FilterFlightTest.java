package com.gridnine.testing;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class FilterFlightTest {
    FilterFlight filter = new FilterFlight();
    List<Flight> flights;

    @BeforeEach
    void setUp() {
        filter = new FilterFlight();
        flights = FlightBuilder.createFlights();
    }

    @Test
    void filterWrongDate() {
        List<Flight> expected = new ArrayList<>(List.copyOf(flights));
        expected.remove(2);
        flights = filter.filterWrongDate(flights);
        Assertions.assertEquals(expected, flights);
    }

    @Test
    void filterWrongDepartureTime() {
        List<Flight> expected = new ArrayList<>(List.copyOf(flights));
        expected.remove(3);
        flights = filter.filterWrongDepartureTime(flights);
        Assertions.assertEquals(expected, flights);
    }

    @Test
    void filterLongTimeOnGround() {
        List<Flight> expected = new ArrayList<>(List.copyOf(flights));
        expected.remove(5);
        expected.remove(4);
        flights = filter.filterLongTimeOnGround(flights,120);
        Assertions.assertEquals(expected, flights);

    }
}