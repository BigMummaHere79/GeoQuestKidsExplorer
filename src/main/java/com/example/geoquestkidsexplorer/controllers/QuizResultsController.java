package com.example.geoquestkidsexplorer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the quiz results dialog box.
 */
public class QuizResultsController {

    @FXML private Label scoreLabel;
    @FXML private Label messageLabel;
    @FXML private Button mainButton;
    @FXML private Button secondaryButton;

    private Stage dialogStage;
    private String currentContinent;
    private Runnable onRetry;
    private Runnable onContinue;
    private Runnable onPractice;

    /**
     * Sets the stage for this dialog, allowing it to be closed.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the results and configures the buttons based on the score.
     */
    public void setResults(int score, int totalQuestions, String continent, boolean passed) {
        this.currentContinent = continent;
        double percentage = (double) score / totalQuestions * 100;
        scoreLabel.setText(String.format("You scored %d/%d (%.0f%%)!", score, totalQuestions, percentage));

        if (passed) {
            messageLabel.setText("Congratulations! You've unlocked the next continent!");
            mainButton.setText("Continue to next adventure!");
            secondaryButton.setVisible(false); // Hide the second button for a passing score
        } else {
            messageLabel.setText("You can do it! Keep exploring to improve.");
            mainButton.setText("Repeat test");
            secondaryButton.setText("Go back to practice mode");
            secondaryButton.setVisible(true);
        }
    }

    /**
     * Sets the actions to be performed when the buttons are clicked.
     */
    public void setActions(Runnable onRetry, Runnable onContinue, Runnable onPractice) {
        this.onRetry = onRetry;
        this.onContinue = onContinue;
        this.onPractice = onPractice;
    }

    @FXML
    private void handleMainButton(ActionEvent event) {
        if (mainButton.getText().startsWith("Repeat test")) {
            if (onRetry != null) {
                onRetry.run();
            }
        } else {
            if (onContinue != null) {
                onContinue.run();
            }
        }
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    @FXML
    private void handleSecondaryButton(ActionEvent event) {
        if (onPractice != null) {
            onPractice.run();
        }
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    /**
     * Handles the close button action to close the dialog window.
     */
    @FXML
    private void handleCloseButton(ActionEvent event) {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}