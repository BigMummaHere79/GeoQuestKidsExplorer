package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.GameStateManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AntarcticaController {

    @FXML private Button continueButton; // Bind to the "Continue" button in antarctica.fxml

    @FXML
    private void handleContinueButton(ActionEvent event) {
        // Unlock next continent (Africa)
        String nextContinent = GameStateManager.getInstance().getNextContinent("Antarctica");
        if (nextContinent != null) {
            GameStateManager.getInstance().unlockContinent(nextContinent);
            System.out.println(nextContinent + " unlocked! Ready for adventure.");
            // Optional: Alert alert = new Alert(Alert.AlertType.INFORMATION);
            // alert.setContentText(nextContinent + " unlocked!");
            // alert.showAndWait();
        } else {
            System.out.println("Congratulations! You've completed all continents!");
        }

        // Navigate back to homepage
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading homepage.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setContinentName(String continentName) {
    }
}