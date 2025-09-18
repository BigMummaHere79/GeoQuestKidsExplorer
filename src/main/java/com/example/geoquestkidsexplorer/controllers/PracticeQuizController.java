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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @FXML private Button backButton; // We need this to change the text dynamically
    @FXML private Label quizWelcomeLabel; // We need this to change the text dynamically

    private String continentName;
    private List<PracticeQuizQuestions> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;

    // This method is now called from the previous controller (e.g., OceaniaController)
    public void setContinentName(String continent) {
        this.continentName = continent;
        // Update the hardcoded labels with the correct continent name
        quizWelcomeLabel.setText("Practice your knowledge with the " + continentName + " Continent!");
        backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        // Now that we have the continent, load the questions from the database
        loadQuizQuestions();
    }

    @FXML
    public void initialize() {
        // The listener is attached once and for all.
        answerGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !((RadioButton) newValue).isDisable()) {
                checkAnswer((RadioButton) newValue);
            }
        });
        // Hide feedback container and next question button initially
        feedbackContainer.setVisible(false);
        nextQuestionButton.setVisible(false);
    }

    private void loadQuizQuestions() {
        questions.clear(); // Clear any previous questions
        final int numberOfQuestions = 25;
        for (int i = 0; i < numberOfQuestions; i++) {
            // This is where we use the dynamic continentName
            PracticeQuizQuestions q = DatabaseManager.getPracticeQuizQuestion(continentName);
            if (q != null) {
                questions.add(q);
            }
        }
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            // Re-show radio buttons for the new question
            option1.setVisible(true);
            option2.setVisible(true);
            option3.setVisible(true);
            option4.setVisible(true);
            option1.setToggleGroup(answerGroup);
            option2.setToggleGroup(answerGroup);
            option3.setToggleGroup(answerGroup);
            option4.setToggleGroup(answerGroup);

            PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            countryImageView.setImage(currentQuestion.countryImage());
            questionLabel.setText(currentQuestion.questionText());

            List<String> options = new ArrayList<>(currentQuestion.getChoices());
            Collections.shuffle(options);
            option1.setText(options.get(0));
            option2.setText(options.get(1));
            option3.setText(options.get(2));
            option4.setText(options.get(3));

            resetStyles();
            answerGroup.selectToggle(null);
            disableRadioButtons(false);

            funFactLabel.setText("");
            feedbackContainer.setVisible(false);
            nextQuestionButton.setVisible(false);
        } else {
            // --- End of Quiz Logic ---

            // Hide the radio buttons and their tiles
            option1.setVisible(false);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);

            // Detach radio buttons from the toggle group to prevent selection issues
            option1.setToggleGroup(null);
            option2.setToggleGroup(null);
            option3.setToggleGroup(null);
            option4.setToggleGroup(null);

            questionNumberLabel.setText("Quiz Complete!");
            questionLabel.setText("You have finished the " + continentName + " practice quiz!");
            feedbackContainer.setVisible(true);
            feedbackMessageLabel.setText("Final Score: " + score);
            funFactLabel.setText("You can now try the Test Mode Quiz!");
            funFactLabel.setVisible(true);
            nextQuestionButton.setVisible(false);
            resetStyles();
            //disableRadioButtons(true);
        }
    }

    // This method's ONLY job is to select the RadioButton when the user clicks the tile.
    // The ToggleGroup listener in to initialize() method will then handle the rest of the logic.
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
        PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String selectedAnswer = selectedRadioButton.getText();
        boolean isCorrect = selectedAnswer.equals(currentQuestion.getCorrectAnswer());
        if (isCorrect) {
            score++;
            scoreLabel.setText(String.valueOf(score));
            selectedRadioButton.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 5;");
            feedbackMessageLabel.setText("Awesome! That's a correct answer. 😊");
            feedbackMessageLabel.setTextFill(Color.web("#4caf50"));
        } else {
            selectedRadioButton.setStyle("-fx-background-color: #ffccbc; -fx-background-radius: 5;");
            for (RadioButton rb : new RadioButton[]{option1, option2, option3, option4}) {
                if (rb.getText().equals(currentQuestion.getCorrectAnswer())) {
                    rb.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 5;");
                }
            }
            feedbackMessageLabel.setText("Good try! Keep exploring. 😟");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
        }
        disableRadioButtons(true);
        funFactLabel.setText("Fun Facts about " + continentName + ":\n" + currentQuestion.getFunFact());
        String funFact = questions.get(currentQuestionIndex).getFunFact();
        // Show fun fact now that an answer has been selected
        funFactLabel.setText(funFact);
        funFactLabel.setVisible(true);
        feedbackContainer.setVisible(true);
        nextQuestionButton.setVisible(true);
    }

    private void resetStyles() {
        option1.setStyle("");
        option2.setStyle("");
        option3.setStyle("");
        option4.setStyle("");
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

    @FXML
    private void backToGameModes(ActionEvent event) {
        try {
            // NOTE: You will need to duplicate this method for each continent,
            // as each one needs to go back to its specific FXML page.
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

