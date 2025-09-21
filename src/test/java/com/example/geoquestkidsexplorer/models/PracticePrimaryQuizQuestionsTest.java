package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.Image;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PracticePrimaryQuizQuestionsTest {
    @Test
    void testConstructorsSetsAndGets(){
        Image dummyImage = null;
        PracticeQuizQuestions questions = new PracticeQuizQuestions(
                "What country is this?",
                List.of("Australia","New Zealand","Solomon Islands","New Caledonia"),
                "New Zealand","Known for Maori culture and landscapes from Lord of the Rings",
                dummyImage
        );

        assertEquals("What country is this?", questions.getQuestionText());
    }

    @Test
    void testNumberOfChoices(){
        Image dummyImage = null;
        PracticeQuizQuestions questions = new PracticeQuizQuestions(
                "What country is this?",
                List.of("Australia","New Zealand","Solomon Islands","New Caledonia"),
                "New Zealand","Known for Maori culture and landscapes from Lord of the Rings",
                dummyImage
        );

        assertEquals(4, questions.getChoices().size());
    }

    @Test
    void testIfCountryContainsSolomonIslands(){
        Image dummyImage = null;
        PracticeQuizQuestions questions = new PracticeQuizQuestions(
                "What country is this?",
                List.of("Australia","New Zealand","Solomon Islands","New Caledonia"),
                "New Zealand","Known for Maori culture and landscapes from Lord of the Rings",
                dummyImage
        );

        assertTrue(questions.getChoices().contains("Solomon Islands"));
    }

    @Test
    void testIfCountryContainsNewZealand(){
        Image dummyImage = null;
        PracticeQuizQuestions questions = new PracticeQuizQuestions(
                "What country is this?",
                List.of("Australia","New Zealand","Solomon Islands","New Caledonia"),
                "New Zealand","Known for Maori culture and landscapes from Lord of the Rings",
                dummyImage
        );
        assertTrue(questions.getChoices().contains("New Zealand"));
    }

    @Test
    void testIfCountryContainsAustralia(){
        Image dummyImage = null;
        PracticeQuizQuestions questions = new PracticeQuizQuestions(
                "What country is this?",
                List.of("Australia","New Zealand","Solomon Islands","New Caledonia"),
                "New Zealand","Known for Maori culture and landscapes from Lord of the Rings",
                dummyImage
        );

        assertTrue(questions.getChoices().contains("Australia"));
    }

    @Test
    void testIfAnswerIsCorrect(){
        var choices = List.of("New Zealand","Australia","Solomon Islands","New Caledonia");
        PracticeQuizQuestions questions = new PracticeQuizQuestions("What country is this?", choices,
                "New Caledonia","French overseas territory, famous for lagoons and reefs",null);

        assertEquals("New Caledonia", questions.getCorrectAnswer());
    }

    @Test
    void testChoices(){
        var choices = List.of("New Zealand","Australia","Solomon Islands","New Caledonia");
        PracticeQuizQuestions questions = new PracticeQuizQuestions("What country is this?", choices,
                "New Caledonia","French overseas territory, famous for lagoons and reefs",null);

        assertEquals(choices, questions.getChoices());
    }

    @Test
    void testNullImage(){
        Image dummyImage = null;
        var choices = new PracticeQuizQuestions("What country is this?",
                List.of("New Zealand","Australia","Solomon Islands","New Caledonia"),
                "New Caledonia","French overseas territory, famous for lagoons and reefs",
                dummyImage);

        assertNull(choices.getCountryImage());
    }

    @Test
    void testFunFact(){
        var choices = new PracticeQuizQuestions("What country is this?",
                List.of("New Zealand","Australia","Solomon Islands","New Caledonia"),
                "New Caledonia","French overseas territory, famous for lagoons and reefs",
                null);

        assertEquals("French overseas territory, famous for lagoons and reefs",choices.getFunFact());
    }
}