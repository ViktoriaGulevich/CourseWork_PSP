package by.victoria.controller;

import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.UserDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static by.victoria.app.CurrentUser.setCurrentUserDto;
import static by.victoria.command.Command.LOGIN;
import static by.victoria.connection.ConnectionHandler.getConnectionHandler;

public class LoginController {
    @FXML
    private Button buttonId_enter_authorization;

    @FXML
    private Button buttonId_registration_authorization;

    @FXML
    private TextField text_Field_login_autorization;

    @FXML
    private TextField text_Field_password_autorization;

    public void initialize() {
        ConnectionHandler connectionHandler = getConnectionHandler();

        buttonId_enter_authorization.setOnAction(event -> {
            UserDto userDto = new UserDto(text_Field_login_autorization.getText(), text_Field_password_autorization.getText());

            connectionHandler.writeObject(LOGIN);
            connectionHandler.writeObject(userDto);

            Command command = (Command) connectionHandler.readObject();

            switch (command) {
                case USER -> {
                    userDto = (UserDto) connectionHandler.readObject();
                    setCurrentUserDto(userDto);
                    ((Stage) buttonId_enter_authorization.getScene().getWindow()).close();
                    new ShowWindowFxml("helloUser");
                }
                case ADMIN -> {
                    userDto = (UserDto) connectionHandler.readObject();
                    setCurrentUserDto(userDto);
                    ((Stage) buttonId_enter_authorization.getScene().getWindow()).close();
                    new ShowWindowFxml("helloAdmin", 700);
                }
                default -> {
                    text_Field_login_autorization.setText("неверный логин");
                    text_Field_password_autorization.setText("или пароль");
                }
            }
        });
        buttonId_registration_authorization.setOnAction(event -> {
            ((Stage) buttonId_registration_authorization.getScene().getWindow()).close();
            new ShowWindowFxml("registration");
        });
    }
}
