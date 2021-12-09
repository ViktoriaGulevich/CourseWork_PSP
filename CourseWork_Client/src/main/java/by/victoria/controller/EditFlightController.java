package by.victoria.controller;

import by.victoria.app.CurrentFlight;
import by.victoria.app.DateParser;
import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.FlightDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditFlightController {
    @FXML
    private Button Button_Back_admin;
    @FXML
    private Button Button_Edit_admin;
    @FXML
    private TextField Text_Field_Edit_admin_CountSeats;
    @FXML
    private TextField Text_Field_Edit_admin_DATEARRIVAL;
    @FXML
    private TextField Text_Field_Edit_admin_DATEGEPARTURE;
    @FXML
    private TextField Text_Field_Edit_admin_FROM;
    @FXML
    private TextField Text_Field_Edit_admin_IDflight;
    @FXML
    private TextField Text_Field_Edit_admin_Price;
    @FXML
    private TextField Text_Field_Edit_admin_WHERE;

    public void initialize() {
        FlightDto flightDto = CurrentFlight.getCurrentFlightDto();
        Text_Field_Edit_admin_CountSeats.setText(flightDto.getCountOfSeats().toString());
        Text_Field_Edit_admin_DATEARRIVAL.setText(DateParser.dateToString(flightDto.getArrivalDate()));
        Text_Field_Edit_admin_DATEGEPARTURE.setText(DateParser.dateToString(flightDto.getDepartureDate()));
        Text_Field_Edit_admin_FROM.setText(flightDto.getFromWhere());
        Text_Field_Edit_admin_IDflight.setText(flightDto.getId().toString());
        Text_Field_Edit_admin_Price.setText(flightDto.getCost().toString());
        Text_Field_Edit_admin_WHERE.setText(flightDto.getToWhere());

        Button_Back_admin.setOnAction(event -> {
            ((Stage) Button_Back_admin.getScene().getWindow()).close();
            new ShowWindowFxml("helloAdmin",700);
        });
        Button_Edit_admin.setOnAction(event -> {
            try {
                String seatsText = Text_Field_Edit_admin_CountSeats.getText();
                String datearrivalText = Text_Field_Edit_admin_DATEARRIVAL.getText();
                String dategepartureText = Text_Field_Edit_admin_DATEGEPARTURE.getText();
                String fromWhere = Text_Field_Edit_admin_FROM.getText();
                String cost = Text_Field_Edit_admin_Price.getText();
                String toWhere = Text_Field_Edit_admin_WHERE.getText();

                flightDto.setCost(Double.valueOf(cost));
                flightDto.setCountOfSeats(Integer.valueOf(seatsText));
                flightDto.setToWhere(toWhere);
                flightDto.setFromWhere(fromWhere);
                flightDto.setDepartureDate(DateParser.stringToDate(dategepartureText));
                flightDto.setArrivalDate(DateParser.stringToDate(datearrivalText));

                CurrentFlight.setCurrentFlightDto(flightDto);

                ConnectionHandler connectionHandler = ConnectionHandler.getConnectionHandler();

                connectionHandler.writeObject(Command.EDIT_FLIGHT);
                connectionHandler.writeObject(flightDto);
            } catch (RuntimeException e) {
            }
        });
    }
}

