package by.victoria.controller;

import by.victoria.app.CurrentFlight;
import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.FlightDto;
import by.victoria.model.dto.FreePlaceDto;
import by.victoria.model.dto.TicketDto;
import by.victoria.model.property.FreePlaceDtoProperty;
import by.victoria.model.property.TicketDtoProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

import static by.victoria.connection.ConnectionHandler.getConnectionHandler;

public class AllFlightAdminController {
    private final ObservableList<TicketDtoProperty> tableTicketList = FXCollections.observableArrayList();
    private final ObservableList<FreePlaceDtoProperty> tableFreePlaceList = FXCollections.observableArrayList();
    @FXML
    public Button Button_BACK_admin;
    @FXML
    private Button Button_admin_changePrice;
    @FXML
    private Button Button_searchFlight_admin;
    @FXML
    private Button Button_search_admin;
    @FXML
    private TableView<TicketDtoProperty> Table_AllBBTickets_admin;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_AllBBTickets_admin_IdFlight;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_AllBBTickets_admin_IdSeat;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_AllBBTickets_admin_IdTicket;
    @FXML
    private TableColumn<TicketDtoProperty, String> Table_AllBBTickets_admin_SatusTicket;
    @FXML
    private TableView<FreePlaceDtoProperty> Table_FreeSeats_admin;
    @FXML
    private TableColumn<FreePlaceDtoProperty, String> Table_FreeSeats_admin_IdFlight;
    @FXML
    private TableColumn<FreePlaceDtoProperty, String> Table_FreeSeats_admin_IdSeat;
    @FXML
    private TableColumn<FreePlaceDtoProperty, String> Table_FreeSeats_admin_PriceSeat;
    @FXML
    private TextField Text_Field_IdFlight_admin;
    @FXML
    private TextField Text_Field_IdTicket_admin;

    public void initialize() {
        ConnectionHandler connectionHandler = getConnectionHandler();

        Table_AllBBTickets_admin_IdFlight.setCellValueFactory(param -> param.getValue().flightIdProperty());
        Table_AllBBTickets_admin_IdSeat.setCellValueFactory(param -> param.getValue().placeNumberProperty());
        Table_AllBBTickets_admin_IdTicket.setCellValueFactory(param -> param.getValue().ticketIdProperty());
        Table_AllBBTickets_admin_SatusTicket.setCellValueFactory(param -> param.getValue().statusProperty());

        Table_FreeSeats_admin_IdFlight.setCellValueFactory(param -> param.getValue().flightIdProperty());
        Table_FreeSeats_admin_IdSeat.setCellValueFactory(param -> param.getValue().placeProperty());
        Table_FreeSeats_admin_PriceSeat.setCellValueFactory(param -> param.getValue().costProperty());

        updateTables();

        Button_BACK_admin.setOnAction(event -> {
            ((Stage) Button_BACK_admin.getScene().getWindow()).close();
            new ShowWindowFxml("helloAdmin",700);
        });
        Button_admin_changePrice.setOnAction(event -> {
            try {
                FreePlaceDtoProperty selectedItem = Table_FreeSeats_admin.getSelectionModel().getSelectedItem();

                connectionHandler.writeObject(Command.FIND_FLIGHT);
                connectionHandler.writeObject(Integer.valueOf(selectedItem.getFlightId()));

                FlightDto flightDto = (FlightDto) connectionHandler.readObject();
                CurrentFlight.setCurrentFlightDto(flightDto);

                ((Stage) Button_admin_changePrice.getScene().getWindow()).close();
                new ShowWindowFxml("editFlight");
            } catch (RuntimeException e) {
            }
        });
        Button_searchFlight_admin.setOnAction(event -> {
            try {
                connectionHandler.writeObject(Command.FIND_ALL_FREE_PLACES);
                List<FreePlaceDto> freePlaceDtos = (List<FreePlaceDto>) connectionHandler.readObject();

                String text = Text_Field_IdFlight_admin.getText();
                if (!text.isBlank()) {
                    Integer flightId = Integer.valueOf(text);

                    List<FreePlaceDto> freePlaceDtoList = freePlaceDtos.stream()
                            .filter(freePlaceDto -> freePlaceDto.getFlightId().equals(flightId))
                            .collect(Collectors.toList());

                    updateTablePlaces(freePlaceDtoList);
                } else {
                    updateTablePlaces(freePlaceDtos);
                }
            } catch (RuntimeException e) {
            }
        });
        Button_search_admin.setOnAction(event -> {
            try {
                connectionHandler.writeObject(Command.FIND_ALL_TICKETS);
                List<TicketDto> ticketDtos = (List<TicketDto>) connectionHandler.readObject();

                String text = Text_Field_IdTicket_admin.getText();
                if (!text.isBlank()) {
                    Integer ticketId = Integer.valueOf(text);

                    List<TicketDto> ticketDtoList = ticketDtos.stream()
                            .filter(ticketDto -> ticketDto.getId().equals(ticketId))
                            .collect(Collectors.toList());

                    updateTableTicket(ticketDtoList);
                } else {
                    updateTableTicket(ticketDtos);
                }
            } catch (RuntimeException e) {
            }
        });
    }

    public void updateTables() {
        ConnectionHandler connectionHandler = getConnectionHandler();

        connectionHandler.writeObject(Command.FIND_ALL_TICKETS);
        List<TicketDto> ticketDtos = (List<TicketDto>) connectionHandler.readObject();

        updateTableTicket(ticketDtos);

        connectionHandler.writeObject(Command.FIND_ALL_FREE_PLACES);
        List<FreePlaceDto> freePlaceDtos = (List<FreePlaceDto>) connectionHandler.readObject();

        updateTablePlaces(freePlaceDtos);
    }

    public void updateTableTicket(List<TicketDto> ticketDtos) {
        tableTicketList.clear();

        for (TicketDto ticketDto : ticketDtos) {
            tableTicketList.add(new TicketDtoProperty(ticketDto));
        }
        Table_AllBBTickets_admin.setItems(tableTicketList);
    }

    public void updateTablePlaces(List<FreePlaceDto> freePlaceDtos) {
        tableFreePlaceList.clear();

        for (FreePlaceDto freePlaceDto : freePlaceDtos) {
            tableFreePlaceList.add(new FreePlaceDtoProperty(freePlaceDto));
        }
        Table_FreeSeats_admin.setItems(tableFreePlaceList);
    }

}
