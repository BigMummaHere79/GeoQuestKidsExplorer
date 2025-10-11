package com.example.geoquestkidsexplorer.controllers;

//We don't need GameStateManger at the moment. This is for unlocking countries for later on.
import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.database.DatabaseAdapter;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.database.IQuizQuestionDAO;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;
import com.example.geoquestkidsexplorer.models.UserSession;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * As mentioned within Practise Quiz,
 * this controller can be refactored similar to that of Flashcards
 * This is in order to achieve easier code visibility and testing!
 * **/
public class TestModeController {

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
    @FXML private ComboBox<String> answerDropdown;
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

    /** For testing:
     * As of now, because refactoring hasn't started
     * Creating a more or less simple method to call within test
     * Same thing, implements the same logic
     * **/
    public TestModeController(){
        this(new DatabaseAdapter());
    }
    public TestModeController(IQuizQuestionDAO dao){
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

     //Sets the continent name to load the correct quiz data.
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

    //Controller UI set up:
    //Once the user starts typing all the options show:
    @FXML
    public void initialize(){
        //make ComboBox to editable so the user can type
        answerDropdown.setEditable(true);

        // //Add event listener to detect once the user starts typing
        //Automatically shows options when the user starts typing
        //Using an inner anonymous class:
        answerDropdown.getEditor().textProperty().addListener(new javafx.beans.value.ChangeListener<String>(){
            @Override
            public void changed(javafx.beans.value.ObservableValue<? extends String> observable, String oldValue, String newValue){
                if(!answerDropdown.isShowing()){
                    answerDropdown.show();
                }
            }
        });
    }

    //Adjust this:
    //To populate the dropdown:
    //Clear old options
    //Add multiple-choice answers for the current question
    //Reset the selected value
    private void loadQuestions() {
        if (currentQuestionIndex < questions.size()) {
            TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);

            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            countryImageView.setImage(currentQuestion.getCountryImage());
            questionLabel.setText(currentQuestion.getCorrectAnswer());

            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            questionLabel.setText(currentQuestion.getQuestionText());

            //Reset and populate ComboBox
            answerDropdown.getItems().clear();
            answerDropdown.getItems().addAll(currentQuestion.getChoices());
            answerDropdown.getSelectionModel().clearSelection();
            answerDropdown.setDisable(false);
            answerDropdown.setPromptText("Type and select your answer");


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



    //Adjust this
    // To check the selected answer
    //Read selected answer instead if text input
    //To evaluate answer correctness
    @FXML
    private void handleSubmit() {
        if (isSubmitted) return;

        isSubmitted = true;
        timeline.stop();

        TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String userAnswer = answerDropdown.getValue();
        //if user submits before selecting an answer or without typing thus also checks for an empty string
        if(userAnswer == null || userAnswer.trim().isEmpty()){
            //Prompt user to select an answer
            feedbackMessageLabel.setText("Please type or select an answer before submitting!");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
            isSubmitted = false;
            timeline.play();
            return;
        }

        userAnswer = userAnswer.trim();
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

        answerDropdown.setDisable(true);
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
            double scorePercentage = (double) score / questions.size() * 100;
            int currentLevel = 0;
            String sql = "SELECT level FROM continents WHERE continent = ?";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, continentName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        currentLevel = rs.getInt("level");
                    } else {
                        System.err.println("showResults: No level found for continent: " + continentName);
                    }
                }
            } catch (SQLException e) {
                System.err.println("showResults: Error fetching level for " + continentName + ": " + e.getMessage());
                e.printStackTrace();
                return;
            }

            // Save result and update level
            String username = UserSession.getUsername();
            if (username == null || username.isEmpty()) {
                System.err.println("showResults: No user logged in, cannot save results");
                return;
            }

            boolean levelUpdated = DatabaseManager.saveQuizResultAndUpdateLevel(username, currentLevel, scorePercentage);
            boolean passed = scorePercentage >= 80.0;
            if (passed && levelUpdated) {
                String nextContinent = GameStateManager.getInstance().getNextContinent(continentName);
                if (nextContinent != null) {
                    GameStateManager.getInstance().unlockContinent(nextContinent);
                    GameStateManager.getInstance().saveState();
                    System.out.println("showResults: Unlocked " + nextContinent + " for user " + username);
                } else {
                    System.out.println("showResults: No next continent available for " + continentName);
                }
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/testresults.fxml"));
            Parent root = loader.load();
            TestResultsController resultsController = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(questionNumberLabel.getScene().getWindow());
            dialogStage.setScene(new Scene(root, 400, 300));
            dialogStage.setTitle("Quiz Results");

            resultsController.setDialogStage(dialogStage);
            resultsController.setResults(score, questions.size(), continentName, passed);

            resultsController.setActions(
                    this::retryQuiz,
                    () -> {
                        dialogStage.close();
                        if (passed && levelUpdated) {
                            try {
                                backToHomePage();
                            } catch (IOException e) {
                                System.err.println("backToHomePage error: " + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    },
                    () -> {
                        loadPracticeQuiz();
                        dialogStage.close();
                    }
            );

            dialogStage.showAndWait();

            if (!passed) {
                resetQuiz();
            } else if (levelUpdated) {
                Stage quizStage = (Stage) questionNumberLabel.getScene().getWindow();
                quizStage.close();
                try {
                    backToHomePage();
                } catch (IOException e) {
                    System.err.println("backToHomePage error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("showResults error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void backToHomePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
        Parent root = loader.load();
        HomePageController controller = loader.getController();
        controller.refreshContinentLocks();
        Scene scene = new Scene(root, 1200.0, 800.0);
        Stage stage = (Stage) questionNumberLabel.getScene().getWindow();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        System.out.println("backToHomePage: Navigated to homepage, refreshed locks");
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


    private void loadPracticeQuiz() {
        System.out.println("Redirecting to the practice quiz...");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1200.0, 800.0);
            Stage stage = (Stage) questionNumberLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
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