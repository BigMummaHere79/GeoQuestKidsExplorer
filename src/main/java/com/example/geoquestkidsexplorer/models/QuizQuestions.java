package com.example.geoquestkidsexplorer.models;

import java.util.List;

public class QuizQuestions {
    private String countryImage;
    private String questionText;
    private String correctAnswer;

    public QuizQuestions(String countryImage, String questionText, String correctAnswer) {
        this.countryImage = countryImage;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public String getCountryCode() {
        return countryImage;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}