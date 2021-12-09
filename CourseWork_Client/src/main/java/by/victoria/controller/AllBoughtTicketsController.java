package by.victoria.controller;

import by.victoria.app.CurrentUser;
import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.TicketDto;
import by.victoria.model.property.TicketDtoProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.List;

import static by.victoria.connection.ConnectionHandler.getConnectionHandler;

public class AllBoughtTicketsController {
    private final ObservableList<TicketDtoProperty> tableList = FXCollections.observableArrayList();
    @FXML
    private Button Button_client_BACK;
    @FXML
    private Button Button_client_RETURNTICKET;
    @FXML
    private TableView<TicketDtoProperty> Table_ALLBOUGHTTICKETS_client;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_ALLBOUGHTTICKETS_client_PlaceNumber;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_ALLBOUGHTTICKETS_client_DATEARRIVAL;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_ALLBOUGHTTICKETS_client_DATEDEPARTURE;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_ALLBOUGHTTICKETS_client_FROM;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_ALLBOUGHTTICKETS_client_PRICE;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_ALLBOUGHTTICKETS_client_WHERE;

    public void initialize() {
        Table_ALLBOUGHTTICKETS_client_PlaceNumber.setCellValueFactory(param -> param.getValue().placeNumberProperty());
        Table_ALLBOUGHTTICKETS_client_DATEARRIVAL.setCellValueFactory(param -> param.getValue().arrivalDateProperty());
        Table_ALLBOUGHTTICKETS_client_DATEDEPARTURE.setCellValueFactory(param -> param.getValue().departureDateProperty());
        Table_ALLBOUGHTTICKETS_client_FROM.setCellValueFactory(param -> param.getValue().fromWhereProperty());
        Table_ALLBOUGHTTICKETS_client_PRICE.setCellValueFactory(param -> param.getValue().costProperty());
        Table_ALLBOUGHTTICKETS_client_WHERE.setCellValueFactory(param -> param.getValue().toWhereProperty());

        updateTable();

        Button_client_BACK.setOnAction(event -> {
            ((Stage) Button_client_BACK.getScene().getWindow()).close();
            new ShowWindowFxml("helloUser");
        });
        Button_client_RETURNTICKET.setOnAction(event -> {
            try {
                TicketDto ticketDto = Table_ALLBOUGHTTICKETS_client.getSelectionModel().getSelectedItem().toTicketDto();
                ConnectionHandler connectionHandler = getConnectionHandler();

                connectionHandler.writeObject(Command.RETURN_TICKET);
                connectionHandler.writeObject(ticketDto);
                connectionHandler.writeObject(CurrentUser.getCurrentUserDto());

                updateTable();
            } catch (NullPointerException e) {
            }
        });
    }

    public void updateTable() {
        ConnectionHandler connectionHandler = ConnectionHandler.getConnectionHandler();
        tableList.clear();

        connectionHandler.writeObject(Command.FIND_ALL_BOUGHT_TICKETS);
        connectionHandler.writeObject(CurrentUser.getCurrentUserDto().getId());
        List<TicketDto> ticketDtos = (List<TicketDto>) connectionHandler.readObject();
        for (TicketDto ticketDto : ticketDtos) {
            tableList.add(new TicketDtoProperty(ticketDto));
        }
        Table_ALLBOUGHTTICKETS_client.setItems(tableList);
    }
}
