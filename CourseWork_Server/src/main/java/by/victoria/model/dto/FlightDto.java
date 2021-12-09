package by.victoria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlightDto implements Serializable {
    private Integer id;
    private String fromWhere;
    private String toWhere;
    private Date departureDate;
    private Date arrivalDate;
    private Integer countOfSeats;
    private Double cost;

    public FlightDto(String fromWhere, String toWhere, Date departureDate, Integer countOfSeats, Double cost) {
        this.fromWhere = fromWhere;
        this.toWhere = toWhere;
        this.departureDate = departureDate;
        this.countOfSeats = countOfSeats;
        this.cost = cost;
    }

    public FlightDto(String fromWhere, String toWhere) {
        this.fromWhere = fromWhere;
        this.toWhere = toWhere;
    }

    public FlightDto(String fromWhere, String toWhere, LocalDate date) {
        this.fromWhere = fromWhere;
        this.toWhere = toWhere;
        this.departureDate = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
