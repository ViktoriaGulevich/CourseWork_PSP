package by.victoria.model.property;

import by.victoria.model.dto.FreePlaceDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FreePlaceDtoProperty {
    private StringProperty flightId;
    private StringProperty place;
    private StringProperty cost;

    public FreePlaceDtoProperty(FreePlaceDto freePlaceDto) {
        this.flightId = new SimpleStringProperty(freePlaceDto.getFlightId().toString());
        this.place = new SimpleStringProperty(freePlaceDto.getPlace().toString());
        this.cost = new SimpleStringProperty(freePlaceDto.getCost().toString());
    }

    public String getFlightId() {
        return flightId.get();
    }

    public void setFlightId(String flightId) {
        this.flightId.set(flightId);
    }

    public StringProperty flightIdProperty() {
        return flightId;
    }

    public String getPlace() {
        return place.get();
    }

    public void setPlace(String place) {
        this.place.set(place);
    }

    public StringProperty placeProperty() {
        return place;
    }

    public String getCost() {
        return cost.get();
    }

    public void setCost(String cost) {
        this.cost.set(cost);
    }

    public StringProperty costProperty() {
        return cost;
    }
}
