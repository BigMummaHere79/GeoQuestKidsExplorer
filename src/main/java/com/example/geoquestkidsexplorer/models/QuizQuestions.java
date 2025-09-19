package com.example.geoquestkidsexplorer.models;

import java.util.List;

public class QuizQuestions {
    private String countryCode;
    private String questionText;
    private List<String> choices;
    private String correctAnswer;
    private String funFact;

    public QuizQuestions(String countryCode, String questionText, List<String> choices, String correctAnswer, String funFact) {
        this.countryCode = countryCode;
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.funFact = funFact;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getFunFact() {
        return funFact;
    }
}