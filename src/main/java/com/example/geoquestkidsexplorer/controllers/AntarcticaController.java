package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the Antarctica continent view UI.
 * Manages Antarctica-specific content and navigation.
 * Extends BaseController for shared functionality like stage management and continent color application.
 */
public class AntarcticaController extends BaseController {

    @FXML private Button continueButton;
    @FXML private Label continentLabel;
    @FXML private Label statusLabel;

    private String continentName = "Antarctica";
    private String username;
    private String avatar;

    /**
     * Sets the stage for this controller.
     *
     * @param stage The JavaFX stage.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the Antarctica view UI.
     */
    @FXML
    public void initialize() {
        if (continentLabel != null) {
            continentLabel.setText(continentName);
        }
        if (statusLabel != null) {
            boolean isUnlocked = GameStateManager.getInstance().isContinentUnlocked(continentName);
            statusLabel.setText(isUnlocked ? "Antarctica is unlocked!" : "Antarctica is locked.");
            statusLabel.setStyle(isUnlocked ? "-fx-text-fill: green;" : "-fx-text-fill: red;");
        }
        // Apply continent colors
        applyContinentColors();
    }

    /**
     * Sets profile data for the controller.
     *
     * @param username The username to set.
     * @param avatar   The avatar to set.
     */
    @Override
    public void setProfileData(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        UserSession.setUser(username, avatar);
    }

    /**
     * Implements continent-specific setup.
     * Applies Antarctica colors and sets up the UI.
     *
     * @param continentName The name of the continent (expected to be "Antarctica").
     */
    @Override
    protected void setupContinent(String continentName) {
        this.continentName = continentName != null ? continentName : "Antarctica";
        applyContinentColors();
        // Trigger UI update
        initialize();
    }

    /**
     * Applies continent-specific colors to UI elements.
     */
    private void applyContinentColors() {
        Map<String, Node> nodes = new HashMap<>();
        if (continentLabel != null) nodes.put("continentLabel", continentLabel);
        if (statusLabel != null) nodes.put("statusLabel", statusLabel);
        if (continueButton != null) nodes.put("continueButton", continueButton);
        applyContinentColors(continentName, nodes);
    }

    /**
     * Handles the "Continue" button, updating user progress and navigating to the homepage.
     *
     * @param event The action event triggered by clicking the continue button.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void handleContinueButton(ActionEvent event) throws IOException {
        if (username == null || username.isEmpty()) {
            System.err.println("handleContinueButton: No user logged in, cannot update progress");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No user is logged in. Please log in to continue.");
            alert.showAndWait();
            return;
        }

        // Simulate completing Antarctica (level 1) with 100% score
        boolean updated = DatabaseManager.saveQuizResultAndUpdateLevel(username, 1, 100.0);
        if (updated) {
            String nextContinent = GameStateManager.getInstance().getNextContinent(continentName);
            if (nextContinent != null) {
                GameStateManager.getInstance().unlockContinent(nextContinent);
                GameStateManager.getInstance().saveState();
                System.out.println("handleContinueButton: Unlocked " + nextContinent + " for user " + username);
            } else {
                System.out.println("handleContinueButton: No next continent available for user " + username);
            }
        } else {
            System.err.println("handleContinueButton: Failed to update progress for user " + username);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update progress. Please try again.");
            alert.showAndWait();
            return;
        }

        // Navigate back to homepage
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/homepage.fxml",
                (HomePageController controller) -> {
                    controller.setProfileData(username, avatar);
                    controller.setStage(stage);
                });
    }
}