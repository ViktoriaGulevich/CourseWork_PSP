package by.victoria;

import by.victoria.app.ShowWindowFxml;
import javafx.application.Application;
import javafx.stage.Stage;

public class Runner extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        new ShowWindowFxml("login");
    }
}