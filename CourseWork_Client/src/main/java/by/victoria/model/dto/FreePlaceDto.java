package by.victoria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FreePlaceDto implements Serializable {
    private Integer flightId;
    private Integer place;
    private Double cost;
}
