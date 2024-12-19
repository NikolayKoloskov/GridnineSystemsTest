package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlyFilterTest {
    FlyFilter flyFilter;
    Flight flight;
    LocalDateTime time = LocalDateTime.now();


    @BeforeEach
    void setUp() {
        flyFilter = null;
        flight = null;
    }

    @Test
    void addFilter() {
        flyFilter = new FlyFilter();
        List<Filter<Flight>> filters = new ArrayList<>();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        flyFilter.addFilter(filter);
        filters.add(filter);
        assertEquals(filters, flyFilter.getFilters());
        assertEquals(1, flyFilter.getFilters().size());
        assertEquals(filter, flyFilter.getFilters().get(0));
        FilterLongTimeOnGround filter2 = new FilterLongTimeOnGround(Duration.ofHours(1));
        flyFilter.addFilter(filter2);
        filters.add(filter2);
        assertEquals(filters, flyFilter.getFilters());
        assertEquals(2, flyFilter.getFilters().size());
        assertEquals(filter2, flyFilter.getFilters().get(1));
    }

    @Test
    void removeFilter() {
        flyFilter = new FlyFilter();
        List<Filter<Flight>> filters = new ArrayList<>();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        flyFilter.addFilter(filter);
        filters.add(filter);
        assertEquals(filters, flyFilter.getFilters());
        flyFilter.removeFilter(filter);
        assertEquals(0, flyFilter.getFilters().size());
        assertEquals(List.of(), flyFilter.getFilters());
    }

    @Test
    void addFilters() {
        flyFilter = new FlyFilter();
        List<Filter<Flight>> filters = new ArrayList<>();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        FilterWrongDateFrom filter2 = new FilterWrongDateFrom(Optional.of(LocalDateTime.now().minusHours(1)));
        flyFilter.addFilters(List.of(filter, filter2));
        filters.add(filter);
        filters.add(filter2);
        assertEquals(filters, flyFilter.getFilters());
    }

    @Test
    void addFiltersThrowsExceptionForNullFilters() {
        flyFilter = new FlyFilter();
        Assertions.assertThrows(NullPointerException.class, () -> flyFilter.addFilters(null));
    }

    @Test
    void filterOneFlightsWrongDepartureTime() {
        flyFilter = new FlyFilter();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        List<Flight> flights = List.of(new Flight(List.of(new Segment(time.plusHours(1), time))));
        List<Flight> expected = List.of();
        flyFilter.addFilter(filter);
        List<Flight> actual = flyFilter.filterFlights(flights);
        assertEquals(expected, actual);
    }

    @Test
    void filterOneFlightCorrectDepartureTime() {
        flyFilter = new FlyFilter();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        List<Flight> flights = List.of(new Flight(List.of(new Segment(time, time.plusHours(1)))));
        List<Flight> expected = flights;
        flyFilter.addFilter(filter);
        List<Flight> actual = flyFilter.filterFlights(flights);
        assertEquals(expected, actual);
        assertEquals(1, flyFilter.getFilters().size());
    }

    @Test
    void filterMultipleCorrectFlights() {
        flyFilter = new FlyFilter();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        FilterWrongDateFrom filter2 = new FilterWrongDateFrom(Optional.of(LocalDateTime.now().minusHours(1)));
        FilterLongTimeOnGround filter3 = new FilterLongTimeOnGround(Duration.ofHours(2));
        Flight flight = new Flight(List.of(new Segment(time, time.plusHours(1))));
        Flight flight2 = new Flight(List.of(new Segment(time.plusHours(1), time.plusHours(2))));
        Flight flight3 = new Flight(List.of(new Segment(time.plusHours(2), time.plusHours(3)), new Segment(time.plusHours(4), time.plusHours(5))));
        List<Flight> flights = List.of(flight, flight2, flight3);
        List<Flight> expected = flights;
        flyFilter.addFilters(List.of(filter, filter2, filter3));
        List<Flight> actual = flyFilter.filterFlights(flights);
        assertEquals(expected, actual);
        assertEquals(3, flyFilter.getFilters().size());
    }

    @Test
    void filterMultipleWrongFlights() {
        flyFilter = new FlyFilter();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        FilterWrongDateFrom filter2 = new FilterWrongDateFrom(Optional.of(LocalDateTime.now()));
        FilterLongTimeOnGround filter3 = new FilterLongTimeOnGround(Duration.ofHours(1));
        Flight flight = new Flight(List.of(new Segment(time.plusHours(1), time)));
        Flight flight2 = new Flight(List.of(new Segment(time.minusHours(5), time.plusHours(2))));
        Flight flight3 = new Flight(List.of(new Segment(time.plusHours(2), time.plusHours(3)), new Segment(time.plusHours(20), time.plusHours(30))));
        List<Flight> flights = List.of(flight, flight2, flight3);
        List<Flight> expected = List.of();
        flyFilter.addFilters(List.of(filter, filter2, filter3));
        List<Flight> actual = flyFilter.filterFlights(flights);
        assertEquals(expected, actual);
        assertEquals(3, flyFilter.getFilters().size());
    }

    @Test
    void filterBadFlights() {
        flyFilter = new FlyFilter();
        FilterWrongDepartureTime filter = new FilterWrongDepartureTime();
        Flight goodFlight = new Flight(List.of(new Segment(time, time.plusHours(1))));
        Flight badFlight = new Flight(List.of(new Segment(time.plusHours(10), time.plusHours(2))));
        List<Flight> flights = List.of(goodFlight, badFlight);
        List<Flight> expected = List.of(badFlight);
        flyFilter.addFilter(filter);
        List<Flight> actual = flyFilter.filterBadFlights(flights);
        assertEquals(expected, actual);
        assertEquals(1, flyFilter.getFilters().size());
    }
}