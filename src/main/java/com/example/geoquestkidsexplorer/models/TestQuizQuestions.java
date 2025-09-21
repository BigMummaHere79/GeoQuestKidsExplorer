package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.Image;
import java.util.List;

public class TestQuizQuestions extends PrimaryQuizQuestions {
    private int scoreValue;

    public TestQuizQuestions(String questionText, List<String> choices, String correctAnswer, int scoreValue, Image countryImage) {
        // Calls the constructor of the parent class
        super(questionText, choices, correctAnswer, countryImage);
        this.scoreValue = scoreValue;
    }

    // Getter for the unique scoreValue property
    public int getScoreValue() {
        return scoreValue;
    }
}