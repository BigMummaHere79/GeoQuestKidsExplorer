package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.Country;
import com.example.geoquestkidsexplorer.models.CountryQuestion;
import com.example.geoquestkidsexplorer.models.PracticeQuizQuestions;
import com.example.geoquestkidsexplorer.models.TestQuizQuestions;
import com.example.geoquestkidsexplorer.repositories.QuizService;
import com.example.geoquestkidsexplorer.utils.DatabaseService;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of QuizService.
 * Handles quiz question generation and country data.
 * Note: Assumes countries table is seeded elsewhere; funFacts parsing is stubbed (add column if needed).
 * Uses static normalize from interface.
 */
public class DatabaseQuizService extends DatabaseService implements QuizService {

    @Override
    public CountryQuestion getRandomCountryByContinent(String continent) {
        final String sql = """
            SELECT country, country_picture, hints
            FROM countries
            WHERE continent = ?
            ORDER BY RANDOM()
            LIMIT 1
            """;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, continent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String country = rs.getString("country");
                    String hints = rs.getString("hints");
                    byte[] bytes = rs.getBytes("country_picture");
                    Image img = (bytes != null && bytes.length > 0)
                            ? new Image(new ByteArrayInputStream(bytes))
                            : null;
                    return new CountryQuestion(country, img, hints);
                }
            }
        } catch (SQLException e) {
            System.err.println("getRandomCountryByContinent error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public PracticeQuizQuestions getPracticeQuizQuestion(String continent) {
        CountryQuestion correctQuestion = getRandomCountryByContinent(continent);
        if (correctQuestion == null) return null;

        String correctAnswer = correctQuestion.getCountryName();
        Image countryImage = correctQuestion.getImage();
        String hints = correctQuestion.getHints();

        List<String> choices = getNRandomCountriesByContinent(continent, 3, correctAnswer);
        choices.add(correctAnswer);
        Collections.shuffle(choices);

        String questionText = "Which country is this?";
        String funFact = "This is " + correctAnswer + ". " + hints;

        return new PracticeQuizQuestions(questionText, choices, correctAnswer, funFact, countryImage);
    }

    @Override
    public TestQuizQuestions getTestQuizQuestion(String continent) {
        CountryQuestion correctQuestion = getRandomCountryByContinent(continent);
        if (correctQuestion == null) return null;

        String questionText = "Which country is this?";
        String correctAnswer = correctQuestion.getCountryName();
        Image countryImage = correctQuestion.getImage();

        List<String> choices = new ArrayList<>();
        choices.add(correctAnswer);
        List<String> incorrectAnswers = getNRandomCountriesByContinent(continent, 3, correctAnswer);
        choices.addAll(incorrectAnswers);
        Collections.shuffle(choices);

        int scoreValue = 10;

        return new TestQuizQuestions(questionText, choices, correctAnswer, scoreValue, countryImage);
    }

    @Override
    public Image getCountryImage(String countryName) {
        String sql = "SELECT country_picture FROM countries WHERE country = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, countryName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    byte[] imgBytes = rs.getBytes("country_picture");
                    if (imgBytes != null) {
                        return new Image(new ByteArrayInputStream(imgBytes));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getNRandomCountriesByContinent(String continent, int limit, String exclude) {
        List<String> countries = new ArrayList<>();
        String sql = "SELECT country FROM countries WHERE continent = ? AND country != ? ORDER BY RANDOM() LIMIT ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, continent);
            ps.setString(2, exclude);
            ps.setInt(3, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    countries.add(rs.getString("country"));
                }
            }
        } catch (SQLException e) {
            System.err.println("getNRandomCountriesByContinent error: " + e.getMessage());
        }
        return countries;
    }

    @Override
    public List<Country> getCountriesByContinent(String continent) throws SQLException {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT country, continent FROM countries WHERE continent = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("country");
                    String continentName = rs.getString("continent");
                    String flagFileName = name.replaceAll(" ", "") + ".png";
                    // Note: funFactsJson not in table; stub or add column
                    String funFactsJson = null; // rs.getString("funFacts");
                    String[][] funFacts = parseFunFacts(funFactsJson);
                    countries.add(new Country(name, continentName, flagFileName, funFacts));
                }
            }
        }
        return countries;
    }

    private static String[][] parseFunFacts(String funFactsStr) {
        // Stub: Implement JSON/delimited parsing
        return new String[0][];
    }

    @Override
    public String getContinentByLevel(int level) {
        String sql = "SELECT continent FROM continents WHERE level = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, level);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("continent");
                }
            }
        } catch (SQLException e) {
            System.err.println("getContinentByLevel error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllContinentsInOrder() {
        List<String> continents = new ArrayList<>();
        String sql = "SELECT continent FROM continents ORDER BY level ASC";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                continents.add(rs.getString("continent"));
            }
        } catch (SQLException e) {
            System.err.println("getAllContinentsInOrder error: " + e.getMessage());
        }
        return continents;
    }
}