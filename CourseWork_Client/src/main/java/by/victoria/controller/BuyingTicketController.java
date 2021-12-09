package by.victoria.controller;

import by.victoria.app.CurrentFlight;
import by.victoria.app.CurrentTicket;
import by.victoria.app.CurrentUser;
import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.TicketDto;
import by.victoria.model.dto.UserDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static by.victoria.connection.ConnectionHandler.getConnectionHandler;

public class BuyingTicketController {
    @FXML
    private Button Button_client_buyticket_BACK;

    @FXML
    private Button Button_client_buyticket_BUY;

    @FXML
    private TextField Text_Field_client_buyticket_Citizenship;

    @FXML
    private TextField Text_Field_client_buyticket_NumberPassport;

    @FXML
    private TextField Text_Field_client_buyticket_Prc;

    @FXML
    private TextField Text_Field_client_buyticket_name;

    @FXML
    private TextField Text_Field_client_buyticket_surname;

    public void initialize() {
        Text_Field_client_buyticket_Prc.setText(CurrentFlight.getCurrentFlightDto().getCost().toString());

        Button_client_buyticket_BACK.setOnAction(event -> {
            CurrentFlight.setCurrentFlightDto(null);
            CurrentFlight.setCurrentFlightDtoList(null);
            ((Stage) Button_client_buyticket_BACK.getScene().getWindow()).close();
            new ShowWindowFxml("allFlight");
        });
        Button_client_buyticket_BUY.setOnAction(event -> {
            String citizenship = Text_Field_client_buyticket_Citizenship.getText();
            String passport = Text_Field_client_buyticket_NumberPassport.getText();
            String name = Text_Field_client_buyticket_name.getText();
            String surname = Text_Field_client_buyticket_surname.getText();
            if (!(citizenship.isBlank() || passport.isBlank() || name.isBlank() || surname.isBlank())) {
                UserDto userDto = CurrentUser.getCurrentUserDto();
                userDto.setCitizenship(citizenship);
                userDto.setName(name);
                userDto.setPassportNumber(passport);
                userDto.setSurname(surname);

                ConnectionHandler connectionHandler = getConnectionHandler();

                connectionHandler.writeObject(Command.BUY_TICKET);
                connectionHandler.writeObject(userDto);
                connectionHandler.writeObject(CurrentFlight.getCurrentFlightDto());
                TicketDto ticketDto = (TicketDto) connectionHandler.readObject();

                CurrentTicket.setCurrentTicketDto(ticketDto);
                ((Stage) Button_client_buyticket_BUY.getScene().getWindow()).close();
                new ShowWindowFxml("boughtTicket");
            }
        });
    }
}
