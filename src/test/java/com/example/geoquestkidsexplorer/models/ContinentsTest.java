package com.example.geoquestkidsexplorer.models;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContinentsTest {

    private Continents newCountry() {
        Country fiji  =  new Country("Oceania", "Fiji", "Fiji.png",
                new String[][] {{"Fiji", "🏝️" }});
        return new Continents("Fiji","oceaniaflags/", "ffffff", List.of(fiji));
    }

    @Test
    void getName() {
        Continents continents = newCountry();
        assertEquals("Fiji", continents.getName());
    }

    @Test
    void setName() {
        Continents continents = newCountry();
        continents.setName("Africa");
        assertEquals("Africa", continents.getName());
    }

    @Test
    void getFlagDirectory() {
        Continents continents = newCountry();
        assertEquals("oceaniaflags/", continents.getFlagDirectory());
    }

    @Test
    void setFlagDirectory() {
        Continents continents = newCountry();
        continents.setFlagDirectory("oceaniaflags/");
        assertEquals("oceaniaflags/", continents.getFlagDirectory());
    }

    @Test
    void getBackgroundColor() {
        Continents continents = newCountry();
        assertEquals("ffffff", continents.getBackgroundColor());
    }

    @Test
    void setBackgroundColor() {
        Continents continents = newCountry();
        continents.setBackgroundColor("000000");
        assertEquals("000000", continents.getBackgroundColor());
    }

    @Test
    void getCountries() {
        Continents continents = newCountry();
        assertNotNull(continents.getCountries());
    }

    @Test
    void setCountries() {
        Continents continents = newCountry();
        continents.setCountries(List.of(new Country("Vanuatu"," Oceania","Australia",
                new  String[][]{{ "Vanuatu is an archipelago of 82 islands in the South Pacific.", "🏝️"}})));
        assertEquals("Vanuatu", continents.getCountries().get(0).getName());
    }

}