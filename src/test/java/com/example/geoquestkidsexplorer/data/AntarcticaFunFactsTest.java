package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AntarcticaFunFactsTest {

    private String[][] getCountryFacts(String countryName) {
        AfricaFunFacts africaFunFacts = new AfricaFunFacts();
        List<Country> countries = africaFunFacts.getCountries();
        return countries.stream()
                .filter(c -> c.getName().equals(countryName))
                .findFirst()
                .map(Country::getFunFacts)
                .orElse(null);
    }

    @Test
    void getantarcticaFacts() {
        String[][] facts = getCountryFacts("Antarctica");
        assertNotNull(facts, "Facts for Antarctica should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Antarctica"));
    }
}