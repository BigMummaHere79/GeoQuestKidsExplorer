package com.example.geoquestkidsexplorer.controllers;

//Don't need for now.Just for unlocking and locking countries
//import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestQuizController {

    @FXML private VBox quizBox;
    @FXML private Label questionNumberLabel;
    @FXML private Label scoreLabel;
    @FXML private ImageView countryImageView;
    @FXML private Label quizWelcomeLabel;
    @FXML private Label questionLabel;
    @FXML private Label countryImagePlaceholder;
    @FXML private TextField answerField;
    @FXML private Button submitButton;
    @FXML private Label feedbackMessageLabel;
    @FXML private Button nextQuestionButton;
    @FXML private Button backButton;
    @FXML private Label timerLabel;
    private Timeline timeline;

    private String continentName;
    private List<TestQuizQuestions> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private final int QUESTIONS_PER_QUIZ = 10;
    private boolean isSubmitted = false;
    private int timeSeconds = 60;

    /**
     * Sets the continent name to load the correct quiz data.
     */
    public void setContinentName(String continent) {
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Error: Received null or empty continent name");
            continent = "Oceania"; // Fallback to default continent
        }
        this.continentName = continent;
        quizWelcomeLabel.setText("Practice your knowledge with the " + continentName + " Continent!");
        backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        this.questions = new ArrayList<>();
        for (int i = 0; i < QUESTIONS_PER_QUIZ; i++) {
            TestQuizQuestions question = DatabaseManager.getTestQuizQuestion(continentName);
            if (question != null) { // Only add non-null questions
                this.questions.add(question);
            }
        }
        if (questions.isEmpty()) {
            System.err.println("No valid questions loaded for continent: " + continentName);
            questionLabel.setText("No questions available for " + continentName);
            return;
        }
        loadQuizQuestions();
    }

    private void loadQuizQuestions() {
        if (questions.isEmpty()) {
            questionLabel.setText("No questions available.");
            countryImageView.setVisible(false);
            countryImagePlaceholder.setText("No questions available.");
            countryImagePlaceholder.setVisible(true);
            return;
        }
        if (currentQuestionIndex < questions.size()) {
            TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
            if (currentQuestion == null) {
                System.err.println("Null question at index: " + currentQuestionIndex);
                return;
            }
            Image quizImage = currentQuestion.countryImage();
            if (quizImage != null) {
                countryImageView.setImage(quizImage);
                countryImageView.setVisible(true);
                countryImagePlaceholder.setVisible(false);
            } else {
                countryImageView.setVisible(false);
                countryImagePlaceholder.setText("No Image Available");
                countryImagePlaceholder.setVisible(true);
            }
            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            questionLabel.setText(currentQuestion.getQuestionText());
            answerField.setText("");
            answerField.setDisable(false);
            submitButton.setVisible(true);
            nextQuestionButton.setVisible(false);
            feedbackMessageLabel.setText("");
            isSubmitted = false;
            startTimer();
        } else {
            // End quiz if no more questions
            handleNextQuestion(new ActionEvent(nextQuestionButton, null));
        }
    }

    private void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = 60;
        timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeSeconds--;
                    timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                    if (timeSeconds <= 0) {
                        timeline.stop();
                        handleSubmit();
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void handleSubmit() {
        if (isSubmitted) return;
        isSubmitted = true;
        timeline.stop();
        TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String userAnswer = answerField.getText().trim();
        String correctAnswer = currentQuestion.getCorrectAnswer().trim();
        boolean isCorrect = userAnswer.equalsIgnoreCase(correctAnswer);
        if (isCorrect) {
            score++;
            scoreLabel.setText(String.valueOf(score));
            feedbackMessageLabel.setText("Awesome! That's a correct answer. 😊");
            feedbackMessageLabel.setTextFill(Color.web("#4caf50"));
        } else {
            feedbackMessageLabel.setText("Good try! The correct answer is " + correctAnswer + ". 😟");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
        }
        answerField.setDisable(true);
        submitButton.setVisible(false);
        nextQuestionButton.setVisible(true);
    }

    // --- CRITICAL FIX: The FXML method must receive the ActionEvent ---
    @FXML
    private void handleNextQuestion(ActionEvent event) {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            loadQuizQuestions();
        } else {
            // Pass the event to endQuiz
            endQuiz(event);
        }
    }

    private void endQuiz(ActionEvent event) {
        showQuizResults(event);
    }

    private void showQuizResults(ActionEvent event) {
        try {
            if (continentName == null || continentName.trim().isEmpty()) {
                System.err.println("Error: continentName is null or empty in showQuizResults");
                return; // Prevent proceeding if continentName is invalid
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/quizresults.fxml"));
            Parent root = loader.load();
            QuizResultsController controller = loader.getController();
            int totalQuestions = questions.size();
            boolean passed = score >= (totalQuestions * 0.8);

            controller.setResults(score, totalQuestions, continentName, passed);

            // Get the main stage from the event source, which is guaranteed to be a valid node
            Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (mainStage == null) {
                System.err.println("Error: mainStage is null in showQuizResults");
                return;
            }
            controller.setMainStage(mainStage);

            controller.setActions(
                    () -> { // onRetry
                        currentQuestionIndex = 0;
                        score = 0;
                        loadQuizQuestions();
                    },
                    () -> { // onContinue
                        if (passed) {
                            //GameStateManager.getInstance().unlockContinent(GameStateManager.getInstance().getNextContinent(continentName));
                            backToHomePage(event);
                        }
                    },
                    () -> { // onPractice
                        // The action is handled by QuizResultsController
                    }
            );

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Quiz Results");
            dialogStage.setScene(new Scene(root));
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToHomePage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backToContinentView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/continentview.fxml"));
            Parent root = loader.load();
            ContinentsController controller = loader.getController();
            controller.setContinentName(this.continentName);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}