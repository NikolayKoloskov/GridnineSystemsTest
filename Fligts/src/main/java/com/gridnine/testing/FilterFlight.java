package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Фильтрация полетов и возвращаемые отфильтрованные полеты.
 */
public class FilterFlight implements Filter {

    public List<Flight> filterWrongDate(List<Flight> flights) {
        if (flights.isEmpty()) {
            return flights;
        }
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .toList();
    }

    @Override
    public List<Flight> filterWrongDepartureTime(List<Flight> flights) {
        if (flights.isEmpty()) {
            return flights;
        }
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .anyMatch(segment -> segment.getDepartureDate().isBefore(segment.getArrivalDate())))
                .toList();
    }


    @Override
    public List<Flight> filterLongTimeOnGround(List<Flight> flights, long maxTime) {

        List<Flight> result = new ArrayList<>();
        long maxTimeOnGround = maxTime;
        if (maxTime < 0) {
            throw new IllegalArgumentException("Время не может быть меньше 0.");
        }
        for (int i = 0; i < flights.size(); i++) {
            long timeInGround = 0;
            for (int j = 0; j < flights.get(i).getSegments().size(); j++) {
                if (j + 1 == flights.get(i).getSegments().size()) {
                    break;
                }
                timeInGround += ChronoUnit.MINUTES.between(flights.get(i).getSegments().get(j).getArrivalDate(),
                        flights.get(i).getSegments().get(j + 1).getDepartureDate());
                if (timeInGround > maxTimeOnGround) {
                    break;
                }
            }
            if (timeInGround < maxTimeOnGround) {
                result.add(flights.get(i));
            }
        }
        return result;
    }
}
