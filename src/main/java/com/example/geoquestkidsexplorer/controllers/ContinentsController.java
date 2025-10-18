package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
            applyContinentColors();
        } else {
            System.err.println("initialize: continentName is null or empty, setting default UI");
            if (gameModeWelcomeLabel != null) {
                gameModeWelcomeLabel.setText("Error: No continent selected.");
            }
        }
    }

    @Override
    public void setProfileData(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        UserSession.setUser(username, avatar);
        System.out.println("ContinentsController.setProfileData: username=" + username + ", avatar=" + avatar);
    }

    @Override
    protected void setupContinent(String continentName) {
        this.continentName = continentName != null ? continentName : "";
        System.out.println("setupContinent: continentName=" + continentName);
        applyContinentColors();
        initialize();
    }

    public void setContinentName(String continentName) {
        System.out.println("setContinentName called with: " + continentName);
        this.continentName = continentName != null ? continentName : "";
        applyContinentColors();
        initialize();
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
        applyContinentColors(continentName, nodes);
        switch (continentName) {
            case "Oceania" -> {
                if (bgPane != null) bgPane.setStyle("-fx-background-color: #f9f9f9; -bg2: #F4BA9B; -bg3: #F5793A;");
                if (navBar != null) navBar.setStyle("-fx-background-color: #F5793A;");
                if (backButton != null) backButton.setStyle("-fx-background-color: #F4BA9B;");
            }
            case "South America" -> {
                if (bgPane != null) bgPane.setStyle("-fx-background-color: #f9f9f9; -bg2: #E488DA; -bg3: #A95AA1;");
                if (navBar != null) navBar.setStyle("-fx-background-color: #A95AA1;");
                if (backButton != null) backButton.setStyle("-fx-background-color: #E488DA;");
            }
            case "North America" -> {
                if (bgPane != null) bgPane.setStyle("-fx-background-color: #f9f9f9; -bg2: #FA76A7; -bg3: #D81B60;");
                if (navBar != null) navBar.setStyle("-fx-background-color: #D81B60;");
                if (backButton != null) backButton.setStyle("-fx-background-color: #FA76A7;");
            }
            case "Europe" -> {
                if (bgPane != null) bgPane.setStyle("-fx-background-color: #f9f9f9; -bg2: #4B66FF; -bg3: #0F2080;");
                if (navBar != null) navBar.setStyle("-fx-background-color: #0F2080;");
                if (backButton != null) backButton.setStyle("-fx-background-color: #4B66FF;");
            }
            default -> System.err.println("applyContinentColors: Unknown continent " + continentName);
        }
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
                    controller.setContinentName(continent);
                    controller.setProfileData(username, avatar);
                    controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                    applyQuizStyles(controller, continent);
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
                    applyQuizStyles(controller, continent);
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
                    controller.setContinentName(continent);
                    controller.setProfileData(username, avatar);
                    controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                    applyQuizStyles(controller, continent);
                });
    }

    private void applyQuizStyles(BaseController controller, String continent) {
        Parent root = controller.getStage().getScene().getRoot();
        Node navBar = root.lookup("#navBar");
        Node backBtn = root.lookup("#backButton");
        Node score = root.lookup("#scoreLabel");
        Node timer = root.lookup("#timerLabel");
        if (navBar != null && backBtn != null) {
            switch (continent) {
                case "Oceania" -> {
                    if (navBar != null) navBar.setStyle("-fx-background-color: #F5793A;");
                    if (backBtn != null) backBtn.setStyle("-fx-background-color: #F4BA9B;");
                    if (score != null) score.setStyle("-fx-background-color: #F4BA9B;");
                    if (timer != null) timer.setStyle("-fx-background-color: #F4BA9B;");
                }
                case "South America" -> {
                    if (navBar != null) navBar.setStyle("-fx-background-color: #A95AA1;");
                    if (backBtn != null) backBtn.setStyle("-fx-background-color: #E488DA;");
                    if (score != null) score.setStyle("-fx-background-color: #E488DA;");
                    if (timer != null) timer.setStyle("-fx-background-color: #E488DA;");
                }
                case "North America" -> {
                    if (navBar != null) navBar.setStyle("-fx-background-color: #D81B60;");
                    if (backBtn != null) backBtn.setStyle("-fx-background-color: #FA76A7;");
                    if (score != null) score.setStyle("-fx-background-color: #FA76A7;");
                    if (timer != null) timer.setStyle("-fx-background-color: #FA76A7;");
                }
                case "Europe" -> {
                    if (navBar != null) navBar.setStyle("-fx-background-color: #0F2080;");
                    if (backBtn != null) backBtn.setStyle("-fx-background-color: #4B66FF;");
                    if (score != null) score.setStyle("-fx-background-color: #4B66FF;");
                    if (timer != null) timer.setStyle("-fx-background-color: #4B66FF;");
                }
                default -> System.err.println("applyQuizStyles: Unknown continent " + continent);
            }
        }
    }
}