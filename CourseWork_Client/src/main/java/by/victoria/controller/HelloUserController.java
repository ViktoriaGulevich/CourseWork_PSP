package by.victoria.controller;

import by.victoria.app.CurrentFlight;
import by.victoria.app.CurrentTicket;
import by.victoria.app.CurrentUser;
import by.victoria.app.ShowWindowFxml;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HelloUserController {
    @FXML
    public Button Button_mainmenu_bookedtickets;
    @FXML
    public Button Button_mainmenu_searchflght;
    @FXML
    public Button Button_mainmenu_logout;
    @FXML
    public Button Button_mainmenu_boughtickets;

    public void initialize() {
        CurrentFlight.setCurrentFlightDto(null);
        CurrentFlight.setCurrentFlightDtoList(null);

        Button_mainmenu_bookedtickets.setOnAction(event -> {
            ((Stage) Button_mainmenu_bookedtickets.getScene().getWindow()).close();
            new ShowWindowFxml("allBookedTickets");
        });
        Button_mainmenu_searchflght.setOnAction(event -> {
            ((Stage) Button_mainmenu_searchflght.getScene().getWindow()).close();
            new ShowWindowFxml("findFlightClient");
        });
        Button_mainmenu_logout.setOnAction(event -> {
            CurrentUser.setCurrentUserDtoNull();
            CurrentFlight.setCurrentFlightDto(null);
            CurrentTicket.setCurrentTicketDto(null);
            ((Stage) Button_mainmenu_logout.getScene().getWindow()).close();
            new ShowWindowFxml("login");
        });
        Button_mainmenu_boughtickets.setOnAction(event -> {
            ((Stage) Button_mainmenu_logout.getScene().getWindow()).close();
            new ShowWindowFxml("allBoughtTickets");
        });
    }
}
