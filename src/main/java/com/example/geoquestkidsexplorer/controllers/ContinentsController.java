package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the generic continent game mode page.
 * Handles all continents, replacing individual controllers like AfricaController, AsiaController, etc.
 * Extends BaseController for shared functionality like stage management and continent color application.
 */
public class ContinentsController extends BaseController {

    @FXML private Label continentLabel;
    @FXML private Button backButton;
    @FXML private Label gameModeWelcomeLabel;
    @FXML private javafx.scene.layout.VBox bgPane;
    @FXML private javafx.scene.layout.HBox navBar;

    private String continentName;
    private String username;
    private String avatar;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
    }

    @Override
    public void setProfileData(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        UserSession.getInstance().setUser(username, avatar);
        System.out.println("ContinentsController.setProfileData: username=" + username + ", avatar=" + avatar);
    }

    @Override
    protected void setupContinent(String continentName) {
        this.continentName = continentName != null ? continentName : "";
        System.out.println("setupContinent: continentName=" + continentName);
        applyContinentColors();
        updateUI();
    }

    public void setContinentName(String continentName) {
        System.out.println("setContinentName called with: " + continentName);
        this.continentName = continentName != null ? continentName : "";
        applyContinentColors();
        updateUI();
    }

    private void updateUI() {
        if (continentName != null && !continentName.isBlank()) {
            if (continentLabel != null) {
                continentLabel.setText(continentName);
            }
            if (gameModeWelcomeLabel != null) {
                gameModeWelcomeLabel.setText("Game Mode Control for " + continentName + " Continent!");
            }
            if (backButton != null) {
                backButton.setText("⬅️ Back to Continents");
            }
        } else {
            if (gameModeWelcomeLabel != null) {
                gameModeWelcomeLabel.setText("Error: No continent selected.");
            }
        }
    }

    private void applyContinentColors() {
        if (continentName == null || continentName.isBlank()) {
            System.err.println("applyContinentColors: continentName is null or empty");
            return;
        }
        Map<String, Node> nodes = new HashMap<>();
        if (bgPane != null) nodes.put("bgPane", bgPane);
        if (navBar != null) nodes.put("navBar", navBar);
        if (backButton != null) nodes.put("backButton", backButton);
        applyContinentColors(continentName, nodes);  // Calls super; remove the switch
    }

    @FXML
    private void backToContinents(ActionEvent event) throws IOException {
        System.out.println("backToContinents: Navigating back with username=" + username);
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(), "/com/example/geoquestkidsexplorer/homepage.fxml",
                (HomePageController controller) -> {
                    controller.setProfileData(username, avatar);
                    controller.setStage(stage);
                });
    }

    @FXML
    private void handleGameModeClick(MouseEvent event) throws IOException {
        Node n = (Node) event.getTarget();
        while (n != null && n.getId() == null) n = n.getParent();
        String tileId = (n != null) ? n.getId() : null;
        if (tileId == null) {
            System.err.println("handleGameModeClick: No tile ID found");
            return;
        }
        if (continentName == null || continentName.isBlank()) {
            System.err.println("handleGameModeClick: continentName is null or empty");
            if (gameModeWelcomeLabel != null) {
                gameModeWelcomeLabel.setText("Error: No continent selected.");
            }
            return;
        }
        System.out.println("handleGameModeClick: tileId=" + tileId + ", continentName=" + continentName);
        switch (tileId) {
            case "practiceModeTile" -> openPracticeQuiz(event, continentName);
            case "flashCardsModeTile" -> openFlashCardMode(event, continentName);
            case "testModeTile" -> {
                openTestQuiz(event, continentName);
                System.out.println("Test Mode Quiz has been clicked! " + tileId);
            }
            default -> System.err.println("handleGameModeClick: Unknown tile ID " + tileId);
        }
    }

    private void openPracticeQuiz(MouseEvent event, String continent) throws IOException {
        if (continent == null || continent.isBlank()) {
            System.err.println("openPracticeQuiz: Continent is null or empty");
            return;
        }
        System.out.println("openPracticeQuiz: Loading for continent=" + continent + ", username=" + username);
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(), "/com/example/geoquestkidsexplorer/practicequiz.fxml",
                (PracticeQuizController controller) -> {
                    controller.setContinentName(continentName);
                    controller.setProfileData(username, avatar);
                    controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                    controller.setupContinent(continent);
                });
    }

    private void openFlashCardMode(MouseEvent event, String continent) throws IOException {
        if (continent == null || continent.isBlank()) {
            System.err.println("openFlashCardMode: Continent is null or empty");
            return;
        }
        System.out.println("openFlashCardMode: Loading for continent=" + continent + ", username=" + username);
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(), "/com/example/geoquestkidsexplorer/FlashcardsPage.fxml",
                (FlashcardsController controller) -> {
                    controller.setRegion(continent);
                    controller.setProfileData(username, avatar);
                    controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                    controller.setupContinent(continentName);
                });
    }

    private void openTestQuiz(Event event, String continent) throws IOException {
        if (continent == null || continent.isBlank()) {
            System.err.println("openTestQuiz: Continent is null or empty");
            return;
        }
        System.out.println("openTestQuiz: Loading for continent=" + continent + ", username=" + username);
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(), "/com/example/geoquestkidsexplorer/testmode.fxml",
                (TestModeController controller) -> {
                    controller.setContinentName(continentName);
                    controller.setProfileData(username, avatar);
                    controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                    controller.setupContinent(continent);
                });
    }
}