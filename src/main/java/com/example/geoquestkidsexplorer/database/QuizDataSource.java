package com.example.geoquestkidsexplorer.database;
import com.example.geoquestkidsexplorer.models.CountryQuestion;

/**
 * Abstraction for retrieving individual quiz data items and
 * applying standard nornalisation rules
 * */

// Mimicked the week 6 Module for creating a Mock
//Had to make an interface so we can mock the database for testing
public interface QuizDataSource {
    CountryQuestion getRandomCountryByContinent(String continent);
    String normalise (String source);
}
