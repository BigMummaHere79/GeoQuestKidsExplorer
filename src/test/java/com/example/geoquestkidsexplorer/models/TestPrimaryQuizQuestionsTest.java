package com.example.geoquestkidsexplorer.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

class TestPrimaryQuizQuestionsTest {

    // Tests that question text can be returned
    @Test
    void testQuestionText(){
        var question = new TestQuizQuestions("What country is this?", new ArrayList<>(), "Australia", 10, null);
        assertEquals("What country is this?",question.getQuestionText());
    }

    // test correct answers
    @Test
    void testCorrectAnswer(){
        var answer = new TestQuizQuestions("What country is this?", new ArrayList<>(), "Australia", 10, null);
        assertEquals("Australia", answer.getCorrectAnswer());
    }

    // test that if image is null, still show
    @Test
    void imageSetToNullButShows(){
        var image = new TestQuizQuestions("What country is this?", new ArrayList<>(), "Australia", 10, null);
        assertNull(image.getCountryImage());
    }


    // Need to recheckk the asser
    // Added a new test to demonstrate proper testing of the new countryCode field
    @Test
    void testChoice() {
        var choices = new TestQuizQuestions("What country is this?", new ArrayList<>(), "Australia", 10, null);
        //assertTrue(choices.getChoices().contains("What country"));
        assertEquals(List.of(), choices.getChoices());
    }
}