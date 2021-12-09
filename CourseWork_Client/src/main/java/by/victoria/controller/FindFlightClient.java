package by.victoria.controller;

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
import java.util.List;

import static by.victoria.app.CurrentFlight.setCurrentFlightDto;
import static by.victoria.app.CurrentFlight.setCurrentFlightDtoList;

public class FindFlightClient {
    @FXML
    private Button button_Menu;
    @FXML
    private Button Button_client_serchflight;
    @FXML
    private TextField Text_Field_FROM_client;
    @FXML
    private TextField Text_Field_WHERE_client;
    @FXML
    private DatePicker Text_Field_date_client;

    public void initialize() {
        ConnectionHandler connectionHandler = ConnectionHandler.getConnectionHandler();

        button_Menu.setOnAction(event -> {
            ((Stage) button_Menu.getScene().getWindow()).close();
            new ShowWindowFxml("helloUser");
        });
        Button_client_serchflight.setOnAction(event -> {
            String fromWhere = Text_Field_FROM_client.getText();
            String toWhere = Text_Field_WHERE_client.getText();
            LocalDate date = Text_Field_date_client.getValue();

            if (!fromWhere.isBlank() && !toWhere.isBlank()) {
                connectionHandler.writeObject(Command.FIND_FLIGHT_USE_PARAMS);

                if (date != null) {
                    connectionHandler.writeObject(new FlightDto(fromWhere, toWhere, date));

                    FlightDto flightDto = (FlightDto) connectionHandler.readObject();
                    setCurrentFlightDto(flightDto);
                } else {
                    connectionHandler.writeObject(new FlightDto(fromWhere, toWhere));

                    List<FlightDto> flightDtoList = (List<FlightDto>) connectionHandler.readObject();
                    setCurrentFlightDtoList(flightDtoList);
                }
            } else {
                connectionHandler.writeObject(Command.FIND_ALL_FLIGHTS);

                List<FlightDto> flightDtoList = (List<FlightDto>) connectionHandler.readObject();
                setCurrentFlightDtoList(flightDtoList);
            }
            ((Stage) Button_client_serchflight.getScene().getWindow()).close();
            new ShowWindowFxml("allFlight");
        });
    }
}
