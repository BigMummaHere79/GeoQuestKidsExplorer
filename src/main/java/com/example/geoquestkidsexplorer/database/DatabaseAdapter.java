package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;

/**
* Wasn't abe to directly use Database as it has static methods
* This Adapter preserves existing code while giving instance-based dependency
* */

public class DatabaseAdapter implements IQuizQuestionDAO {
    @Override
    public PracticeQuizQuestions getPractiseQuizQuestion(String continent) {
        return DatabaseManager.getPracticeQuizQuestion(continent);
    }

    @Override
    public TestQuizQuestions getTestQuizQuestion(String continent) {
        return DatabaseManager.getTestQuizQuestion(continent);
    }
}
