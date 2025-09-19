package com.example.geoquestkidsexplorer.database;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.QuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;

// Created for interface used in RealQuizDataTest

public interface IQuizQuestionDAO {

    PracticeQuizQuestions getPractiseQuizQuestion(String continent);
    TestQuizQuestions getTestQuizQuestion(String continent);
}
