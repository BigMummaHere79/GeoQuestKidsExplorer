package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AntarcticaController {

    @FXML private Button continueButton; // Bind to the "Proceed to the Next Level" button in antarctica.fxml


    /**
     * When "Continue" button is clicked, it updates the user's progress in the DB, and unclocks next
     * continent in the game + takes user back to homepage
     * @param event The action event is triggered by clicking the continue button.
     */
    @FXML
    private void handleContinueButton(ActionEvent event) {
        String username = UserSession.getUsername();
        if (username == null || username.isEmpty()) {
            System.err.println("handleContinueButton: No user logged in, cannot update progress");
            // Optionally show an error alert to the user
            return;
        }

        // Simulate completing Antarctica (level 1) with 100% score
        boolean updated = DatabaseManager.saveQuizResultAndUpdateLevel(username, 1, 100.0);
        if (updated) {
            String nextContinent = GameStateManager.getInstance().getNextContinent("Antarctica");
            if (nextContinent != null) {
                GameStateManager.getInstance().unlockContinent(nextContinent);
                GameStateManager.getInstance().saveState(); // Persist the state
                System.out.println("handleContinueButton: Unlocked " + nextContinent + " for user " + username);
            } else {
                System.out.println("handleContinueButton: No next continent available for user " + username);
            }
        } else {
            System.err.println("handleContinueButton: Failed to update progress for user " + username);
            // Optionally show an error alert to the user
            return;
        }

        // Navigate back to homepage to refresh UI
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200.0, 800.0);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            System.out.println("handleContinueButton: Navigated to homepage for user " + username);
        } catch (IOException e) {
            System.err.println("handleContinueButton: Error loading homepage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setContinentName(String continentName) {
    }
}