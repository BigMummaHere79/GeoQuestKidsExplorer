package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseAdapter;
import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.database.IQuizQuestionDAO;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

    /**
     * CREATED THESE METHODS FOR UNIT TESTING
     * Like Flash Cards, I believe these can be refactored a lot more to seperate logic from UI
     * into other classes.
     * These will be done in the future
     **/
    private PracticeQuizQuestions current;
    private final IQuizQuestionDAO quizDao;

    // FXML/runtime
    public PracticeQuizController(){
        this(new DatabaseAdapter());
    }
    //Unit tests
    public PracticeQuizController(IQuizQuestionDAO dao)
    {
        this.quizDao = dao;
    }

    // Used by tests (No fxml, no JavaFx
    public PracticeQuizQuestions fetchPractise(String continent){
        current = quizDao.getPractiseQuizQuestion(continent);
        return current;
    }
    // Used by tests to compare chosen vs correct
    public boolean evaluateChoice(String chosen){
        return current != null && chosen != null && chosen.equals((current.getCorrectAnswer()));
    }
    //For unit-testing
    public void loadQuestion(String continent){
        var question = fetchPractise(continent);
        if(questionLabel != null) questionLabel.setText(question.getQuestionText());
        if(countryImageView != null && question.getCountryImage() != null) countryImageView.setImage(question.getCountryImage());
    }
    //Helper for Assertions
    public PracticeQuizQuestions getCurrent(){
        return current;
    }
    /****
     *
     * ***/

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

    //This is for the Hint button
    @FXML
    private Button hintButton;
    private boolean hintUsed = false;

    @FXML
    private void handleHint(ActionEvent event) {
        if (hintUsed || currentQuestionIndex >= questions.size()) {
            return;
        }

        hintUsed = true;             // Mark hint as used
        hintButton.setDisable(true); // Disable the button right away

        PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();

        List<RadioButton> incorrectOptions = new ArrayList<>();
        for (RadioButton rb : new RadioButton[]{option1, option2, option3, option4}) {
            if (!rb.isDisable() && !rb.getText().equals(correctAnswer)) {
                incorrectOptions.add(rb);
            }
        }

        if (!incorrectOptions.isEmpty()) {
            RadioButton toRemove = incorrectOptions.get(ThreadLocalRandom.current().nextInt(incorrectOptions.size()));
            toRemove.setDisable(true);
            toRemove.setVisible(false); // Hide the wrong answer
        }
    }



    @FXML
    public void initialize() {
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

            PracticeQuizQuestions currentQuestion = questions.get(currentQuestionIndex);
            // Reset and re-enable all radio buttons
            RadioButton[] radioButtons = new RadioButton[]{option1, option2, option3, option4};
            for (RadioButton rb : radioButtons) {
                rb.setSelected(false);
                rb.setDisable(false); // Re-enable the buttons
                rb.setStyle(null); // Clear any styles
                // Make sure they are visible, just in case
                rb.setVisible(true);
            }
            // Make sure the toggle group is cleared
            answerGroup.selectToggle(null);

            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
            countryImageView.setImage(currentQuestion.getCountryImage());
            questionLabel.setText(currentQuestion.getQuestionText());

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

            hintUsed = false;
            hintButton.setDisable(false);

        } else {
            // End of Quiz Logic
            endQuiz();
        }
    }

    // New method to handle direct RadioButton selection
    @FXML
    private void handleAnswerSelection(ActionEvent event) {
        if (!answerChecked) {
            checkAnswer((RadioButton) event.getSource());
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

        hintButton.setDisable(true);   // Disables hint after an answer is selected


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

        // New: Hide the radio button tiles at the end of the quiz
        option1Tile.setVisible(false);
        option2Tile.setVisible(false);
        option3Tile.setVisible(false);
        option4Tile.setVisible(false);
    }

    @FXML
    private void backToGameModes(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/continentview.fxml"));
            Parent root = loader.load();
            ContinentsController controller = loader.getController();
            controller.setContinentName(this.continentName);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
