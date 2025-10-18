package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.database.DatabaseAdapter;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.repositories.IQuizQuestionDAO;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TestModeController extends BaseController {

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
    private final IQuizQuestionDAO quizDao;
    private TestQuizQuestions current;
    private String continentName;
    private String username;
    private String avatar;
    private List<TestQuizQuestions> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private final int QUESTIONS_PER_QUIZ = 10;
    private boolean isSubmitted = false;
    private int timeSeconds = 60;
    private ContextMenu suggestionsMenu;
    private List<String> currentChoices = List.of();
    private int highlightedIndex = -1;

    public TestModeController() {
        this(new DatabaseAdapter());
    }

    public TestModeController(IQuizQuestionDAO dao) {
        this.quizDao = dao;
    }

    public TestQuizQuestions fetchTest(String continent) {
        current = quizDao.getTestQuizQuestion(continent);
        return current;
    }

    public boolean evaluateAnswer(String chosen) {
        return current != null && chosen != null && chosen.equals(current.getCorrectAnswer());
    }

    public TestQuizQuestions getCurrent() {
        return current;
    }

    @Override
    public void setProfileData(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        UserSession.setUser(username, avatar);
        System.out.println("TestModeController.setProfileData: username=" + username + ", avatar=" + avatar);
    }

    @Override
    protected void setupContinent(String continent) {
        this.continentName = continent;
        if (quizWelcomeLabel != null) {
            quizWelcomeLabel.setText("Practice your knowledge with the " + continentName + " Continent!");
        }
        if (backButton != null) {
            backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        }
        this.questions = new ArrayList<>();
        for (int i = 0; i < QUESTIONS_PER_QUIZ; i++) {
            TestQuizQuestions question = DatabaseManager.getTestQuizQuestion(continent);
            if (question != null) {
                this.questions.add(question);
            }
        }
        System.out.println("TestModeController.setupContinent: continent=" + continent + ", questions loaded=" + questions.size());
        loadQuestions();
    }

    public void setContinentName(String continent) {
        setupContinent(continent);
    }

    @FXML
    public void initialize() {
        suggestionsMenu = new ContextMenu();
        setupAutoCompleteBehavior();
        String sessionUsername = UserSession.getUsername();
        String sessionAvatar = UserSession.getAvatar();
        if (sessionUsername != null && !sessionUsername.isEmpty()) {
            setProfileData(sessionUsername, sessionAvatar);
        } else {
            System.err.println("TestModeController.initialize: UserSession is empty");
        }
    }

    private void setupAutoCompleteBehavior() {
        answerField.textProperty().addListener((obs, oldV, newV) -> {
            highlightedIndex = -1;
            if (newV == null || newV.isBlank()) {
                suggestionsMenu.hide();
            } else {
                showSuggestions(newV);
            }
        });

        answerField.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case DOWN -> moveHighlight(+1);
                case UP -> moveHighlight(-1);
                case ENTER, TAB -> {
                    if (suggestionsMenu.isShowing() && !suggestionsMenu.getItems().isEmpty()) {
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

        answerField.focusedProperty().addListener((obs, was, is) -> {
            if (!is) suggestionsMenu.hide();
        });
    }

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
            item.setOnAction(ev -> {
                answerField.setText(suggestion);
                answerField.positionCaret(suggestion.length());
                suggestionsMenu.hide();
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

    private void moveHighlight(int delta) {
        if (!suggestionsMenu.isShowing() || suggestionsMenu.getItems().isEmpty()) return;
        int size = suggestionsMenu.getItems().size();
        int newIndex = highlightedIndex + delta;
        if (newIndex < 0) newIndex = size - 1;
        if (newIndex >= size) newIndex = 0;
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

    private String getHighlightedOrFirst() {
        if (!suggestionsMenu.isShowing() || suggestionsMenu.getItems().isEmpty()) return null;
        int idx = highlightedIndex >= 0 ? highlightedIndex : 0;
        CustomMenuItem item = (CustomMenuItem) suggestionsMenu.getItems().get(idx);
        return ((Label) item.getContent()).getText();
    }

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
            if (questionNumberLabel != null) {
                questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            }
            if (countryImageView != null) {
                countryImageView.setImage(currentQuestion.getCountryImage());
            }
            if (questionLabel != null) {
                questionLabel.setText(currentQuestion.getQuestionText());
            }
            currentChoices = currentQuestion.getChoices();
            suggestionsMenu.hide();
            if (answerField != null) {
                answerField.clear();
                answerField.setDisable(false);
                answerField.setPromptText("Type your answer...");
            }
            if (submitButton != null) submitButton.setVisible(true);
            if (nextQuestionButton != null) nextQuestionButton.setVisible(true);
            if (submitButton != null) submitButton.setDisable(false);
            if (nextQuestionButton != null) nextQuestionButton.setDisable(true);
            if (feedbackMessageLabel != null) feedbackMessageLabel.setText("");
            isSubmitted = false;
            startTimer();
        } else {
            showResults();
        }
    }

    private void startTimer() {
        if (timeline != null) timeline.stop();
        timeSeconds = 60;
        if (timerLabel != null) {
            timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
        }
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    timeSeconds--;
                    if (timerLabel != null) {
                        timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
                    }
                    if (timeSeconds <= 0) {
                        timeline.stop();
                        handleTimeEnd();
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void handleTimeEnd() {
        if (isSubmitted) return;
        isSubmitted = true;
        suggestionsMenu.hide();
        if (answerField != null) answerField.setDisable(true);
        if (submitButton != null) submitButton.setDisable(true);
        if (nextQuestionButton != null) nextQuestionButton.setDisable(false);
        TestQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer().trim();
        feedbackMessageLabel.setText("Time's up! The correct answer is " + correctAnswer + ". 😟");
        feedbackMessageLabel.setTextFill(Color.web("#f44336"));
    }

    @FXML
    private void handleSubmit() {
        if (isSubmitted || timeSeconds <= 0) return;
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
            if (scoreLabel != null) {
                scoreLabel.setText(String.valueOf(score));
            }
            feedbackMessageLabel.setText("Awesome! That's a correct answer. 😊");
            feedbackMessageLabel.setTextFill(Color.web("#4caf50"));
        } else {
            feedbackMessageLabel.setText("Good try! The correct answer is " + correctAnswer + ". 😟");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
        }
        if (answerField != null) answerField.setDisable(true);
        if (submitButton != null) submitButton.setDisable(true);
        if (nextQuestionButton != null) nextQuestionButton.setDisable(false);
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
                        System.err.println("No level found for continent: " + continentName);
                        feedbackMessageLabel.setText("Error: Continent data not found.");
                        feedbackMessageLabel.setTextFill(Color.RED);
                        backToContinentView(new ActionEvent());
                        return;
                    }
                }
            } catch (SQLException e) {
                System.err.println("Database error fetching level: " + e.getMessage());
                e.printStackTrace();
                feedbackMessageLabel.setText("Database error. Please try again.");
                feedbackMessageLabel.setTextFill(Color.RED);
                backToContinentView(new ActionEvent());
                return;
            }

            String effectiveUsername = this.username != null && !this.username.isEmpty() ?
                    this.username : UserSession.getUsername();
            if (effectiveUsername == null || effectiveUsername.isEmpty()) {
                System.err.println("showResults: No valid username found. Local username="
                        + this.username + ", UserSession username=" + UserSession.getUsername());
                feedbackMessageLabel.setText("Error: Please log in to save your progress.");
                feedbackMessageLabel.setTextFill(Color.RED);
                backToContinentView(new ActionEvent());
                return;
            }

            boolean levelUpdated = DatabaseManager.saveQuizResultAndUpdateLevel(effectiveUsername, currentLevel, scorePercentage);
            boolean passed = scorePercentage >= 80.0;
            System.out.println("showResults: username=" + effectiveUsername + ", scorePercentage=" + scorePercentage + ", " +
                    "passed=" + passed + ", levelUpdated=" + levelUpdated);

            if (!passed && !levelUpdated) {
                System.out.println("Quiz failed for user " + effectiveUsername + ", score: " + scorePercentage + "." +
                        " No level update needed.");
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/testresults.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException e) {
                System.err.println("Error loading testresults.fxml: " + e.getMessage());
                e.printStackTrace();
                feedbackMessageLabel.setText("Error displaying results. Please try again.");
                feedbackMessageLabel.setTextFill(Color.RED);
                backToContinentView(new ActionEvent());
                return;
            }

            TestResultsController resultsController = loader.getController();
            if (resultsController == null) {
                System.err.println("TestResultsController is null");
                feedbackMessageLabel.setText("Error: Failed to initialize results dialog.");
                feedbackMessageLabel.setTextFill(Color.RED);
                backToContinentView(new ActionEvent());
                return;
            }

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Stage primaryStage = questionNumberLabel != null && questionNumberLabel.getScene() != null
                    ? (Stage) questionNumberLabel.getScene().getWindow() : null;
            if (primaryStage == null) {
                System.err.println("Primary stage is null, using default stage");
            }
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(root, 1200, 800));
            dialogStage.setTitle("Quiz Results");
            dialogStage.setResizable(false);

            try {
                resultsController.setDialogStage(dialogStage);
                resultsController.setResults(score, questions.size(), continentName, passed);
                resultsController.setActions(
                        () -> {
                            System.out.println("showResults: Retry action triggered");
                            retryQuiz();
                            dialogStage.close();
                        },
                        () -> {
                            System.out.println("showResults: Home action triggered");
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
                            System.out.println("showResults: Practice action triggered");
                            loadPracticeQuiz();
                            dialogStage.close();
                        }
                );
                System.out.println("showResults: Displaying dialog for user " + effectiveUsername);
                dialogStage.showAndWait();
            } catch (Exception e) {
                System.err.println("Error setting up results dialog: " + e.getMessage());
                e.printStackTrace();
                feedbackMessageLabel.setText("Error displaying results. Please try again.");
                feedbackMessageLabel.setTextFill(Color.RED);
                dialogStage.close();
                backToContinentView(new ActionEvent());
            }
        } catch (Exception e) {
            System.err.println("Unexpected error in showResults: " + e.getMessage());
            e.printStackTrace();
            feedbackMessageLabel.setText("Unexpected error occurred. Please try again.");
            feedbackMessageLabel.setTextFill(Color.RED);
            backToContinentView(new ActionEvent());
        }
    }

    private void backToHomePage() throws IOException {
        System.out.println("backToHomePage: Navigating with username=" + username);
        NavigationHelper.loadSceneWithConfig(questionNumberLabel, "/com/example/geoquestkidsexplorer/homepage.fxml",
                (HomePageController controller) -> {
                    controller.setProfileData(username, avatar);
                    controller.refreshContinentLocks();
                    controller.setStage((Stage) questionNumberLabel.getScene().getWindow());
                });
    }

    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        if (scoreLabel != null) {
            scoreLabel.setText("0");
        }
        loadQuestions();
    }

    private void retryQuiz() {
        resetQuiz();
    }

    private void loadPracticeQuiz() {
        try {
            System.out.println("loadPracticeQuiz: Navigating with username=" + username);
            NavigationHelper.loadSceneWithConfig(questionNumberLabel, "/com/example/geoquestkidsexplorer/continentview.fxml",
                    (ContinentsController controller) -> {
                        controller.setContinentName(continentName);
                        controller.setProfileData(username, avatar);
                        controller.setStage((Stage) questionNumberLabel.getScene().getWindow());
                    });
        } catch (IOException e) {
            System.err.println("Error loading practice quiz: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void backToContinentView(ActionEvent event) {
        try {
            System.out.println("backToContinentView: Navigating with username=" + username);
            NavigationHelper.loadSceneWithConfig((Node) event.getSource(), "/com/example/geoquestkidsexplorer/continentview.fxml",
                    (ContinentsController controller) -> {
                        controller.setContinentName(continentName);
                        controller.setProfileData(username, avatar);
                        controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                    });
        } catch (IOException e) {
            System.err.println("Error navigating back to continent view: " + e.getMessage());
            e.printStackTrace();
        }
    }
}