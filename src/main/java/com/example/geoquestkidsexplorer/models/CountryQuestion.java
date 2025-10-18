package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.Image;

/**
 * Model representing a country question for quizzes.
 * Immutable design for encapsulation; uses private final fields.
 */
public class CountryQuestion {
    private final String countryName;
    private final Image image;
    private final String hints;

    /**
     * Constructs a CountryQuestion.
     * @param countryName The country name.
     * @param image The country image.
     * @param hints Hints for the question.
     */
    public CountryQuestion(String countryName, Image image, String hints) {
        this.countryName = countryName;
        this.image = image;
        this.hints = hints;
    }

    // Getters for encapsulation
    public String getCountryName() {
        return countryName;
    }

    public Image getImage() {
        return image;
    }

    public String getHints() {
        return hints;
    }
}