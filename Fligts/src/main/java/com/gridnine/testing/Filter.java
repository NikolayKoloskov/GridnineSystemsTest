package com.gridnine.testing;

import java.util.List;

public interface Filter {
    public List<Flight> filterWrongDate(List<Flight> flights);
    public List<Flight> filterWrongDepartureTime(List<Flight> flights);
    public List<Flight> filterLongTimeOnGround(List<Flight> flights, long time);
}
