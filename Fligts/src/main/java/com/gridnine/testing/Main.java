package com.gridnine.testing;

import com.gridnine.testing.filters.FilterLongTimeOnGround;
import com.gridnine.testing.filters.FilterWrongDateFrom;
import com.gridnine.testing.filters.FilterWrongDepartureTime;
import com.gridnine.testing.filters.FlyFilter;
import com.gridnine.testing.model.Flight;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();
        FilterWrongDateFrom filterDate = new FilterWrongDateFrom(Optional.of(LocalDateTime.now()));
        FilterWrongDepartureTime filterDeparture = new FilterWrongDepartureTime();
        FilterLongTimeOnGround filterTimeOnGround = new FilterLongTimeOnGround(Duration.ofHours(2));

        System.out.println("Вывод полетов по фильтру - вылет до текущего момента времени - исключены из списка:");
        FlyFilter filter = new FlyFilter();
        filter.addFilter(filterDate);
        List<Flight> filteredFlights = filter.filterFlights(flights);
        filteredFlights.stream().map(Object::toString).forEach(System.out::println);
        System.out.println("Исключенные полеты:");
        List<Flight> filteredBadFlights = filter.filterBadFlights(flights);
        filteredBadFlights.stream().map(Object::toString).forEach(System.out::println);
        filter.removeFilter(filterDate);


        System.out.println("Вывод полетов по фильтру - дата прилета позже времени вылета - исключены из списка:");
        filter.addFilter(filterDeparture);
        filteredFlights = filter.filterFlights(flights);
        filteredFlights.stream().map(Object::toString).forEach(System.out::println);
        System.out.println("Исключенные полеты:");
        filteredBadFlights = filter.filterBadFlights(flights);
        filteredBadFlights.stream().map(Object::toString).forEach(System.out::println);
        filter.removeFilter(filterDeparture);


        System.out.println("Вывод полетов по фильтру - время на земле более 2х часов - исключены из списка:");
        filter.addFilter(filterTimeOnGround);
        filteredFlights = filter.filterFlights(flights);
        filteredFlights.stream().map(Object::toString).forEach(System.out::println);
        System.out.println("Исключенные полеты:");
        filteredBadFlights = filter.filterBadFlights(flights);
        filteredBadFlights.stream().map(Object::toString).forEach(System.out::println);
        filter.removeFilter(filterTimeOnGround);

    }
}


