package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.CountryQuestion;
import com.example.geoquestkidsexplorer.repositories.QuizDataSource;

/**
 *
 Instance based way to fetch quiz data instead of calling
 static DatabaseManager methods directly.
 Letting the controllers depend on QuizDataSource interface so we can plug mock for tests
**/

// Use Implements -> Derived from the Modules week 9 I believe for more info
public final class RealQuizDataSource implements QuizDataSource {

    @Override
    public CountryQuestion getRandomCountryByContinent(String continent) {
        return DatabaseManager.getRandomCountryByContinent(continent);
    }

    @Override
    public String normalise(String source) {
        return DatabaseManager.normalize(source);
    }
}
