package by.victoria.controller;

import by.victoria.app.CurrentFlight;
import by.victoria.app.CurrentTicket;
import by.victoria.app.ShowWindowFxml;
import by.victoria.model.dto.TicketDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookedTicketController {
    @FXML
    private Button Button_client_bookdticlet_BACK;

    @FXML
    private Button Button_client_bookedticlet_ALLBOOKDTICKETS;

    @FXML
    private TextField Text_Field_client_bookedticlet_PLACE;

    @FXML
    private TextField Text_Field_client_bookedticlet_DATEARRIVAL;

    @FXML
    private TextField Text_Field_client_bookedticlet_DATEDEPARTURE;

    @FXML
    private TextField Text_Field_client_bookedticlet_FROM;

    @FXML
    private TextField Text_Field_client_bookedticlet_NUMBERFLIGHT;

    @FXML
    private TextField Text_Field_client_bookedticlet_PRICE;

    @FXML
    private TextField Text_Field_client_bookedticlet_WHERE;

    public void initialize() {
        TicketDto ticketDto = CurrentTicket.getCurrentTicketDto();
        Text_Field_client_bookedticlet_PLACE.setText(ticketDto.getNumberOfSeat().toString());
        Text_Field_client_bookedticlet_DATEARRIVAL.setText(ticketDto.getFlight().getArrivalDate().toString());
        Text_Field_client_bookedticlet_DATEDEPARTURE.setText(ticketDto.getFlight().getDepartureDate().toString());
        Text_Field_client_bookedticlet_FROM.setText(ticketDto.getFlight().getFromWhere());
        Text_Field_client_bookedticlet_NUMBERFLIGHT.setText(ticketDto.getFlight().getId().toString());
        Text_Field_client_bookedticlet_PRICE.setText(ticketDto.getCost().toString());
        Text_Field_client_bookedticlet_WHERE.setText(ticketDto.getFlight().getToWhere());

        Button_client_bookdticlet_BACK.setOnAction(event -> {
            CurrentFlight.setCurrentFlightDto(null);
            CurrentFlight.setCurrentFlightDtoList(null);
            ((Stage) Button_client_bookdticlet_BACK.getScene().getWindow()).close();
            new ShowWindowFxml("allFlight");
        });
        Button_client_bookedticlet_ALLBOOKDTICKETS.setOnAction(event -> {
            ((Stage) Button_client_bookedticlet_ALLBOOKDTICKETS.getScene().getWindow()).close();
            new ShowWindowFxml("allBookedTickets");
        });

    }
}
