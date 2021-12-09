package by.victoria.model.property;

import by.victoria.app.DateParser;
import by.victoria.model.dto.FlightDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FlightDtoProperty {
    private StringProperty id;
    private StringProperty fromWhere;
    private StringProperty toWhere;
    private StringProperty departureDate;
    private StringProperty arrivalDate;
    private StringProperty countOfSeats;
    private StringProperty cost;

    public FlightDtoProperty(FlightDto flightDto) {
        this.id = new SimpleStringProperty(flightDto.getId().toString());
        this.fromWhere = new SimpleStringProperty(flightDto.getFromWhere());
        this.toWhere = new SimpleStringProperty(flightDto.getToWhere());
        this.departureDate = new SimpleStringProperty(DateParser.dateToString(flightDto.getDepartureDate()));
        this.arrivalDate = new SimpleStringProperty(DateParser.dateToString(flightDto.getArrivalDate()));
        this.countOfSeats = new SimpleStringProperty(flightDto.getCountOfSeats().toString());
        this.cost = new SimpleStringProperty(flightDto.getCost().toString());
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getFromWhere() {
        return fromWhere.get();
    }

    public void setFromWhere(String fromWhere) {
        this.fromWhere.set(fromWhere);
    }

    public StringProperty fromWhereProperty() {
        return fromWhere;
    }

    public String getToWhere() {
        return toWhere.get();
    }

    public void setToWhere(String toWhere) {
        this.toWhere.set(toWhere);
    }

    public StringProperty toWhereProperty() {
        return toWhere;
    }

    public String getDepartureDate() {
        return departureDate.get();
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate.set(departureDate);
    }

    public StringProperty departureDateProperty() {
        return departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate.get();
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate.set(arrivalDate);
    }

    public StringProperty arrivalDateProperty() {
        return arrivalDate;
    }

    public String getCountOfSeats() {
        return countOfSeats.get();
    }

    public void setCountOfSeats(String countOfSeats) {
        this.countOfSeats.set(countOfSeats);
    }

    public StringProperty countOfSeatsProperty() {
        return countOfSeats;
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
