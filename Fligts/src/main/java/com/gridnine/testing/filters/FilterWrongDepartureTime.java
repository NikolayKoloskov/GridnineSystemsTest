package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.util.Objects;

public class FilterWrongDepartureTime implements Filter<Flight> {
    @Override
    public boolean doFilter(Flight flight) {
        return Objects.requireNonNull(flight).getSegments()
                .stream()
                .anyMatch(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate()));
    }
}
