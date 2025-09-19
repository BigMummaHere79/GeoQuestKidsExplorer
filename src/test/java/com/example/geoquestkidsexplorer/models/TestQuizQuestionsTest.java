package com.example.geoquestkidsexplorer.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javafx.scene.image.Image;

class TestQuizQuestionsTest {

    // Tests that question text can be returned
    @Test
    void testQuestionText(){
        var question = new TestQuizQuestions("What country is this?","Australia",
                (Image) null);
        assertEquals("What country is this?",question.getQuestionText());
    }

    // test correct answers
    @Test
    void testCorrectAnswer(){
        var answer = new TestQuizQuestions("What country is this?","New Zealand",
                (Image) null);
        assertEquals("New Zealand", answer.getCorrectAnswer());
    }

    // test that if image is null, still show
    @Test
    void imageSetToNullButShows(){
        var image = new TestQuizQuestions("What country is this?","New Zealand",
                (Image) null);
        assertNull(image.countryImage());
    }
}