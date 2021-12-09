package by.victoria.controller;

import by.victoria.app.ShowWindowFxml;
import by.victoria.command.Command;
import by.victoria.connection.ConnectionHandler;
import by.victoria.model.dto.UserDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static by.victoria.connection.ConnectionHandler.getConnectionHandler;

public class RegistrationController {
    @FXML
    private Button ButtonId_registration;

    @FXML
    private Button ButtonId_registration_Back;

    @FXML
    private TextField Text_Field_login_registration;

    @FXML
    private TextField Text_Field_password_registration;

    @FXML
    private TextField Text_Field_password_registration_repeat;

    public void initialize() {
        ConnectionHandler connectionHandler = getConnectionHandler();

        ButtonId_registration.setOnAction(event -> {
            String password = Text_Field_password_registration.getText();
            String passwordRepeat = Text_Field_password_registration_repeat.getText();

            if (password.equals(passwordRepeat)) {
                String login = Text_Field_login_registration.getText();
                UserDto userDto = new UserDto(login, password);

                connectionHandler.writeObject(Command.REGISTRATION);
                connectionHandler.writeObject(userDto);

                Object readObject = connectionHandler.readObject();
                if (readObject != null) {
                    ((Stage) ButtonId_registration.getScene().getWindow()).close();
                    new ShowWindowFxml("login");
                } else {
                    Text_Field_login_registration.setText("пользователь");
                    Text_Field_password_registration.setText("существует");
                    Text_Field_password_registration_repeat.setText("");
                }
            } else {
                Text_Field_login_registration.setText("пароли");
                Text_Field_password_registration.setText("должены");
                Text_Field_password_registration_repeat.setText("совпадать!");
            }
        });

        ButtonId_registration_Back.setOnAction(event -> {
            ((Stage) ButtonId_registration_Back.getScene().getWindow()).close();
            new ShowWindowFxml("login");
        });
    }
}
