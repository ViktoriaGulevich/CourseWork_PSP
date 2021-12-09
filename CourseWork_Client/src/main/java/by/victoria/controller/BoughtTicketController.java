package by.victoria.controller;

import by.victoria.app.CurrentTicket;
import by.victoria.app.CurrentUser;
import by.victoria.app.DateParser;
import by.victoria.app.ShowWindowFxml;
import by.victoria.model.dto.TicketDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class BoughtTicketController {
    @FXML
    private Button Button_client_boughtticlet_ALLBOUGHTTICKETS;
    @FXML
    private Button Button_client_boughtticlet_BACK;
    @FXML
    private Button Button_client_boughtticlet_printfile_NO;
    @FXML
    private Button Button_client_boughtticlet_printfile_YES;
    @FXML
    private TextField Text_Field_client_boughtticlet_PLACE;
    @FXML
    private TextField Text_Field_client_boughtticlet_DATEARRIVAL;
    @FXML
    private TextField Text_Field_client_boughtticlet_DATEDEPARTURE;
    @FXML
    private TextField Text_Field_client_boughtticlet_FROM;
    @FXML
    private TextField Text_Field_client_boughtticlet_NUMBERFLIGHT;
    @FXML
    private TextField Text_Field_client_boughtticlet_PRICE;
    @FXML
    private TextField Text_Field_client_boughtticlet_WHERE;

    public void initialize() {
        TicketDto ticketDto = CurrentTicket.getCurrentTicketDto();

        Text_Field_client_boughtticlet_PLACE.setText(ticketDto.getNumberOfSeat().toString());
        Text_Field_client_boughtticlet_DATEARRIVAL.setText(ticketDto.getFlight().getArrivalDate().toString());
        Text_Field_client_boughtticlet_DATEDEPARTURE.setText(ticketDto.getFlight().getDepartureDate().toString());
        Text_Field_client_boughtticlet_FROM.setText(ticketDto.getFlight().getFromWhere());
        Text_Field_client_boughtticlet_NUMBERFLIGHT.setText(ticketDto.getFlight().getId().toString());
        Text_Field_client_boughtticlet_PRICE.setText(ticketDto.getCost().toString());
        Text_Field_client_boughtticlet_WHERE.setText(ticketDto.getFlight().getToWhere());


        Button_client_boughtticlet_BACK.setOnAction(event -> {
            ((Stage) Button_client_boughtticlet_BACK.getScene().getWindow()).close();
            new ShowWindowFxml("buyingTicket");
        });
        Button_client_boughtticlet_ALLBOUGHTTICKETS.setOnAction(event -> {
            ((Stage) Button_client_boughtticlet_ALLBOUGHTTICKETS.getScene().getWindow()).close();
            new ShowWindowFxml("allBoughtTickets");
        });
        Button_client_boughtticlet_printfile_NO.setOnAction(event -> {
            ((Stage) Button_client_boughtticlet_printfile_NO.getScene().getWindow()).close();
            new ShowWindowFxml("helloUser");
        });
        Button_client_boughtticlet_printfile_YES.setOnAction(event -> {
            String ticket = "\tTicket\n\n" +
                    "--------------------------------------------------" + "\n\n" +
                    "ticket №  : " + ticketDto.getId() + "\n" +
                    "flight №  : " + ticketDto.getFlight().getId() + "\n" +
                    "from      : " + ticketDto.getFlight().getFromWhere() + "\n" +
                    "to        : " + ticketDto.getFlight().getToWhere() + "\n" +
                    "departure : " + DateParser.dateToString(ticketDto.getFlight().getDepartureDate()) + "\n" +
                    "arrival   : " + DateParser.dateToString(ticketDto.getFlight().getArrivalDate()) + "\n" +
                    "place     : " + ticketDto.getNumberOfSeat() + "\n\n" +
                    "--------------------------------------------------";

            try {
                FileWriter fileWriter = new FileWriter(CurrentUser.getCurrentUserDto().getSurname() + ".txt");
                fileWriter.write(ticket);
                fileWriter.flush();
            } catch (IOException e) {
            }
        });
    }
}
