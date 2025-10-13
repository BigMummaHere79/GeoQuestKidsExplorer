module com.example.geoquestkidsexplorer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires java.sql;
    requires java.desktop;
    requires org.controlsfx.controls; // Adding this for ControlsFX


    opens com.example.geoquestkidsexplorer to javafx.fxml;
    exports com.example.geoquestkidsexplorer;
    opens  com.example.geoquestkidsexplorer.controllers to javafx.fxml;
    exports com.example.geoquestkidsexplorer.controllers;
    exports com.example.geoquestkidsexplorer.database;
    exports com.example.geoquestkidsexplorer.models;
    opens com.example.geoquestkidsexplorer.models;
    opens com.example.geoquestkidsexplorer.database to javafx.fxml;
}