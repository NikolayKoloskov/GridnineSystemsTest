package com.gridnine.testing.filters;

import com.gridnine.testing.model.Flight;

public interface Filter<T extends Flight> {
    boolean doFilter(T flight);
}
