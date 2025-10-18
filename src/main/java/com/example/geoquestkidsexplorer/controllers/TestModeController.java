package com.example.geoquestkidsexplorer.controllers;

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
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestModeController {

    @FXML private VBox quizBox;
    @FXML private Label questionNumberLabel;
    @FXML private Label scoreLabel;
    @FXML private ImageView countryImageView;
    @FXML private Label quizWelcomeLabel;
    @FXML private Label questionLabel;
    @FXML private Label countryImagePlaceholder;

    // NEW: TextField (we removed the ComboBox)
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

    // Autocomplete support
    private ContextMenu suggestionsMenu; // NOTE: change this as this is causing the build error for MVN builds when the test runs
    private List<String> currentChoices = List.of();
    private int highlightedIndex = -1; // for up/down navigation

    public TestModeController() { this(new DatabaseAdapter()); }
    public TestModeController(IQuizQuestionDAO dao) { this.quizDao = dao; }


    /**
     * Fetches a single test quiz question for corresponding continent.
     *
     * @param continent the name of the continent
     * @return a {@link TestQuizQuestions} object (or null if none)
     */
    public TestQuizQuestions fetchTest(String continent) {
        current = quizDao.getTestQuizQuestion(continent);
        return current;
    }

    /**
     * Evaluates whether their answer matches the correct answer
     *
     * @param chosen the user’s answer text
     * @return true if it exactly matches the correct answer; false otherwise
     */
    public boolean evaluateAnswer(String chosen) {
        return current != null && chosen != null && chosen.equals(current.getCorrectAnswer());
    }

    /**
     * Returns the currently loaded quiz question.
     *
     * @return the current {@link TestQuizQuestions} object
     */
    public TestQuizQuestions getCurrent() { return current; }


    /**
     * Sets the continent name and loads the quiz questions accordingly.
     *
     * @param continent the name of the continent to test
     */
    public void setContinentName(String continent) {
        this.continentName = continent;
        quizWelcomeLabel.setText("Practice your knowledge with the " + continentName + " Continent!");
        backButton.setText("⬅️ Back to " + continentName + " Game Mode");

        this.questions = new ArrayList<>();
        for (int i = 0; i < QUESTIONS_PER_QUIZ; i++) {
            this.questions.add(DatabaseManager.getTestQuizQuestion(continent));
        }
        loadQuestions();
    }

    @FXML
    public void initialize() {
        suggestionsMenu = new ContextMenu(); // ✅ initialize safely here
        // Wire up TextField events for autocomplete
        setupAutoCompleteBehavior();
    }

    /* -------------------- Autocomplete (pure JavaFX) -------------------- */

    private void setupAutoCompleteBehavior() {
        // Text change → filter and (re)show suggestions
        answerField.textProperty().addListener((obs, oldV, newV) -> {
            highlightedIndex = -1;
            if (newV == null || newV.isBlank()) {
                suggestionsMenu.hide();
            } else {
                showSuggestions(newV);
            }
        });

        // Key handling for navigation / selection
        answerField.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN -> moveHighlight(+1);
                case UP -> moveHighlight(-1);
                case ENTER, TAB -> {
                    if (suggestionsMenu.isShowing() && !suggestionsMenu.getItems().isEmpty()) {
                        // if we have a highlighted item use it; else choose the first suggestion
                        String val = getHighlightedOrFirst();
                        if (val != null) {
                            answerField.setText(val);
                            answerField.positionCaret(val.length());
                        }
                        suggestionsMenu.hide();
                    }
                }
                case ESCAPE -> suggestionsMenu.hide();
                default -> { /* no-op */ }
            }
        });

        // Hide suggestions when field loses focus
        answerField.focusedProperty().addListener((obs, was, is) -> {
            if (!is) suggestionsMenu.hide();
        });
    }

    /**
     * Filters and displays suggestion items that begin with the typed prefix.
     *
     * @param typed the current text input by the user
     */
    private void showSuggestions(String typed) {
        if (currentChoices == null || currentChoices.isEmpty()) {
            suggestionsMenu.hide();
            return;
        }
        String q = typed.toLowerCase(Locale.ROOT);

        List<String> filtered = currentChoices.stream()
                .filter(c -> c != null && c.toLowerCase(Locale.ROOT).startsWith(q))
                .sorted()
                .limit(10)
                .toList();

        if (filtered.isEmpty()) {
            suggestionsMenu.hide();
            return;
        }

        suggestionsMenu.getItems().clear();
        for (int i = 0; i < filtered.size(); i++) {
            final int index = i;
            String suggestion = filtered.get(i);
            CustomMenuItem item = buildSuggestionItem(suggestion);
            // mouse click → select
            item.setOnAction(ev -> {
                answerField.setText(suggestion);
                answerField.positionCaret(suggestion.length());
                suggestionsMenu.hide();
                // (Optional) auto-submit on click:
                // handleSubmit();
            });
            suggestionsMenu.getItems().add(item);
        }
        highlightedIndex = -1;
        if (!suggestionsMenu.isShowing()) {
            suggestionsMenu.show(answerField, Side.BOTTOM, 0, 0);
        }
    }

    private CustomMenuItem buildSuggestionItem(String text) {
        Label lbl = new Label(text);
        lbl.setStyle("-fx-padding: 6 12; -fx-font-size: 14px;");
        CustomMenuItem item = new CustomMenuItem(lbl, true);
        item.getStyleClass().add("auto-item");
        return item;
    }

    /**
     * Moves the highlight index among suggestion items, cycling through the list.
     *
     * @param delta +1 to move down, -1 to move up
     */
    private void moveHighlight(int delta) {
        if (!suggestionsMenu.isShowing() || suggestionsMenu.getItems().isEmpty()) return;

        int size = suggestionsMenu.getItems().size();
        int newIndex = highlightedIndex + delta;
        if (newIndex < 0) newIndex = size - 1;
        if (newIndex >= size) newIndex = 0;

        // clear old
        if (highlightedIndex >= 0 && highlightedIndex < size) {
            setItemHighlight(highlightedIndex, false);
        }
        highlightedIndex = newIndex;
        setItemHighlight(highlightedIndex, true);
    }

    private void setItemHighlight(int idx, boolean on) {
        if (idx < 0 || idx >= suggestionsMenu.getItems().size()) return;
        CustomMenuItem item = (CustomMenuItem) suggestionsMenu.getItems().get(idx);
        Label lbl = (Label) item.getContent();
        if (on) {
            lbl.setStyle("-fx-padding: 6 12; -fx-font-size: 14px; -fx-background-color: #e0f2fe;");
        } else {
            lbl.setStyle("-fx-padding: 6 12; -fx-font-size: 14px;");
        }
    }

    /**
     * Returns the highlighted suggestion text, or the first suggestion if none is highlighted.
     *
     * @return selected suggestion text or null if no suggestions
     */
    private String getHighlightedOrFirst() {
        if (!suggestionsMenu.isShowing() || suggestionsMenu.getItems().isEmpty()) return null;
        int idx = highlightedIndex >= 0 ? highlightedIndex : 0;
        CustomMenuItem item = (CustomMenuItem) suggestionsMenu.getItems().get(idx);
        return ((Label) item.getContent()).getText();
    }

    /* -------------------- Quiz flow -------------------- */


    private void loadQuestions() {

        if (questions.isEmpty()) {
            System.err.println("No questions available for quiz. Returning to continent view.");
            feedbackMessageLabel.setText("Error: No questions available.");
            feedbackMessageLabel.setTextFill(Color.RED);
            try {
                backToContinentView(new ActionEvent());
            } catch (Exception e) {
                System.err.println("Error returning to continent view: " + e.getMessage());
            }
            return;
        }

        if (currentQuestionIndex < questions.size()) {
            TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);

            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            countryImageView.setImage(currentQuestion.getCountryImage());
            questionLabel.setText(currentQuestion.getQuestionText());

            // (Re-)bind choices to autocomplete
            currentChoices = currentQuestion.getChoices();
            suggestionsMenu.hide();
            answerField.clear();
            answerField.setDisable(false);
            answerField.setPromptText("Type your answer...");

            submitButton.setVisible(true);
            nextQuestionButton.setVisible(true);
            submitButton.setDisable(false);
            nextQuestionButton.setDisable(true);

            feedbackMessageLabel.setText("");
            isSubmitted = false;

            startTimer();
        } else {
            showResults();
        }
    }

    /**
     * Starts a 60‑second timer limit for user to answer question
     * When time reaches zero, stops the timer and triggers
     * {@link #handleTimeEnd()}.
     */
    private void startTimer() {
        if (timeline != null) timeline.stop();

        timeSeconds = 60;
        timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeSeconds--;
                    timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                    if (timeSeconds <= 0) {
                        timeline.stop();
                        handleTimeEnd();
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    // NOTE: Adding additional helper for making the timer ends after 60 seconds countdown! GLENDA.
    private void handleTimeEnd() {
        if (isSubmitted) return;

        isSubmitted = true;
        suggestionsMenu.hide();
        answerField.setDisable(true);
        submitButton.setDisable(true);
        nextQuestionButton.setDisable(false);

        TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer().trim();
        feedbackMessageLabel.setText("Time's up! The correct answer is " + correctAnswer + ". 😟");
        feedbackMessageLabel.setTextFill(Color.web("#f44336"));
    }

    @FXML
    private void handleSubmit() {
        if (isSubmitted || timeSeconds <= 0) // NOTE: adding timeSeconds to check if timer is/or 0.
            return;

        isSubmitted = true;
        if (timeline != null) timeline.stop();
        suggestionsMenu.hide();

        TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String userAnswer = answerField.getText();

        if (userAnswer == null || userAnswer.trim().isEmpty()) {
            feedbackMessageLabel.setText("Please type or select an answer before submitting!");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
            isSubmitted = false;
            if (timeline != null) timeline.play();
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

        answerField.setDisable(true);
        submitButton.setDisable(true);
        nextQuestionButton.setDisable(false);
    }


    /**
     * navigates user to the next quiz question
     *
     * @param event the action event triggered to continue to next question
     */
    @FXML
    private void handleNextQuestion(ActionEvent event) {
        currentQuestionIndex++;
        loadQuestions();
    }

    private void showResults() {
        try {
            double scorePercentage = (double) score / questions.size() * 100;
            int currentLevel = 0;

            // Fetch the current level of the continent
            String sql = "SELECT level FROM continents WHERE continent = ?";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, continentName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        currentLevel = rs.getInt("level");
                    } else {
                        System.err.println("No level found for continent: " + continentName);
                        feedbackMessageLabel.setText("Error: Continent data not found.");
                        feedbackMessageLabel.setTextFill(Color.RED);
                        return;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Database error fetching level: " + e.getMessage());
                feedbackMessageLabel.setText("Database error. Please try again.");
                feedbackMessageLabel.setTextFill(Color.RED);
                return;
            }

            String username = UserSession.getUsername();
            if (username == null || username.isEmpty()) {
                System.err.println("No valid username found in UserSession.");
                feedbackMessageLabel.setText("Error: Please log in to save your progress.");
                feedbackMessageLabel.setTextFill(Color.RED);
                return;
            }

            // Save quiz result and check if level was updated
            boolean levelUpdated = DatabaseManager.saveQuizResultAndUpdateLevel(username, currentLevel, scorePercentage);
            boolean passed = scorePercentage >= 80.0;

            // For failed quizzes, levelUpdated will be false, but the result was saved successfully
            // Only show error if there was a database issue (handled in DatabaseManager)
            if (!passed && !levelUpdated) {
                System.out.println("Quiz failed for user " + username + ", score: " + scorePercentage + ". No level update needed.");
            } else if (passed && levelUpdated) {
                String nextContinent = GameStateManager.getInstance().getNextContinent(continentName);
                if (nextContinent != null) {
                    GameStateManager.getInstance().unlockContinent(nextContinent);
                    GameStateManager.getInstance().saveState();
                    System.out.println("Unlocked continent: " + nextContinent);
                } else {
                    System.out.println("No next continent available for " + continentName);
                }
            }

            // Load and show the results dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/testresults.fxml"));
            Parent root = loader.load();
            TestResultsController resultsController = loader.getController();

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(questionNumberLabel.getScene() != null ? questionNumberLabel.getScene().getWindow() : null);
            dialogStage.setScene(new Scene(root, 1200, 800));
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
                                System.err.println("Error navigating to homepage: " + e.getMessage());
                                feedbackMessageLabel.setText("Error returning to homepage.");
                                feedbackMessageLabel.setTextFill(Color.RED);
                            }
                        }
                    },
                    () -> {
                        loadPracticeQuiz();
                        dialogStage.close();
                    }
            );

            dialogStage.showAndWait();

        } catch (IOException e) {
            System.err.println("Error loading results: " + e.getMessage());
            feedbackMessageLabel.setText("Error displaying results. Please try again.");
            feedbackMessageLabel.setTextFill(Color.RED);
        }
    }

    /**
     * Navigates back to the homepage
     * @throws IOException if the FXML or scene loading fails
     */
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
    }

    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        scoreLabel.setText("0");
        loadQuestions();
    }

    private void retryQuiz() { resetQuiz(); }

    private void loadPracticeQuiz() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/continentview.fxml"));
            Parent root = loader.load();
            ContinentsController controller = loader.getController();
            controller.setContinentName(this.continentName); // Set the continent name
            Scene scene = new Scene(root, 1200.0, 800.0);
            Stage stage = (Stage) questionNumberLabel.getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates back to the homepage view
     *
     * @param event the action event by triggering button
     */
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
