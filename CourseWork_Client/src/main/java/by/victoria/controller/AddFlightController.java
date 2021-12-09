package by.victoria.controller;

import by.victoria.app.DateParser;
import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.FlightDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

import static by.victoria.connection.ConnectionHandler.getConnectionHandler;

public class AddFlightController {
    @FXML
    public TextField Text_Field_Add_admin_Time;
    @FXML
    public TextField Text_Field_Add_admin_Time1;
    @FXML
    public DatePicker Text_Field_Add_admin_DATEARRIVAL;
    @FXML
    private Button Button_Add_admin;
    @FXML
    private Button Button_Back_admin;
    @FXML
    private TextField Text_Field_Add_admin_CountSeats;
    @FXML
    private DatePicker Text_Field_Add_admin_DATEDEPARTURE;
    @FXML
    private TextField Text_Field_Add_admin_FROM;
    @FXML
    private TextField Text_Field_Add_admin_Price;
    @FXML
    private TextField Text_Field_Add_admin_WHERE;

    public void initialize() {
        Button_Back_admin.setOnAction(event -> {
            ((Stage) Button_Back_admin.getScene().getWindow()).close();
            new ShowWindowFxml("helloAdmin", 700);
        });
        Button_Add_admin.setOnAction(event -> {
            try {
                String countSeatsText = Text_Field_Add_admin_CountSeats.getText();
                String fromText = Text_Field_Add_admin_FROM.getText();
                String priceText = Text_Field_Add_admin_Price.getText();
                String whereText = Text_Field_Add_admin_WHERE.getText();
                LocalDate localDate = Text_Field_Add_admin_DATEDEPARTURE.getValue();
                String timeText = Text_Field_Add_admin_Time.getText();
                FlightDto flightDto = new FlightDto(
                        fromText,
                        whereText,
                        DateParser.localDateAndStringTimeToDate(localDate, timeText),
                        Integer.valueOf(countSeatsText),
                        Double.valueOf(priceText));
                LocalDate datearrivalValue = Text_Field_Add_admin_DATEARRIVAL.getValue();
                String time1Text = Text_Field_Add_admin_Time1.getText();
                flightDto.setArrivalDate(DateParser.localDateAndStringTimeToDate(datearrivalValue, time1Text));

                ConnectionHandler connectionHandler = getConnectionHandler();

                connectionHandler.writeObject(Command.ADD_FLIGHT);
                connectionHandler.writeObject(flightDto);
            } catch (RuntimeException e) {
                Text_Field_Add_admin_CountSeats.setText("");
                Text_Field_Add_admin_FROM.setText("");
                Text_Field_Add_admin_Price.setText("");
                Text_Field_Add_admin_WHERE.setText("");
                Text_Field_Add_admin_DATEDEPARTURE.setValue(LocalDate.now());
                Text_Field_Add_admin_Time.setText("00:00:00");
                Text_Field_Add_admin_DATEARRIVAL.setValue(LocalDate.now());
                Text_Field_Add_admin_Time1.setText("00:00:00");
            }
        });
    }
}
