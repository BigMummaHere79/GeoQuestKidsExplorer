package com.example.geoquestkidsexplorer.controllers;

//We don't need GameStateManger at the moment. This is for unlocking countries for later on.
import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.database.DatabaseAdapter;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.database.IQuizQuestionDAO;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * As mentioned within Practise Quiz,
 * this controller can be refactored similar to that of Flashcards
 * This is in order to achieve easier code visibility and testing!
 * **/

public class TestQuizController {

    // You need to declare the quizBox as a member variable with @FXML
    // so it can be accessed by any method in this class.
    @FXML
    private VBox quizBox;

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

    private final IQuizQuestionDAO quizDao;
    private TestQuizQuestions current;

    private String continentName;
    private List<TestQuizQuestions> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private final int QUESTIONS_PER_QUIZ = 10;
    private boolean isSubmitted = false;
    private int timeSeconds = 60;

    /**
     * As of now, because refactoring hasn't started
     * Creating a more or less simple method to call within test
     * Same thing, implements the same logic
     * **/
    public TestQuizController(){
        this(new DatabaseAdapter());
    }
    public TestQuizController(IQuizQuestionDAO dao){
        this.quizDao = dao;
    }
    public TestQuizQuestions fetchTest(String continent){
        current = quizDao.getTestQuizQuestion(continent);
        return current;
    }
    public boolean evaluateAnswer(String chosen){
        return current != null && chosen != null && chosen.equals(current.getCorrectAnswer());
    }
    public TestQuizQuestions getCurrent(){
        return current;
    }

    /**
     * Sets the continent name to load the correct quiz data.
     */
    public void setContinentName(String continent) {
        this.continentName = continent;
        // Update the hardcoded labels with the correct continent name
        quizWelcomeLabel.setText("Practice your knowledge with the " + continentName + " Continent!");
        backButton.setText("⬅️ Back to " + continentName + " Game Mode");

        // --- CRITICAL FIX: Initialize and populate the questions list here ---
        // This was the missing part that caused the NullPointerException.
        this.questions = new ArrayList<>();
        for (int i = 0; i < QUESTIONS_PER_QUIZ; i++) {
            this.questions.add(DatabaseManager.getTestQuizQuestion(continent));
        }
        // Now that we have the continent and the questions, load the first question.
        loadQuestions();
    }

    private void loadQuestions() {
        if (currentQuestionIndex < questions.size()) {
            TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);

            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            countryImageView.setImage(currentQuestion.countryImage());
            questionLabel.setText(currentQuestion.questionText());

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
            // End of Quiz logic
            showResults();
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
                        // Auto-submit a wrong answer on time out
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

    @FXML
    private void handleNextQuestion(ActionEvent event) {
        currentQuestionIndex++;
        loadQuestions();
    }

    private void showResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/quizresults.fxml"));
            Parent root = loader.load();
            QuizResultsController resultsController = loader.getController();

            Stage stage = (Stage) questionNumberLabel.getScene().getWindow();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            dialogStage.setScene(new Scene(root));
            dialogStage.setTitle("Quiz Results");

            double passingScorePercentage = 80.0;
            boolean passed = ((double) score / questions.size() * 100) >= passingScorePercentage;

            resultsController.setDialogStage(dialogStage);
            resultsController.setResults(score, questions.size(), continentName, passed);

            // Set the actions for the buttons in the results dialog
            resultsController.setActions(
                   this::retryQuiz,
                   () -> {
                       String nextContinent = GameStateManager.getInstance().getNextContinent(continentName);
                       if (nextContinent != null) {
                           GameStateManager.getInstance().unlockContinent(nextContinent);
                           // Reload the StartAdventure page to show the unlocked continent
                           try {
                               backToHomePage();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }
                   },
                    this::loadPracticeQuiz
            );

            dialogStage.showAndWait();

            if (!passed) {
                // If the user did not pass, let's reset the quiz state for a retry or practice
                resetQuiz();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        scoreLabel.setText("0");
        loadQuestions();
    }

    private void retryQuiz() {
        resetQuiz();
    }

    // You will need to add this new method to your TestQuizController
    // It will be responsible for loading the practice quiz view
    private void loadPracticeQuiz() {
        System.out.println("Redirecting to the practice quiz...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) questionNumberLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update backToHomePage to not require ActionEvent:
    private void backToHomePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) questionNumberLabel.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

