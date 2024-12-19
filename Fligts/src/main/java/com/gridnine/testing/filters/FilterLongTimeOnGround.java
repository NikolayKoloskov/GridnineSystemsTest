package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.time.Duration;
import java.util.Objects;

public class FilterLongTimeOnGround implements Filter<Flight> {
    private final Duration maxTime;

    public FilterLongTimeOnGround(Duration maxTime) {
        this.maxTime = Objects.requireNonNull(maxTime);
    }

    @Override
    public boolean doFilter(Flight flight) {
        Duration timeInGround = Duration.ZERO;
        if (Objects.requireNonNull(flight).getSegments().size() == 1) {
            return true;
        }
        for (int i = 0; i < flight.getSegments().size(); i++) {
            if (i + 1 == flight.getSegments().size()) {
                break;
            }
            timeInGround = timeInGround.plus(Duration.between(flight.getSegments().get(i).getArrivalDate(),
                    flight.getSegments().get(i + 1).getDepartureDate()));
            if (timeInGround.compareTo(maxTime) > 0) {
                return false;
            }
        }
        return true;
    }
}
