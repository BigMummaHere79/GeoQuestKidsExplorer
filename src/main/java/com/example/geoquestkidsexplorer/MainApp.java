package com.example.geoquestkidsexplorer;

import com.example.geoquestkidsexplorer.controllers.LoginController;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.database.DatabaseServiceFactory;
import com.example.geoquestkidsexplorer.repositories.UserService;
import com.example.geoquestkidsexplorer.utils.ControllerFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for the GeoQuest Kids Explorer JavaFX application.
 * Initializes the database and launches the login view.
 */
public class MainApp extends Application {
    private static final double INITIAL_WIDTH = 1200.0;
    private static final double INITIAL_HEIGHT = 800.0;

    /**
     * Starts the application by creating the DB and loading login FXML.
     * @param stage The primary stage for the application.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        DatabaseManager.createNewDatabase();  // Uses facade, delegates to SchemaManager

        ControllerFactory controllerFactory = new ControllerFactory();
        // Create UserService using the factory
        UserService userService = DatabaseServiceFactory.createUserService();

        // Set up FXMLLoader with a controller factory
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource(
                "/com/example/geoquestkidsexplorer/loginview.fxml"));
        fxmlLoader.setControllerFactory(controllerFactory::createController);
        Scene scene = new Scene(fxmlLoader.load(), INITIAL_WIDTH, INITIAL_HEIGHT);
        scene.getStylesheets().add(getClass().getResource(
                "/com/example/geoquestkidsexplorer/style.css").toExternalForm());

        LoginController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("GeoQuest Kids Explorer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Launches the application, ensuring DB creation.
     * @param args Command-line args.
     */
    public static void main(String[] args) {
        DatabaseManager.createNewDatabase();
        launch(args);
    }
}