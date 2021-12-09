package by.victoria.mapper;

import by.victoria.model.dto.FlightDto;
import by.victoria.model.dto.FreePlaceDto;
import by.victoria.model.dto.TicketDto;
import by.victoria.model.entity.Flight;
import by.victoria.model.entity.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketMapper {
    public TicketDto ticketToTicketDto(Ticket ticket) {
        FlightMapper flightMapper = new FlightMapper();
        TicketDto ticketDto = new TicketDto();

        ticketDto.setId(ticket.getId());
        ticketDto.setFlight(flightMapper.flightToFlightDto(ticket.getFlight()));
        ticketDto.setNumberOfSeat(ticket.getNumberOfSeat());
        ticketDto.setStatus(ticket.getStatuses().stream().findAny().get().getName());
        ticketDto.setCost(ticket.getCost());

        return ticketDto;
    }

    public Ticket ticketDtoToTicket(TicketDto ticketDto) {
        FlightMapper flightMapper = new FlightMapper();
        Ticket ticket = new Ticket();

        Integer id = ticketDto.getId();
        if (id != null) {
            ticket.setId(id);
        }
        FlightDto flight = ticketDto.getFlight();
        if (flight != null) {
            ticket.setFlight(flightMapper.flightDtoToFlight(flight));
        }
        Integer numberOfSeat = ticketDto.getNumberOfSeat();
        if (numberOfSeat != null) {
            ticket.setNumberOfSeat(numberOfSeat);
        }
        Double cost = ticketDto.getCost();
        if (cost != null) {
            ticket.setCost(cost);
        }

        return ticket;
    }

    public List<FreePlaceDto> ticketListToFreePlaceDtoList(List<Ticket> ticketList) {
        Map<Flight, List<Integer>> occupiedPlaces = new HashMap<>();

        ticketList.forEach(ticket -> {
            List<Integer> occupiedPlaceList = occupiedPlaces.get(ticket.getFlight());

            if (occupiedPlaceList != null) {
                List<Integer> integers = occupiedPlaces.get(ticket.getFlight());
                integers.add(ticket.getNumberOfSeat());
                occupiedPlaces.put(ticket.getFlight(), integers);
            } else {
                occupiedPlaces.put(ticket.getFlight(), new ArrayList<>() {{
                    add(ticket.getNumberOfSeat());
                }});
            }
        });

        return occupiedPlaces.entrySet()
                .stream()
                .map(entry -> {
                    Flight flight = entry.getKey();
                    List<FreePlaceDto> freePlaceDtoList = new ArrayList<>();
                    for (int i = 0; i < flight.getCountOfSeats(); i++) {
                        if (!entry.getValue().contains(i)) {
                            freePlaceDtoList.add(new FreePlaceDto(flight.getId(), i, flight.getCost()));
                        }
                    }
                    return freePlaceDtoList;
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<TicketDto> ticketListToTicketDtoList(List<Ticket> ticketList) {
        return ticketList.stream()
                .map(this::ticketToTicketDto)
                .collect(Collectors.toList());
    }
}
