package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for all controllers, providing common UI setup methods.
 * Encapsulates repetitive logic like continent color application and promotes polymorphism
 * through abstract methods for subclass-specific behavior.
 */
public abstract class BaseController {

    // Encapsulated color palettes for continents (avoids repetitive switch statements)
    private static final Map<String, Map<String, String>> CONTINENT_COLORS = new HashMap<>();

    static {
        Map<String, String> antarctica = new HashMap<>();
        antarctica.put("bg1", "#f9f9f9");
        antarctica.put("bg2", "#B3E5FC");
        antarctica.put("bg3", "#0288D1");
        antarctica.put("nav-bg", "#0288D1");
        antarctica.put("btn-bg", "#B3E5FC");
        CONTINENT_COLORS.put("Antarctica", antarctica);

        Map<String, String> oceania = new HashMap<>();
        oceania.put("bg1", "#f9f9f9");
        oceania.put("bg2", "#F4BA9B");
        oceania.put("bg3", "#F5793A");
        oceania.put("nav-bg", "#F5793A");
        oceania.put("btn-bg", "#F4BA9B");
        CONTINENT_COLORS.put("Oceania", oceania);

        Map<String, String> southAmerica = new HashMap<>();
        southAmerica.put("bg1", "#f9f9f9");
        southAmerica.put("bg2", "#E488DA");
        southAmerica.put("bg3", "#A95AA1");
        southAmerica.put("nav-bg", "#A95AA1");
        southAmerica.put("btn-bg", "#E488DA");
        CONTINENT_COLORS.put("South America", southAmerica);

        Map<String, String> northAmerica = new HashMap<>();
        northAmerica.put("bg1", "#f9f9f9");
        northAmerica.put("bg2", "#FA76A7");
        northAmerica.put("bg3", "#D81B60");
        northAmerica.put("nav-bg", "#D81B60");
        northAmerica.put("btn-bg", "#FA76A7");
        CONTINENT_COLORS.put("North America", northAmerica);

        Map<String, String> europe = new HashMap<>();
        europe.put("bg1", "#f9f9f9");
        europe.put("bg2", "#4B66FF");
        europe.put("bg3", "#0F2080");
        europe.put("nav-bg", "#0F2080");
        europe.put("btn-bg", "#4B66FF");
        CONTINENT_COLORS.put("Europe", europe);
    }

    protected Stage stage;

    /**
     * Sets the stage for this controller.
     * @param stage The JavaFX stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    /**
     * Loads a new scene from the given FXML path and sets it on the stage.
     *
     * @param event the action event triggering the load.
     * @param fxmlPath the path to the FXML file.
     * @throws IOException if the FXML cannot be loaded.
     */
    protected void loadScene(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 800.0);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Applies color palette styles to UI nodes based on the continent.
     * This method encapsulates color logic to avoid duplication.
     * @param continent the continent name.
     * @param nodes a map of node IDs to Node objects for styling.
     */
    protected void applyContinentColors(String continent, Map<String, Node> nodes) {
        Map<String, String> colors = CONTINENT_COLORS.get(continent);
        if (colors == null) return;

        Node bgPane = nodes.get("bgPane");
        if (bgPane != null) {
            bgPane.setStyle(String.format("-bg1:%s; -bg2:%s; -bg3:%s;",
                    colors.get("bg1"), colors.get("bg2"), colors.get("bg3")));
        }

        Node navBar = nodes.get("navBar");
        if (navBar != null) {
            navBar.setStyle(String.format("-nav-bg: %s;", colors.get("nav-bg")));
        }

        Node backButton = nodes.get("backButton");
        if (backButton != null) {
            backButton.setStyle(String.format("-btn-bg: %s;", colors.get("btn-bg")));
        }
    }

    /**
     * Abstract method for subclasses to implement continent-specific setup.
     * Demonstrates polymorphism.
     *
     * @param continentName the name of the continent.
     */
    protected abstract void setupContinent(String continentName);

    /**
     * Abstract method for setting profile data.
     * Enables polymorphic handling of user data across controllers.
     *
     * @param username The username to set.
     * @param avatar   The avatar to set.
     */
    public abstract void setProfileData(String username, String avatar);
}