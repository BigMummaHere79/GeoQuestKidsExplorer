package com.example.geoquestkidsexplorer.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizResultsController {

    @FXML private Label resultMessageLabel;
    @FXML private Label scoreMessageLabel;
    @FXML private Label passFailMessageLabel;
    @FXML private Button continueButton;
    @FXML private Button retryButton;
    @FXML private Button practiceButton;

    private Stage dialogStage;
    private Stage mainStage;

    // A new field to store the continent name
    private String continentName;

    // A new method to set the continent name
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public void setMainStage(Stage stage) {
        this.mainStage = stage;
    }

    private void handlePracticeMode() {
        if (mainStage == null) {
            System.err.println("Error: mainStage is null in handlePracticeMode");
            return;
        }
        if (continentName == null || continentName.trim().isEmpty()) {
            System.err.println("Error: continentName is null or empty in handlePracticeMode");
            // Fallback to a default continent or show an error dialog
            continentName = "Oceania"; // Default to Africa, or prompt user
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/practicequiz.fxml"));
            Parent root = loader.load();

            // Get the controller for the practice quiz
            PracticeQuizController practiceQuizController = loader.getController();

            // Pass the continent name to the new practice quiz controller
            practiceQuizController.setContinentName(continentName);

            mainStage.setScene(new Scene(root));
            mainStage.show();

            if (dialogStage != null) {
                dialogStage.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setActions(Runnable onRetry, Runnable onContinue, Runnable onPractice) {
        if (retryButton != null) {
            retryButton.setOnAction(event -> {
                onRetry.run();
                if (dialogStage != null) {
                    dialogStage.close();
                }
            });
        }
        if (continueButton != null) {
            continueButton.setOnAction(event -> {
                onContinue.run();
                if (dialogStage != null) {
                    dialogStage.close();
                }
            });
        }
        if (practiceButton != null) {
            practiceButton.setOnAction(event -> handlePracticeMode());
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setResults(int score, int total, String continentName, boolean passed) {
        setContinentName(continentName); // Make sure to set the continent name here
        resultMessageLabel.setText("You have completed the " + continentName + " Test!");
        scoreMessageLabel.setText("Your Score: " + score + "/" + total);

        if (passed) {
            passFailMessageLabel.setText("Congratulations! You passed and unlocked the next continent!");
            continueButton.setVisible(true);
        } else {
            passFailMessageLabel.setText("You did not reach the passing score. You need to get 80% to pass this quiz.");
            continueButton.setVisible(false);
        }
    }
}