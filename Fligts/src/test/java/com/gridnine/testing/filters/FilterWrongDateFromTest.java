package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FilterWrongDateFromTest {
    FilterWrongDateFrom filter;
    Flight flight;

    LocalDateTime time = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        filter = new FilterWrongDateFrom(Optional.of(LocalDateTime.now()));
        flight = null;
    }

    @Test
    void doFilterWrongTime() {
        flight = new Flight(List.of(new Segment(time.minusHours(2), time)));
        assertFalse(filter.doFilter(flight));
    }

    @Test
    void doFilterCorrectTime() {
        flight = new Flight(List.of(new Segment(time.plusHours(1), time.plusHours(2))));
        assertTrue(filter.doFilter(flight));
    }

    @Test
    void doFilterNull() {
        assertThrowsExactly(NullPointerException.class, () -> filter.doFilter(null));
    }

    @Test
    void doFilterEmpty() {
        flight = new Flight(List.of());
        assertFalse(filter.doFilter(flight));
    }

    @Test
    void throwsExceptionOnNullTime() {
        assertThrowsExactly(NullPointerException.class, () -> new FilterWrongDateFrom(null));
    }
}