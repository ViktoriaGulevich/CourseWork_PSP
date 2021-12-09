package by.victoria.mapper;

import by.victoria.model.dto.FlightDto;
import by.victoria.model.entity.Flight;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FlightMapper {
    public FlightDto flightToFlightDto(Flight flight) {
        if (flight == null) {
            return null;
        }
        FlightDto flightDto = new FlightDto();

        flightDto.setId(flight.getId());
        flightDto.setArrivalDate(Date.from(flight
                .getArrivalDate()
                .atZone(ZoneId.systemDefault())
                .toInstant()
        ));
        flightDto.setDepartureDate(Date.from(flight
                .getDepartureDate()
                .atZone(ZoneId.systemDefault())
                .toInstant()
        ));
        flightDto.setFromWhere(flight.getFromWhere());
        flightDto.setToWhere(flight.getToWhere());
        flightDto.setCountOfSeats(flight.getCountOfSeats());
        flightDto.setCost(flight.getCost());

        return flightDto;
    }



    public Flight flightDtoToFlight(FlightDto flightDto) {
        Flight flight = new Flight();

        Integer id = flightDto.getId();
        if (id != null) {
            flight.setId(id);
        }
        Date arrivalDate = flightDto.getArrivalDate();
        if (arrivalDate != null) {
            flight.setArrivalDate(arrivalDate
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
        }
        Date departureDate = flightDto.getDepartureDate();
        if (departureDate != null) {
            flight.setDepartureDate(departureDate
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
            );
        }
        String fromWhere = flightDto.getFromWhere();
        if (fromWhere != null) {
            flight.setFromWhere(fromWhere);
        }
        String toWhere = flightDto.getToWhere();
        if (toWhere != null) {
            flight.setToWhere(toWhere);
        }
        Integer countOfSeats = flightDto.getCountOfSeats();
        if (countOfSeats != null) {
            flight.setCountOfSeats(countOfSeats);
        }
        Double cost = flightDto.getCost();
        if (cost != null) {
            flight.setCost(cost);
        }

        return flight;
    }

    public List<FlightDto> flightListToFlightDtoList(List<Flight> flightList) {
        return flightList.stream()
                .map(this::flightToFlightDto)
                .collect(Collectors.toList());
    }
}
