package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class SouthAmericaFunFactsTest {

    private String[][] getCountryFacts(String countryName) {
        SouthAmericaFunFacts southAmericaFunFacts = new SouthAmericaFunFacts();
        List<Country> countries = southAmericaFunFacts.getCountries();
        return countries.stream()
                .filter(c -> c.getName().equals(countryName))
                .findFirst()
                .map(Country::getFunFacts)
                .orElse(null);
    }

    @Test
    void getargentinaFacts() {
        String[][] facts = getCountryFacts("Argentina");
        assertNotNull(facts, "Facts for Argentina should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Argentina"));
    }

    @Test
    void getboliviaFacts() {
        String[][] facts = getCountryFacts("Bolivia");
        assertNotNull(facts, "Facts for Bolivia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Bolivia"));
    }

    @Test
    void getbrazilFacts() {
        String[][] facts = getCountryFacts("Brazil");
        assertNotNull(facts, "Facts for Brazil should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Brazil"));
    }

    @Test
    void getchileFacts() {
        String[][] facts = getCountryFacts("Chile");
        assertNotNull(facts, "Facts for Chile should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Chile"));
    }

    @Test
    void getcolombiaFacts() {
        String[][] facts = getCountryFacts("Colombia");
        assertNotNull(facts, "Facts for Colombia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Colombia"));
    }

    @Test
    void getecuadorFacts() {
        String[][] facts = getCountryFacts("Ecuador");
        assertNotNull(facts, "Facts for Ecuador should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Ecuador"));
    }

    @Test
    void getguyanaFacts() {
        String[][] facts = getCountryFacts("Guyana");
        assertNotNull(facts, "Facts for Guyana should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Guyana"));
    }

    @Test
    void getparaguayFacts() {
        String[][] facts = getCountryFacts("Paraguay");
        assertNotNull(facts, "Facts for Paraguay should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Paraguay"));
    }

    @Test
    void getperuFacts() {
        String[][] facts = getCountryFacts("Peru");
        assertNotNull(facts, "Facts for Peru should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Peru"));
    }

    @Test
    void getsurinameFacts() {
        String[][] facts = getCountryFacts("Suriname");
        assertNotNull(facts, "Facts for Suriname should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Suriname"));
    }

    @Test
    void geturuguayFacts() {
        String[][] facts = getCountryFacts("Uruguay");
        assertNotNull(facts, "Facts for Uruguay should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Uruguay"));
    }

    @Test
    void getvenezuelaFacts() {
        String[][] facts = getCountryFacts("Venezuela");
        assertNotNull(facts, "Facts for Venezuela should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Venezuela"));
    }
}