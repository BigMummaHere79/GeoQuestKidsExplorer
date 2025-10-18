package com.example.geoquestkidsexplorer.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryQuestionTest {

    private CountryQuestion countryQuestion(String name){
        return new CountryQuestion(name, null,"Starts with");
    }

    @Test
    void testGetCountryName() {
        CountryQuestion question = countryQuestion("Peru");
        assertEquals("Peru", question.getCountryName());
    }

    @Test
    void testGetImage() {
        CountryQuestion question = countryQuestion("Peru");
        assertNull(question.getImage(), "Image can be null in test");
    }

    @Test
    void testGetHints() {
        CountryQuestion question = countryQuestion("Peru");
        assertTrue(question.getHints().startsWith("Starts with"));
    }
}