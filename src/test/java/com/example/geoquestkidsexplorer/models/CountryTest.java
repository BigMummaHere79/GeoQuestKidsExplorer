package com.example.geoquestkidsexplorer.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    private Country newCountry(String name) {
        return new Country(name, "Oceania", "oceaniaflags/", new String[][]{{
                "Samoa consists of two main islands: Upolu and Savai’i.", "🏝️"}});
    }

    @Test
    void getName() {
        Country country = newCountry("Fiji");
        assertEquals("Fiji", country.getName());
    }

    @Test
    void setName() {
        Country country = newCountry("Fiji");
        country.setName("Samoa");
        assertEquals("Samoa", country.getName());
    }

    @Test
    void getContinent() {
        Country country = newCountry("Samoa");
        assertEquals("Oceania", country.getContinent());
    }

    @Test
    void setContinent() {
        Country country = newCountry("Samoa");
        country.setContinent("South America");
        assertEquals("South America", country.getContinent());
    }

    @Test
    void getFlagFileName() {
        Country country = newCountry("Samoa");
        assertEquals("oceaniaflags/", country.getFlagFileName());
    }

    @Test
    void setFlagFileName() {
        Country country = newCountry("Samoa");
        country.setFlagFileName("Vanuatu.png");
        assertEquals("Vanuatu.png", country.getFlagFileName());
    }

    @Test
    void getFunFacts() {
        Country country = newCountry("Samoa");
        assertNotNull(country.getFunFacts());
    }

    @Test
    void setFunFacts() {
        Country country = newCountry("Samoa");
        country.setFunFacts(new String[][]{{
                "Samoa consists of two main islands", "🏝️"}});
        assertNotNull(country.getFunFacts());
    }
}