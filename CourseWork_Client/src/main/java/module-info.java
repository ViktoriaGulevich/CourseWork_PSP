module by.victoria {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.google.gson;
    requires java.sql;

    exports by.victoria;
    opens by.victoria to javafx.fxml;
    exports by.victoria.controller;
    opens by.victoria.controller to javafx.fxml;
    exports by.victoria.model.dto;
    opens by.victoria.model.dto to com.google.gson;
    exports by.victoria.app;
    opens by.victoria.app to javafx.fxml;
}