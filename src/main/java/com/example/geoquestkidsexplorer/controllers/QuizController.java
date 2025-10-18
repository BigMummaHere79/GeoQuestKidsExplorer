package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.IQuizQuestionDAO;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * Abstract base class for quiz-related controllers, encapsulating common quiz logic like question loading and timers.
 * Subclasses override for specific quiz types (polymorphism).
 */
public abstract class QuizController extends BaseController {

    protected IQuizQuestionDAO quizDao;
    protected String continentName;
    protected int currentQuestionIndex = 0;
    protected int score = 0;
    protected Timeline timeline;
    protected int timeSeconds = 60;

    /**
     * Constructor for dependency injection of DAO.
     *
     * @param dao the quiz question DAO.
     */
    protected QuizController(IQuizQuestionDAO dao) {
        this.quizDao = dao;
    }

    /**
     * Loads the next question (abstract for polymorphism in subclasses).
     */
    protected abstract void loadQuestion();

    /**
     * Starts a countdown timer.
     */
    protected void startTimer(Label timerLabel) {
        timeSeconds = 60;
        timerLabel.setText(String.format("%02d:%02d", timeSeconds / 60, timeSeconds % 60));
        timeline = new Timeline();
        // Implementation as in original TestModeController/PracticeQuizController
    }

    /**
     * Handles navigation back to continent view.
     *
     * @param event the action event.
     * @throws IOException if FXML load fails.
     */
    protected void backToContinentView(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/geoquestkidsexplorer/continentview.fxml");
    }
}
