package by.victoria.app;

import by.victoria.model.dto.FlightDto;

import java.util.List;

public class CurrentFlight {
    private static CurrentFlight currentFlight;
    private FlightDto flightDto;
    private List<FlightDto> flightDtoList;

    private CurrentFlight() {
    }

    public static FlightDto getCurrentFlightDto() {
        if (currentFlight == null) {
            currentFlight = new CurrentFlight();
        }
        return currentFlight.flightDto;
    }

    public static void setCurrentFlightDto(FlightDto flightDto) {
        if (currentFlight == null) {
            currentFlight = new CurrentFlight();
        }
        currentFlight.flightDto = flightDto;
    }

    public static List<FlightDto> getCurrentFlightDtoList() {
        if (currentFlight == null) {
            currentFlight = new CurrentFlight();
        }
        return currentFlight.flightDtoList;
    }

    public static void setCurrentFlightDtoList(List<FlightDto> flightDtoList) {
        if (currentFlight == null) {
            currentFlight = new CurrentFlight();
        }
        currentFlight.flightDtoList = flightDtoList;
    }
}
