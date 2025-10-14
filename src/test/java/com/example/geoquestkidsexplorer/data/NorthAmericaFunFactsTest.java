package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NorthAmericaFunFactsTest {

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
    void getantiguaAndBarbudaFacts() {
        String[][] facts = getCountryFacts("Antigua");
        assertNotNull(facts, "Facts for Antigua should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Antigua"));
    }

    @Test
    void getbahamasFacts() {
        String[][] facts = getCountryFacts("Bahamas");
        assertNotNull(facts, "Facts for Bahamas should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Bahamad"));
    }

    @Test
    void getbarbadosFacts() {
        String[][] facts = getCountryFacts("Barbados");
        assertNotNull(facts, "Facts for Barbados should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Barbados"));
    }

    @Test
    void getbelizeFacts() {
        String[][] facts = getCountryFacts("Belize");
        assertNotNull(facts, "Facts for Belize should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Belize"));
    }

    @Test
    void getcanadaFacts() {
        String[][] facts = getCountryFacts("Canada");
        assertNotNull(facts, "Facts for Canada should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Canada"));
    }

    @Test
    void getcostaRicaFacts() {
        String[][] facts = getCountryFacts("Costa Rica");
        assertNotNull(facts, "Facts for Costa Rica should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Costa Rica"));
    }

    @Test
    void getcubaFacts() {
        String[][] facts = getCountryFacts("CUba");
        assertNotNull(facts, "Facts for Cuba should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Cuba"));
    }

    @Test
    void getdominicaFacts() {
        String[][] facts = getCountryFacts("Dominica");
        assertNotNull(facts, "Facts for Dominica should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Dominica"));
    }

    @Test
    void getdominicanRepublicFacts() {
        String[][] facts = getCountryFacts("Dominican Republic");
        assertNotNull(facts, "Facts for Dominican Republic should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Dominican Republic"));
    }

    @Test
    void getelSalvadorFacts() {
        String[][] facts = getCountryFacts("Salvador");
        assertNotNull(facts, "Facts for Salvador should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Salvador"));
    }

    @Test
    void getgrenadaFacts() {
        String[][] facts = getCountryFacts("Grenada");
        assertNotNull(facts, "Facts for Grenada should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Grenada"));
    }

    @Test
    void getguatemalaFacts() {
        String[][] facts = getCountryFacts("Guatemala");
        assertNotNull(facts, "Facts for Guatemala should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Guatemala"));
    }

    @Test
    void gethaitiFacts() {
        String[][] facts = getCountryFacts("Haiti");
        assertNotNull(facts, "Facts for Haiti should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Haiti"));
    }

    @Test
    void gethondurasFacts() {
        String[][] facts = getCountryFacts("Honduras");
        assertNotNull(facts, "Facts for Honduras should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Honduras"));
    }

    @Test
    void getjamaicaFacts() {
        String[][] facts = getCountryFacts("Jamaica");
        assertNotNull(facts, "Facts for Jamaica should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Jamaica"));
    }

    @Test
    void getmexicoFacts() {
        String[][] facts = getCountryFacts("Mexico");
        assertNotNull(facts, "Facts for Mexico should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Mexico"));
    }

    @Test
    void getnicaraguaFacts() {
        String[][] facts = getCountryFacts("Nicaragua");
        assertNotNull(facts, "Facts for Nicaragua should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Nicaragua"));
    }

    @Test
    void getpanamaFacts() {
        String[][] facts = getCountryFacts("Panama");
        assertNotNull(facts, "Facts for Panama should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Panama"));
    }

    @Test
    void getsaintKittsAndNevisFacts() {
        String[][] facts = getCountryFacts("Saint Kitts and Nevis");
        assertNotNull(facts, "Facts for Saint Kitts and Nevis should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Saint Kitts and Nevis"));
    }

    @Test
    void getsaintLuciaFacts() {
        String[][] facts = getCountryFacts("Saint Lucia");
        assertNotNull(facts, "Facts for Saint Lucia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Saint Lucia"));
    }

    @Test
    void getsaintVincentAndTheGrenadinesFacts() {
        String[][] facts = getCountryFacts("calypso festivals");
        assertNotNull(facts, "Facts for calypso festivals should not be null");
        assertTrue(Arrays.deepToString(facts).contains("calypso festivals"));
    }

    @Test
    void gettrinidadAndTobagoFacts() {
        String[][] facts = getCountryFacts("Trinidad");
        assertNotNull(facts, "Facts for Trinidad should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Trinidad"));
    }

    @Test
    void getunitedStatesFacts() {
        String[][] facts = getCountryFacts("USA");
        assertNotNull(facts, "Facts for USA should not be null");
        assertTrue(Arrays.deepToString(facts).contains("USA"));
    }
}