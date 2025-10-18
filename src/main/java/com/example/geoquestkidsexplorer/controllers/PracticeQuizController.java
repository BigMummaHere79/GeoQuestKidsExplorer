package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseAdapter;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.repositories.IQuizQuestionDAO;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class PracticeQuizController extends BaseController {

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
    @FXML private Button hintButton;

    private boolean hintUsed = false;
    private String continentName;
    private String username;
    private String avatar;
    private List<PracticeQuizQuestions> questions = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int score = 0;
    private boolean answerChecked = false;
    private static final int NUMBER_OF_PRACTICE_QUESTIONS = 25;
    private PracticeQuizQuestions current;
    private final IQuizQuestionDAO quizDao;

    public PracticeQuizController() {
        this(new DatabaseAdapter());
    }

    public PracticeQuizController(IQuizQuestionDAO dao) {
        this.quizDao = dao;
    }

    public PracticeQuizQuestions fetchPractise(String continent) {
        current = quizDao.getPractiseQuizQuestion(continent);
        return current;
    }

    public boolean evaluateChoice(String chosen) {
        return current != null && chosen != null && chosen.equals(current.getCorrectAnswer());
    }

    public void loadQuestion(String continent) {
        var question = fetchPractise(continent);
        if (questionLabel != null) questionLabel.setText(question.getQuestionText());
        if (countryImageView != null && question.getCountryImage() != null) countryImageView.setImage(question.getCountryImage());
    }

    public PracticeQuizQuestions getCurrent() {
        return current;
    }

    @Override
    protected void setupContinent(String continent) {
        this.continentName = continent;
        if (quizWelcomeLabel != null) {
            quizWelcomeLabel.setText("Flashcards for " + continentName + " Continent!");
        }
        if (backButton != null) {
            backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        }
        System.out.println("PracticeQuizController.setupContinent: continent=" + continent);
    }

    /**
     * Sets profile data for the controller.
     *
     * @param username The username to set.
     * @param avatar   The avatar to set.
     */
    public void setProfileData(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        UserSession.setUser(username, avatar);
    }

    public void setContinentName(String continent) {
        this.continentName = continent;
        if (continentName == null || continentName.trim().isEmpty()) {
            System.err.println("Continent name is null or empty. Skipping quiz load.");
            if (quizWelcomeLabel != null) {
                quizWelcomeLabel.setText("Error: No continent selected.");
            }
            return;
        }
        if (quizWelcomeLabel != null) {
            quizWelcomeLabel.setText("Practice your knowledge with the " + continentName + " Continent!");
        }
        if (backButton != null) {
            backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        }
        loadQuizQuestions();
    }

    @FXML
    public void initialize() {
        feedbackContainer.setVisible(false);
        nextQuestionButton.setVisible(false);
    }

    @FXML
    private void handleHint(ActionEvent event) {
        if (hintUsed || currentQuestionIndex >= questions.size()) {
            return;
        }
        hintUsed = true;
        hintButton.setDisable(true);
        PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();
        List<VBox> incorrectOptions = new ArrayList<>();
        for (VBox vBox : new VBox[]{option1Tile, option2Tile, option3Tile, option4Tile}) {
            if (!vBox.isDisable() && !((RadioButton) vBox.getChildren().get(0)).getText().equals(correctAnswer)) {
                incorrectOptions.add(vBox);
            }
        }
        if (!incorrectOptions.isEmpty()) {
            VBox toRemove = incorrectOptions.get(ThreadLocalRandom.current().nextInt(incorrectOptions.size()));
            toRemove.setDisable(true);
            toRemove.setVisible(false);
        }
    }

    private void loadQuizQuestions() {
        if (continentName == null || continentName.isEmpty()) {
            System.err.println("Continent name is not set. Cannot load practice quiz questions.");
            if (questionLabel != null) {
                questionLabel.setText("No continent selected.");
            }
            return;
        }
        questions.clear();
        for (int i = 0; i < NUMBER_OF_PRACTICE_QUESTIONS; i++) {
            PracticeQuizQuestions q = DatabaseManager.getPracticeQuizQuestion(continentName);
            if (q != null) {
                questions.add(q);
            }
        }
        if (questions.isEmpty()) {
            if (questionLabel != null) {
                questionLabel.setText("No questions found for " + continentName + ". Please check your database.");
            }
            return;
        }
        loadQuestion();
    }

    private void loadQuestion() {
        answerChecked = false;
        if (currentQuestionIndex < questions.size()) {
            PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
            VBox[] vBoxes = new VBox[]{option1Tile, option2Tile, option3Tile, option4Tile};
            for (VBox vbox : vBoxes) {
                vbox.setDisable(false);
                vbox.setStyle(null);
                vbox.setVisible(true);
            }
            answerGroup.selectToggle(null);
            if (questionNumberLabel != null) {
                questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            }
            if (countryImageView != null) {
                countryImageView.setImage(currentQuestion.getCountryImage());
            }
            if (questionLabel != null) {
                questionLabel.setText(currentQuestion.getQuestionText());
            }
            List<String> options = new ArrayList<>(currentQuestion.getChoices());
            Collections.shuffle(options, ThreadLocalRandom.current());
            if (option1 != null) option1.setText(options.get(0));
            if (option2 != null) option2.setText(options.get(1));
            if (option3 != null) option3.setText(options.get(2));
            if (option4 != null) option4.setText(options.get(3));
            resetStyles();
            answerGroup.selectToggle(null);
            disableRadioButtons(false);
            feedbackContainer.setVisible(false);
            nextQuestionButton.setVisible(false);
            hintUsed = false;
            hintButton.setDisable(false);
        } else {
            endQuiz();
        }
    }

    @FXML
    private void handleAnswerSelection(ActionEvent event) {
        if (!answerChecked) {
            RadioButton selectedRadio = (RadioButton) event.getSource();
            VBox selectedVbox = (VBox) selectedRadio.getParent();
            checkAnswer(selectedVbox);
        }
    }

    private void checkAnswer(VBox selectedVbox) {
        if (questions.isEmpty() || currentQuestionIndex >= questions.size()) {
            System.err.println("No questions available to check.");
            return;
        }
        answerChecked = true;
        PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        RadioButton selectedRadioButton = (RadioButton) selectedVbox.getChildren().get(0);
        String selectedAnswer = selectedRadioButton.getText();
        boolean isCorrect = selectedAnswer.equals(currentQuestion.getCorrectAnswer());
        for (VBox vbox : new VBox[]{option1Tile, option2Tile, option3Tile, option4Tile}) {
            vbox.setDisable(true);
        }
        disableRadioButtons(true);
        hintButton.setDisable(true);
        if (isCorrect) {
            score++;
            if (scoreLabel != null) {
                scoreLabel.setText(String.valueOf(score));
            }
            selectedVbox.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 5;");
            feedbackMessageLabel.setText("Awesome! That's a correct answer. 😊");
            feedbackMessageLabel.setTextFill(Color.web("#4caf50"));
        } else {
            selectedVbox.setStyle("-fx-background-color: #ffccbc; -fx-background-radius: 5;");
            for (VBox vbox : new VBox[]{option1Tile, option2Tile, option3Tile, option4Tile}) {
                RadioButton rb = (RadioButton) vbox.getChildren().get(0);
                if (rb.getText().equals(currentQuestion.getCorrectAnswer())) {
                    vbox.setStyle("-fx-background-color: #a5d6a7; -fx-background-radius: 10px;");
                    break;
                }
            }
            feedbackMessageLabel.setText("Good try! The correct answer is " + currentQuestion.getCorrectAnswer() + ". 😟");
            feedbackMessageLabel.setTextFill(Color.web("#f44336"));
        }
        if (funFactLabel != null) {
            funFactLabel.setText(currentQuestion.getFunFact());
        }
        feedbackContainer.setVisible(true);
        nextQuestionButton.setVisible(true);
    }

    private void resetStyles() {
        if (option1Tile != null) option1Tile.setStyle(null);
        if (option2Tile != null) option2Tile.setStyle(null);
        if (option3Tile != null) option3Tile.setStyle(null);
        if (option4Tile != null) option4Tile.setStyle(null);
    }

    private void disableRadioButtons(boolean disable) {
        if (option1 != null) option1.setDisable(disable);
        if (option2 != null) option2.setDisable(disable);
        if (option3 != null) option3.setDisable(disable);
        if (option4 != null) option4.setDisable(disable);
    }

    @FXML
    private void handleNextQuestion(ActionEvent event) {
        currentQuestionIndex++;
        loadQuestion();
    }

    private void endQuiz() {
        if (questionNumberLabel != null) {
            questionNumberLabel.setText("Quiz Complete!");
        }
        if (questionLabel != null) {
            questionLabel.setText("You have finished the " + continentName + " practice quiz!");
        }
        if (countryImageView != null) {
            countryImageView.setImage(null);
        }
        if (feedbackMessageLabel != null) {
            feedbackMessageLabel.setText("Final Score: " + score + " out of " + questions.size());
        }
        if (funFactLabel != null) {
            funFactLabel.setText("You can now try the Test Mode Quiz!");
        }
        feedbackContainer.setVisible(true);
        nextQuestionButton.setVisible(false);
        disableRadioButtons(true);
        option1Tile.setVisible(false);
        option2Tile.setVisible(false);
        option3Tile.setVisible(false);
        option4Tile.setVisible(false);
    }

    @FXML
    private void backToGameModes(ActionEvent event) {
        try {
            NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                    "/com/example/geoquestkidsexplorer/continentview.fxml",
                    (ContinentsController controller) -> {
                        controller.setContinentName(continentName);
                        controller.setProfileData(username, avatar);
                        controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}