package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.Image;
import java.util.List;

public record TestQuizQuestions(
        String questionText,
        String correctAnswer,
        Image countryImage
) {
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }
}