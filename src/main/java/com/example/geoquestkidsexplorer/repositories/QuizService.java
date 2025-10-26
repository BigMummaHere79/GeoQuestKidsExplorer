package com.example.geoquestkidsexplorer.repositories;

import com.example.geoquestkidsexplorer.models.Country;
import com.example.geoquestkidsexplorer.models.CountryQuestion;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for quiz and country-related operations.
 * Abstracts quiz generation and country data retrieval.
 */
public interface QuizService {
    /**
     * Gets a random country question from a continent.
     * @param continent The continent name.
     * @return CountryQuestion or null.
     */
    CountryQuestion getRandomCountryByContinent(String continent);

    /**
     * Generates a practice quiz question.
     * @param continent The continent.
     * @return PracticeQuizQuestions or null.
     */
    PracticeQuizQuestions getPracticeQuizQuestion(String continent);

    /**
     * Generates a test quiz question.
     * @param continent The continent.
     * @return TestQuizQuestions or null.
     */
    TestQuizQuestions getTestQuizQuestion(String continent);

    /**
     * Gets country image by name.
     * @param countryName The country.
     * @return Image or null.
     */
    javafx.scene.image.Image getCountryImage(String countryName);

    /**
     * Gets N random countries from continent, excluding one.
     * @param continent The continent.
     * @param limit Number of countries.
     * @param exclude Country to exclude.
     * @return List of country names.
     */
    List<String> getNRandomCountriesByContinent(String continent, int limit, String exclude);

    /**
     * Gets all countries by continent.
     * @param continent The continent.
     * @return List of Country.
     * @throws SQLException On error.
     */
    List<Country> getCountriesByContinent(String continent) throws SQLException;

    /**
     * Gets continent name by level.
     * @param level The level (1-7).
     * @return Continent name or null.
     */
    String getContinentByLevel(int level);

    /**
     * Normalizes a string for comparison (trim, lowercase).
     * @param s The string.
     * @return Normalized string.
     */
    static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    /**
     * Will be used to make sure continents is being fetch from the database.
     **/
    List<String> getAllContinentsInOrder();
}
