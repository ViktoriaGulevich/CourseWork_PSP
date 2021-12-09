package by.victoria.controller;

import by.victoria.app.CurrentFlight;
import by.victoria.app.CurrentUser;
import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.FlightDto;
import by.victoria.model.property.FlightDtoProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class HelloAdminController {
    private final ObservableList<FlightDtoProperty> tableList = FXCollections.observableArrayList();
    @FXML
    private Button Button_add_admin;
    @FXML
    private Button Button_back_admin;
    @FXML
    private Button Button_edit_admin;
    @FXML
    private Button Button_search_admin;
    @FXML
    private Button Button_seeAllTikcets_admin;
    @FXML
    private TableView<FlightDtoProperty> Table_ALLFLIGHTS_admin;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_admin_Arrival;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_admin_Countseats;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_admin_Departure;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_admin_FROM;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_admin_IDflight;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_admin_Price;
    @FXML
    private TableColumn<FlightDtoProperty, String> Table_ALLFLIGHTS_admin_WHERE;
    @FXML
    private TextField Text_Field_IDforSearch_admin;

    public void initialize() {
        Table_ALLFLIGHTS_admin_IDflight.setCellValueFactory(param -> param.getValue().idProperty());
        Table_ALLFLIGHTS_admin_Countseats.setCellValueFactory(param -> param.getValue().countOfSeatsProperty());
        Table_ALLFLIGHTS_admin_Arrival.setCellValueFactory(param -> param.getValue().arrivalDateProperty());
        Table_ALLFLIGHTS_admin_Departure.setCellValueFactory(param -> param.getValue().departureDateProperty());
        Table_ALLFLIGHTS_admin_FROM.setCellValueFactory(param -> param.getValue().fromWhereProperty());
        Table_ALLFLIGHTS_admin_Price.setCellValueFactory(param -> param.getValue().costProperty());
        Table_ALLFLIGHTS_admin_WHERE.setCellValueFactory(param -> param.getValue().toWhereProperty());

        ConnectionHandler connectionHandler = ConnectionHandler.getConnectionHandler();

        updateTable();

        Button_add_admin.setOnAction(event -> {
            ((Stage) Button_add_admin.getScene().getWindow()).close();
            new ShowWindowFxml("addFlight");
        });
        Button_back_admin.setOnAction(event -> {
            CurrentUser.setCurrentUserDtoNull();
            ((Stage) Button_back_admin.getScene().getWindow()).close();
            new ShowWindowFxml("login");
        });
        Button_edit_admin.setOnAction(event -> {
            try {
                String id = Table_ALLFLIGHTS_admin.getSelectionModel().getSelectedItem().getId();

                connectionHandler.writeObject(Command.FIND_FLIGHT);
                connectionHandler.writeObject(Integer.valueOf(id));

                FlightDto flightDto = (FlightDto) connectionHandler.readObject();
                CurrentFlight.setCurrentFlightDto(flightDto);

                ((Stage) Button_edit_admin.getScene().getWindow()).close();
                new ShowWindowFxml("editFlight");
            } catch (NullPointerException e) {
            }
        });
        Button_search_admin.setOnAction(event -> {
            String textId = Text_Field_IDforSearch_admin.getText();
            if (textId.isBlank()) {
                updateTable();
            } else {
                connectionHandler.writeObject(Command.FIND_FLIGHT);
                connectionHandler.writeObject(Integer.valueOf(textId));

                FlightDto flightDto = (FlightDto) connectionHandler.readObject();
                updateTable(List.of(flightDto));
            }
        });
        Button_seeAllTikcets_admin.setOnAction(event -> {
            ((Stage) Button_seeAllTikcets_admin.getScene().getWindow()).close();
            new ShowWindowFxml("allFlightAdmin");
        });
    }

    public void updateTable(List<FlightDto> flightDtos) {
        tableList.clear();

        for (FlightDto flightDto : flightDtos) {
            tableList.add(new FlightDtoProperty(flightDto));
        }
        Table_ALLFLIGHTS_admin.setItems(tableList);
    }

    public void updateTable() {
        ConnectionHandler connectionHandler = ConnectionHandler.getConnectionHandler();

        connectionHandler.writeObject(Command.FIND_ALL_FLIGHTS);
        List<FlightDto> flightDtos = (List<FlightDto>) connectionHandler.readObject();

        updateTable(flightDtos);
    }
}
