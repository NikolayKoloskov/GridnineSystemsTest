package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterLongTimeOnGroundTest {
    FilterLongTimeOnGround filterLongTimeOnGround;
    Flight flight;

    LocalDateTime time = LocalDateTime.now().minusHours(10);


    @BeforeEach
    void setUp() {
        filterLongTimeOnGround = new FilterLongTimeOnGround(Duration.ofMinutes(120));
        flight = null;
    }

    @Test
    void doFilterOneFlightPositive() {
        flight = new Flight(List.of(new Segment(time, time.plusHours(1))));
        boolean result = filterLongTimeOnGround.doFilter(flight);
        assertTrue(result);
    }

    @Test
    void doFilterTwoFlightsPositiveOneHourOnGround() {
        flight = new Flight(List.of(new Segment(time, time.plusHours(1)), new Segment(time.plusHours(2), time.plusHours(3))));
        boolean result = filterLongTimeOnGround.doFilter(flight);
        assertTrue(result);
    }

    @Test
    void doFilterTwoFlightsNegative5HourOnGround() {
        flight = new Flight(List.of(new Segment(time, time.plusHours(1)), new Segment(time.plusHours(6), time.plusHours(7))));
        boolean result = filterLongTimeOnGround.doFilter(flight);
        assertFalse(result);
    }

    @Test
    void doFilterThreeFlightsPositiveTwoMinOnGround() {
        flight = new Flight(List.of(new Segment(time, time.plusHours(1)), new Segment(time.plusMinutes(61), time.plusHours(2)), new Segment(time.plusMinutes(121), time.plusHours(3))));
        boolean result = filterLongTimeOnGround.doFilter(flight);
        assertTrue(result);
    }

    @Test
    void throwsExceptionNullFlight() {
        assertThrows(NullPointerException.class, () -> filterLongTimeOnGround.doFilter(null));
    }

    @Test
    void throwsExceptionNullFilter() {
        assertThrows(NullPointerException.class, () -> new FilterLongTimeOnGround(null));
    }

}