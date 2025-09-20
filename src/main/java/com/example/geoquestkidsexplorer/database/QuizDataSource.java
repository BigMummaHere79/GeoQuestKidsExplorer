package com.example.geoquestkidsexplorer.database;
import com.example.geoquestkidsexplorer.database.DatabaseManager.CountryQuestion;

// Mimicked the week 6 Module for creating a Mock
//Had to make an interface so we can mock the database for testing
public interface QuizDataSource {
    CountryQuestion getRandomCountryByContinent(String continent);
    String normalise (String source);
}
