package by.victoria.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ShowWindowFxml {
    public ShowWindowFxml(String filepath) {
        try {
            Stage stage = new Stage();
            Parent parent = new FXMLLoader(new File("src/main/resources/by/victoria/" + filepath + ".fxml").toURI().toURL()).load();

            stage.setScene(new Scene(parent, 600, 400));
            stage.setTitle("Авиакомпания 'Пингвин'");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ShowWindowFxml(String filepath, int size) {
        try {
            Stage stage = new Stage();
            Parent parent = new FXMLLoader(new File("src/main/resources/by/victoria/" + filepath + ".fxml").toURI().toURL()).load();

            stage.setScene(new Scene(parent, size, 400));
            stage.setTitle("Авиакомпания 'Пингвин'");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
