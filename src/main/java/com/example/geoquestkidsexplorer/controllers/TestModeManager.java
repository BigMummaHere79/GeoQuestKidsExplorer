package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.database.IQuizQuestionDAO;
import com.example.geoquestkidsexplorer.models.PrimaryQuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;

import java.util.ArrayList;
import java.util.List;

public class TestModeManager{
    private final List<TestQuizQuestions> questions;
    private int currentQuestionsIndex = 0;
    private int score = 0;

    public TestModeManager(String continent, int QUESTIONS_PER_QUIZ) {
        this.questions = new ArrayList<>();
        for (int i = 0; i < QUESTIONS_PER_QUIZ; i++){
            TestQuizQuestions question = DatabaseManager.getTestQuizQuestion(continent);
            if (question != null){
                this.questions.add(question);
            }
        }
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

    //For autocomplete:
    public List<String> getCurrentChoices(){
        TestQuizQuestions current = getCurrentQuestion();
        return current != null ? current.getChoices() :  new ArrayList<>();
    }

    public void resetQuiz(){
        currentQuestionsIndex = 0;
        score = 0;
    }
}

