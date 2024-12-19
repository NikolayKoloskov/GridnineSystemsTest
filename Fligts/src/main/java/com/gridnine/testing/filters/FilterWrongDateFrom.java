package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class FilterWrongDateFrom implements Filter<Flight> {
    private final LocalDateTime from;

    public FilterWrongDateFrom(Optional<LocalDateTime> from) {
        this.from = from.orElseGet(LocalDateTime::now);
    }

    @Override
    public boolean doFilter(Flight flight) {
        return Objects.requireNonNull(flight).getSegments().stream().anyMatch(segment -> segment.getDepartureDate().isAfter(from));
    }
}
