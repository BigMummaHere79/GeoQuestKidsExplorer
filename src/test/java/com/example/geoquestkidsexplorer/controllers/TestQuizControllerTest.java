package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.fakes.FakeQuizDao;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing the model class
 * Logic is also pulled from FakeQuiz which implements the interface
 * FakeQuiz is used as a mock to replicate input into database
 * **/

class TestQuizControllerTest {

    private TestModeController controller;

    @BeforeEach
    void startUp(){
        controller = new TestModeController(new FakeQuizDao());
    }

    @Test
    void testReturnsNotNull(){
        TestQuizQuestions questions = controller.fetchTest("Oceania");
        assertNotNull(questions);
    }

    @Test
    void returnsExpectedQuestionText(){
        TestQuizQuestions questions = controller.fetchTest("Oceania");
        assertEquals("What country is this?", questions.getQuestionText());
    }

    @Test
    void  testCorrectAnswer(){
        TestQuizQuestions questions = controller.fetchTest("Oceania");
        assertEquals("Australia",questions.getCorrectAnswer());
    }

    @Test
    void testAnswerCorrectTrue(){
        TestQuizQuestions questions = controller.fetchTest("Oceania");
        assertTrue(controller.evaluateAnswer("Australia"));
    }

    @Test
    void incorrectAnswer(){
        TestQuizQuestions questions = controller.fetchTest("Oceania");
        assertFalse(controller.evaluateAnswer("New Zealand"));
    }

    @Test
    void evaluateAnswerNullForFalse() {
        controller.fetchTest("Oceania");
        assertFalse(controller.evaluateAnswer(null));
    }

}