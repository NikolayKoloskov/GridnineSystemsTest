package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterWrongDepartureTimeTest {
    FilterWrongDepartureTime filter;
    Flight flight;

    LocalDateTime time = LocalDateTime.now().minusHours(10);

    @BeforeEach
    void setUp() {
        filter = new FilterWrongDepartureTime();
        flight = null;

    }

    @Test
    void doFilterCorrectTime() {
        flight = new Flight(List.of(new Segment(time, time.plusHours(1))));
        assertTrue(filter.doFilter(flight));
    }

    @Test
    void doFilterWrongTime() {
        flight = new Flight(List.of(new Segment(time.plusHours(12), time.plusHours(11))));
        assertFalse(filter.doFilter(flight));
    }

    @Test
    void doFilterEqualTime() {
        flight = new Flight(List.of(new Segment(time, time)));
        assertFalse(filter.doFilter(flight));
    }

    @Test
    void doFilterNullThrowsException() {
        assertThrows(NullPointerException.class, () -> filter.doFilter(null));
    }
}