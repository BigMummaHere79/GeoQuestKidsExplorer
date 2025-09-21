package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.Image;
import java.util.List;

public class PracticeQuizQuestions extends PrimaryQuizQuestions {
    private String funFact;

    public PracticeQuizQuestions(String questionText, List<String> choices, String correctAnswer, String funFact, Image countryImage) {
        // The `super` keyword calls the constructor of the parent class
        super(questionText, choices, correctAnswer, countryImage);
        this.funFact = funFact;
    }

    // Getter for the unique funFact property
    public String getFunFact() {
        return funFact;
    }
}
