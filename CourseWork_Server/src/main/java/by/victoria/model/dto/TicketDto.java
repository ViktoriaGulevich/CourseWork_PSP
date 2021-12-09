package by.victoria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketDto implements Serializable {
    private Integer id;
    private FlightDto flight;
    private Integer numberOfSeat;
    private Double cost;
    private String status;

    public TicketDto(FlightDto flight, Integer numberOfSeat) {
        this.flight = flight;
        this.numberOfSeat = numberOfSeat;
    }
}
