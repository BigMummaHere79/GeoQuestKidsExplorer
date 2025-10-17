package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.IQuizQuestionDAO;
import com.example.geoquestkidsexplorer.models.PrimaryQuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;

import java.util.List;

public class TestModeManager{
    private final IQuizQuestionDAO quizDao;
    private List<TestQuizQuestions> questions;
    private int currentQuestionsIndex = 0;
    private int score = 0;

    public TestModeManager(IQuizQuestionDAO quizDao, List<TestQuizQuestions> questions) {
        this.quizDao = quizDao;
    }

    public TestQuizQuestions getCurrentQuestion(){
        if (currentQuestionsIndex < questions.size()){
            return questions.get(currentQuestionsIndex);
        }
        return null;
    }

    public boolean submitAnswer(String userAnswer){
        TestQuizQuestions current = getCurrentQuestion();
        if (current == null)
            return false;

        boolean correct = userAnswer.equalsIgnoreCase(current.getCorrectAnswer());
        if (correct)
            score++;
        return correct;
    }

    public void nextQuestion(){
        currentQuestionsIndex++;
    }

    public boolean hasNextQuestion(){
        return currentQuestionsIndex + 1 < questions.size();
    }

    public int getScore(){
        return score;
    }

    public int getCurrentQuestionsIndex() {
        return currentQuestionsIndex;
    }

    public int getTotalQuestions(){
        return questions.size();
    }

    public void resetQuiz(){
        currentQuestionsIndex = 0;
        score = 0;
    }
}

