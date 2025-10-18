package com.example.geoquestkidsexplorer.fakes;

import com.example.geoquestkidsexplorer.repositories.IQuizQuestionDAO;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;

/** Just replicates objects that would be input into database
 * Implements the interface
 * **/

import java.util.ArrayList;
import java.util.List;
/**This fake test **/

public class FakeQuizDao implements IQuizQuestionDAO {
    @Override
    public PracticeQuizQuestions getPractiseQuizQuestion(String continent) {
        return new PracticeQuizQuestions("What country is this?", List.of("Australia","New Caledonia",
                "Solomon Islands","New Zealand"),"New Zealand","Known for Maori culture and landscapes from Lord of the Rings",
                null);
    }

    @Override
    public TestQuizQuestions getTestQuizQuestion(String continent) {
        return (
                new TestQuizQuestions("What country is this?", new ArrayList<>(), "Australia", 10, null));
    }
}
