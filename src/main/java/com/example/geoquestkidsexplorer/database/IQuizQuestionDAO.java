package com.example.geoquestkidsexplorer.database;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;

/**
 * This interface helps with creating a Mock for unit testing
 * Calling the main methods for the logic of Practise and Test Quiz
**/

// Created for interface used in RealQuizDataTest

public interface IQuizQuestionDAO {

    PracticeQuizQuestions getPractiseQuizQuestion(String continent);
    TestQuizQuestions getTestQuizQuestion(String continent);
}
