package com.example.geoquestkidsexplorer.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Utility class for handling scene navigation and loading in the JavaFX application.
 * Encapsulates navigation logic to avoid duplication across controllers, promoting usability.
 * Supports polymorphism through generic controller configuration and dependency injection via ControllerFactory.
 */
public class NavigationHelper {

    private static final ControllerFactory controllerFactory = new ControllerFactory();

    /**
     * Loads a new scene from the specified FXML path and sets it on the stage derived from the source node.
     * Uses fixed dimensions (1200x800) to match application standard.
     * @param source   The node from which to derive the current stage.
     * @param fxmlPath The relative path to the FXML file (e.g., "/com/example/geoquestkidsexplorer/homepage.fxml").
     * @throws IOException If the FXML file cannot be loaded.
     */
    public static void loadScene(Node source, String fxmlPath) throws IOException {
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource(fxmlPath));
        loader.setControllerFactory(controllerFactory::createController);
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200.0, 800.0);
        scene.getStylesheets().add(NavigationHelper.class.getResource(
                "/com/example/geoquestkidsexplorer/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Loads a new scene from the specified FXML path, applies a configuration to the controller,
     * and sets it on the stage derived from the source node.
     * @param <T>      The type of the controller.
     * @param source   The node from which to derive the current stage.
     * @param fxmlPath The relative path to the FXML file.
     * @param config   A Consumer to configure the loaded controller (e.g., setting profile data).
     * @throws IOException If the FXML file cannot be loaded.
     */
    public static <T> void loadSceneWithConfig(Node source, String fxmlPath, Consumer<T> config) throws IOException {
        Stage stage = (Stage) source.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(NavigationHelper.class.getResource(fxmlPath));
        loader.setControllerFactory(controllerFactory::createController);
        Parent root = loader.load();
        T controller = loader.getController();
        config.accept(controller);
        Scene scene = new Scene(root, 1200.0, 800.0);
        scene.getStylesheets().add(NavigationHelper.class.getResource(
                "/com/example/geoquestkidsexplorer/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Navigates back to the home page.
     * @param source The node from which to derive the current stage.
     * @throws IOException If the home page FXML cannot be loaded.
     */
    public static void backToHome(Node source) throws IOException {
        loadScene(source, "/com/example/geoquestkidsexplorer/homepage.fxml");
    }

    /**
     * Navigates to the continent view or a specific continent page.
     * @param source        The node from which to derive the current stage.
     * @param continentName The name of the continent (optional; if null, loads default continent view).
     * @throws IOException If the FXML cannot be loaded.
     */
    public static void backToContinent(Node source, String continentName) throws IOException {
        String fxmlPath = (continentName != null && continentName.equalsIgnoreCase("Antarctica"))
                ? "/com/example/geoquestkidsexplorer/antarctica.fxml"
                : "/com/example/geoquestkidsexplorer/continentview.fxml";
        loadScene(source, fxmlPath);
    }
}