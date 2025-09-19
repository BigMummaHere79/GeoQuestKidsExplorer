package com.example.geoquestkidsexplorer;

import com.example.geoquestkidsexplorer.controllers.LoginController;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    private static final double INITIAL_WIDTH = 1200.0;
    private static final double INITIAL_HEIGHT = 800.0;


    @Override
    public void start(Stage stage) throws IOException {
        // This is the CRUCIAL first step. Create the database and table.
        DatabaseManager.createNewDatabase();

        // Corrected path to match the package name
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("loginview.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), INITIAL_WIDTH, INITIAL_HEIGHT); // Set initial scene dimensions
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Pass the stage to the controller
        LoginController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("GeoQuest Kids Explorer");
        stage.setScene(scene);
        stage.setResizable(false); // Prevent user resizing
        stage.show();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    //create database
    public static void main(String[] args) {
        DatabaseManager.createNewDatabase();
        launch();
    }
}