package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class OceaniaFunFactsTest {

    private String[][] getCountryFacts(String countryName) {
        OceaniaFunFacts oceaniaFunFacts = new OceaniaFunFacts();
        List<Country> countries = oceaniaFunFacts.getCountries();
        return countries.stream()
                .filter(c -> c.getName().equals(countryName))
                .findFirst()
                .map(Country::getFunFacts)
                .orElse(null);
    }

    @Test
    void getaustraliaFacts() {
        String[][] facts = getCountryFacts("Australia");
        assertNotNull(facts, "Facts for Australia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Australia"));
    }

    @Test
    void getfijiFacts() {
        String[][] facts = getCountryFacts("Fiji");
        assertNotNull(facts, "Facts for Fiji should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Fiji"));
    }

    @Test
    void getkiribatiFacts() {
        String[][] facts = getCountryFacts("Kiribati");
        assertNotNull(facts, "Facts for Kiribati should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Kiribati"));
    }

    @Test
    void getmarshallIslandsFacts() {
        String[][] facts = getCountryFacts("Marshall Islands");
        assertNotNull(facts, "Facts for Marshall Islands should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Marshall Islands"));
    }

    @Test
    void getmicronesiaFacts() {
        String[][] facts = getCountryFacts("Micronesia");
        assertNotNull(facts, "Facts for Micronesia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Micronesia"));
    }

    @Test
    void getnauruFacts() {
        String[][] facts = getCountryFacts("Nauru");
        assertNotNull(facts, "Facts for Nauru should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Nauru"));
    }

    @Test
    void getnewZealandFacts() {
        String[][] facts = getCountryFacts("New Zealand");
        assertNotNull(facts, "Facts for New Zealand should not be null");
        assertTrue(Arrays.deepToString(facts).contains("New Zealand"));
    }

    @Test
    void getpalauFacts() {
        String[][] facts = getCountryFacts("Palau");
        assertNotNull(facts, "Facts for Palau should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Palau"));
    }

    @Test
    void getpapuaNewGuineaFacts() {
        String[][] facts = getCountryFacts("Papua New Guinea");
        assertNotNull(facts, "Facts for Papua New Guinea should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Papua New Guinea"));
    }

    @Test
    void getsamoaFacts() {
        String[][] facts = getCountryFacts("Samoa");
        assertNotNull(facts, "Facts for Samoa should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Samoa"));
    }

    @Test
    void getsolomonIslandsFacts() {
        String[][] facts = getCountryFacts("Solomon Islands");
        assertNotNull(facts, "Facts for Solomon Islands should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Solomon Islands"));
    }

    @Test
    void gettongaFacts() {
        String[][] facts = getCountryFacts("Tonga");
        assertNotNull(facts, "Facts for Tonga should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Tonga"));
    }

    @Test
    void gettuvaluFacts() {
        String[][] facts = getCountryFacts("Tuvalu");
        assertNotNull(facts, "Facts for Tuvalu should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Tuvalu"));
    }

    @Test
    void getvanuatuFacts() {
        String[][] facts = getCountryFacts("Vanuatu");
        assertNotNull(facts, "Facts for Vanuatu should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Vanuatu"));
    }
}