package by.victoria.model.property;

import by.victoria.app.DateParser;
import by.victoria.model.dto.FlightDto;
import by.victoria.model.dto.TicketDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TicketDtoProperty {
    private StringProperty ticketId;
    private StringProperty flightId;
    private StringProperty fromWhere;
    private StringProperty toWhere;
    private StringProperty departureDate;
    private StringProperty arrivalDate;
    private StringProperty placeNumber;
    private StringProperty cost;
    private StringProperty status;


    public TicketDtoProperty(TicketDto ticketDto) {
        this.ticketId = new SimpleStringProperty(ticketDto.getId().toString());
        this.flightId = new SimpleStringProperty(ticketDto.getFlight().getId().toString());
        this.status = new SimpleStringProperty(ticketDto.getStatus());
        this.fromWhere = new SimpleStringProperty(ticketDto.getFlight().getFromWhere());
        this.toWhere = new SimpleStringProperty(ticketDto.getFlight().getToWhere());
        this.departureDate = new SimpleStringProperty(DateParser.dateToString(ticketDto.getFlight().getDepartureDate()));
        this.arrivalDate = new SimpleStringProperty(DateParser.dateToString(ticketDto.getFlight().getArrivalDate()));
        this.placeNumber = new SimpleStringProperty(ticketDto.getNumberOfSeat().toString());
        this.cost = new SimpleStringProperty(ticketDto.getCost().toString());
    }

    public TicketDto toTicketDto() {
        String placeNumber = this.placeNumber.get();
        String fromWhere = this.fromWhere.get();
        String toWhere = this.toWhere.get();
        FlightDto flightDto = new FlightDto(fromWhere, toWhere);
        return new TicketDto(flightDto, Integer.valueOf(placeNumber));
    }

    public String getTicketId() {
        return ticketId.get();
    }

    public void setTicketId(String ticketId) {
        this.ticketId.set(ticketId);
    }

    public StringProperty ticketIdProperty() {
        return ticketId;
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

    public String getPlaceNumber() {
        return placeNumber.get();
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber.set(placeNumber);
    }

    public StringProperty placeNumberProperty() {
        return placeNumber;
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

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }
}
