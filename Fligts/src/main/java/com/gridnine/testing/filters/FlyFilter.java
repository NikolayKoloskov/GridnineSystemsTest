package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FlyFilter {
    private final List<Filter<Flight>> filters = new ArrayList<>();

    public void addFilter(Filter<Flight> filter) {
        filters.add(Objects.requireNonNull(filter));
    }

    public void addFilters(List<Filter<Flight>> filtersToAdd) {
        filters.addAll(Objects.requireNonNull(filtersToAdd));

    }


    public void removeFilter(Filter<Flight> filter) {
        filters.remove(Objects.requireNonNull(filter));
    }

    public List<Filter<Flight>> getFilters() {
        return filters;
    }

    public List<Flight> filterFlights(final List<Flight> flights) {
        final List<Flight> result = new ArrayList<>();
        for (final Flight flight : Objects.requireNonNull(flights)) {
            boolean valid = true;
            for (final Filter<Flight> filter : filters) {
                if (!filter.doFilter(flight)) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                result.add(flight);
            }
        }
        return result;
    }

    public List<Flight> filterBadFlights(final List<Flight> flights) {
        final List<Flight> result = new ArrayList<>();
        for (final Flight flight : Objects.requireNonNull(flights)) {
            boolean valid = false;
            for (final Filter<Flight> filter : filters) {
                if (!filter.doFilter(flight)) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                result.add(flight);
            }
        }
        return result;
    }

}
