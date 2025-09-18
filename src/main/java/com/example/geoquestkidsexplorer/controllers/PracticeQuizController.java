package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PracticeQuizController {

    @FXML private Label questionNumberLabel;
    @FXML private Label scoreLabel;
    @FXML private ImageView countryImageView;
    @FXML private Label questionLabel;
    @FXML private ToggleGroup answerGroup;
    @FXML private RadioButton option1, option2, option3, option4;
    @FXML private VBox feedbackContainer;
    @FXML private Label feedbackMessageLabel;
    @FXML private Label funFactLabel;
    @FXML private Button nextQuestionButton;
    @FXML private Button backButton;
    @FXML private Label quizWelcomeLabel;
    @FXML private VBox option1Tile, option2Tile, option3Tile, option4Tile;

    private String continentName;
    private List<PracticeQuizQuestions> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean answerChecked = false;

    // Set the number of practice questions to 25 as requested
    private static final int NUMBER_OF_PRACTICE_QUESTIONS = 25;

    // This method is now called from the previous controller (e.g., AfricaController)
    public void setContinentName(String continent) {
        this.continentName = continent;
        if (continentName == null || continentName.trim().isEmpty()) {
            System.err.println("Continent name is null or empty. Skipping quiz load.");
            quizWelcomeLabel.setText("Error: No continent selected.");
            return;
        }
        quizWelcomeLabel.setText("Practice your knowledge with the " + continentName + " Continent!");
        backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        loadQuizQuestions();
    }

    @FXML
    public void initialize() {
        // The listener is attached once and for all.
        // It checks if a radio button is selected and handles the answer.
        answerGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !answerChecked) {
                checkAnswer((RadioButton) newValue);
            }
        });
        // Hide feedback container and next question button initially
        feedbackContainer.setVisible(false);
        nextQuestionButton.setVisible(false);
    }

    private void loadQuizQuestions() {
        // Ensure the continent name is set before attempting to load questions.
        if (continentName == null || continentName.isEmpty()) {
            System.err.println("Continent name is not set. Cannot load practice quiz questions.");
            questionLabel.setText("No continent selected.");
            return;
        }

        questions.clear(); // Clear any previous questions

        // Loop to get the specified number of questions from the database
        for (int i = 0; i < NUMBER_OF_PRACTICE_QUESTIONS; i++) {
            // This is where we use the dynamic continentName
            PracticeQuizQuestions q = DatabaseManager.getPracticeQuizQuestion(continentName);
            if (q != null) {
                questions.add(q);
            }
        }

        // If no questions were loaded, display an error and return
        if (questions.isEmpty()) {
            questionLabel.setText("No questions found for " + continentName + ". Please check your database.");
            return;
        }

        loadQuestion();
    }

    private void loadQuestion() {
        answerChecked = false;
        if (currentQuestionIndex < questions.size()) {
            // Re-show radio buttons for the new question
            option1.setVisible(true);
            option2.setVisible(true);
            option3.setVisible(true);
            option4.setVisible(true);

            // Detach radio buttons from the toggle group to prevent selection issues
            option1.setToggleGroup(null);
            option2.setToggleGroup(null);
            option3.setToggleGroup(null);
            option4.setToggleGroup(null);

            // Re-attach them to the toggle group
            option1.setToggleGroup(answerGroup);
            option2.setToggleGroup(answerGroup);
            option3.setToggleGroup(answerGroup);
            option4.setToggleGroup(answerGroup);

            PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            countryImageView.setImage(currentQuestion.countryImage());
            questionLabel.setText(currentQuestion.questionText());

            List<String> options = new ArrayList<>(currentQuestion.getChoices());
            Collections.shuffle(options, ThreadLocalRandom.current());

            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            resetStyles();
            answerGroup.selectToggle(null);
            disableRadioButtons(false);
            feedbackContainer.setVisible(false);
            nextQuestionButton.setVisible(false);
        } else {
            // End of Quiz Logic
            endQuiz();
        }
    }

    // This method's ONLY job is to select the RadioButton when the user clicks the tile.
    // The ToggleGroup listener in initialize() will then handle the rest of the logic.
    @FXML
    private void handleTileSelection(MouseEvent event) {
        VBox clickedTile = (VBox) event.getSource();
        for (Node node : clickedTile.getChildren()) {
            if (node instanceof RadioButton) {
                RadioButton selectedRadioButton = (RadioButton) node;
                // Check if the button is not already selected and not disabled
                if (!selectedRadioButton.isSelected() && !selectedRadioButton.isDisable()) {
                    selectedRadioButton.setSelected(true);
                }
                break;
            }
        }
    }

    private void checkAnswer(RadioButton selectedRadioButton) {
        if (questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            System.err.println("No questions available to check.");
            return;
        }
        answerChecked = true;
        PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String selectedAnswer = selectedRadioButton.getText();
        boolean isCorrect = selectedAnswer.equals(currentQuestion.getCorrectAnswer());

        // Disable all radio buttons after an answer is selected
        disableRadioButtons(true);

        if (isCorrect) {
            score++;
            scoreLabel.setText(String.valueOf(score));
            selectedRadioButton.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 5;");
            feedbackMessageLabel.setText("Awesome! That's a correct answer. 😊");
            feedbackMessageLabel.setTextFill(Color.web("#4caf50"));
        } else {
            // Apply wrong answer style
            selectedRadioButton.setStyle("-fx-background-color: #ffccbc; -fx-background-radius: 5;");
            // Find the correct answer and apply the right style
            for (RadioButton rb : new RadioButton[]{option1, option2, option3, option4}) {
                if (rb.getText().equals(currentQuestion.getCorrectAnswer())) {
                    rb.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 5;");
                    break;
                }
            }
            feedbackMessageLabel.setText("Good try! The correct answer is " + currentQuestion.getCorrectAnswer() + ". 😟");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
        }

        funFactLabel.setText(currentQuestion.getFunFact());
        feedbackContainer.setVisible(true);
        nextQuestionButton.setVisible(true);
    }

    private void resetStyles() {
        option1Tile.setStyle(null);
        option2Tile.setStyle(null);
        option3Tile.setStyle(null);
        option4Tile.setStyle(null);
    }

    private void disableRadioButtons(boolean disable) {
        option1.setDisable(disable);
        option2.setDisable(disable);
        option3.setDisable(disable);
        option4.setDisable(disable);
    }

    @FXML
    private void handleNextQuestion(ActionEvent event) {
        currentQuestionIndex++;
        loadQuestion();
    }

    private void endQuiz() {
        // Set the final score and message for the user
        questionNumberLabel.setText("Quiz Complete!");
        questionLabel.setText("You have finished the " + continentName + " practice quiz!");
        countryImageView.setImage(null);
        feedbackMessageLabel.setText("Final Score: " + score + " out of " + questions.size());
        funFactLabel.setText("You can now try the Test Mode Quiz!");

        // Hide UI elements that are no longer needed
        feedbackContainer.setVisible(true);
        nextQuestionButton.setVisible(false);
        disableRadioButtons(true);
    }

    @FXML
    private void backToGameModes(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/continentview.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
