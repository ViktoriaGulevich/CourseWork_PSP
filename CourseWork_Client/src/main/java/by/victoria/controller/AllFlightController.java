package by.victoria.controller;

import by.victoria.app.CurrentFlight;
import by.victoria.app.CurrentTicket;
import by.victoria.app.CurrentUser;
import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.FlightDto;
import by.victoria.model.dto.TicketDto;
import by.victoria.model.property.FlightDtoProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static by.victoria.app.CurrentFlight.getCurrentFlightDto;
import static by.victoria.app.CurrentFlight.getCurrentFlightDtoList;
import static by.victoria.connection.ConnectionHandler.getConnectionHandler;

public class AllFlightController {
    private final ObservableList<FlightDtoProperty> tableList = FXCollections.observableArrayList();
    @FXML
    private Button Button_ALLFLIGHTS__client_Bookingticket;
    @FXML
    private Button button_Bakc;
    @FXML
    private Button Button_ALLFLIGHTS_client_Buyticket;
    @FXML
    private TableView<FlightDtoProperty> Table_ALLFLIGHTS_client;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_client_Date;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_client_From;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_client_IdFlight;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_client_Price;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_client_Where;

    public void initialize() {
        Table_ALLFLIGHTS_client_IdFlight.setCellValueFactory(param -> param.getValue().idProperty());
        Table_ALLFLIGHTS_client_Date.setCellValueFactory(param -> param.getValue().departureDateProperty());
        Table_ALLFLIGHTS_client_From.setCellValueFactory(param -> param.getValue().fromWhereProperty());
        Table_ALLFLIGHTS_client_Price.setCellValueFactory(param -> param.getValue().costProperty());
        Table_ALLFLIGHTS_client_Where.setCellValueFactory(param -> param.getValue().toWhereProperty());

        updateTable();

        Button_ALLFLIGHTS__client_Bookingticket.setOnAction(event -> {
            if (chooseFlight()) {
                ConnectionHandler connectionHandler = getConnectionHandler();

                connectionHandler.writeObject(Command.BOOK_TICKET);
                connectionHandler.writeObject(CurrentUser.getCurrentUserDto());
                connectionHandler.writeObject(CurrentFlight.getCurrentFlightDto());

                TicketDto ticketDto = (TicketDto) connectionHandler.readObject();

                CurrentTicket.setCurrentTicketDto(ticketDto);

                ((Stage) Button_ALLFLIGHTS__client_Bookingticket.getScene().getWindow()).close();
                new ShowWindowFxml("bookedTicket");
            }
        });
        Button_ALLFLIGHTS_client_Buyticket.setOnAction(event -> {
            if (chooseFlight()) {
                ((Stage) Button_ALLFLIGHTS_client_Buyticket.getScene().getWindow()).close();
                new ShowWindowFxml("buyingTicket");
            }
        });
        button_Bakc.setOnAction(event -> {
            CurrentFlight.setCurrentFlightDto(null);
            CurrentFlight.setCurrentFlightDtoList(null);
            ((Stage) button_Bakc.getScene().getWindow()).close();
            new ShowWindowFxml("findFlightClient");
        });
    }

    public void updateTable() {
        List<FlightDto> flightDtoList;

        FlightDto currentFlightDto = getCurrentFlightDto();
        if (currentFlightDto != null) {
            flightDtoList = List.of(currentFlightDto);

        } else {
            List<FlightDto> currentFlightDtoList = getCurrentFlightDtoList();
            flightDtoList = Objects.requireNonNullElseGet(currentFlightDtoList, ArrayList::new);
        }


        tableList.clear();

        for (FlightDto flightDto : flightDtoList) {
            tableList.add(new FlightDtoProperty(flightDto));
        }
        Table_ALLFLIGHTS_client.setItems(tableList);
    }

    public boolean chooseFlight() {
        ConnectionHandler connectionHandler = getConnectionHandler();

        try {
            String idText = Table_ALLFLIGHTS_client.getSelectionModel().getSelectedItem().getId();

            connectionHandler.writeObject(Command.FIND_FLIGHT);
            connectionHandler.writeObject(Integer.valueOf(idText));

            FlightDto flightDto = (FlightDto) connectionHandler.readObject();
            CurrentFlight.setCurrentFlightDto(flightDto);

            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }
}
