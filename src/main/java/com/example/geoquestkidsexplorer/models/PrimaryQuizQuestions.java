package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.Image;
import java.util.List;

public class PrimaryQuizQuestions {
    private String questionText;
    private List<String> choices;
    private String correctAnswer;
    private Image countryImage;

    public PrimaryQuizQuestions (String questionText, List<String> choices, String correctAnswer, Image countryImage) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.countryImage = countryImage;
    }

    // Getters for all properties
    public String getQuestionText() {
        return questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Image getCountryImage() {
        return countryImage;
    }
}