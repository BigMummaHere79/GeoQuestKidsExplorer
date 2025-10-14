package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EuropeFunFactsTest {

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
    void getAustriaFacts() {
        String[][] facts = getCountryFacts("Austria");
        assertNotNull(facts, "Facts for Austria should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Austria"));
    }

    @Test
    void getBelgiumFacts() {
        String[][] facts = getCountryFacts("Belgium");
        assertNotNull(facts, "Facts for Belgium should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Belgium"));

    }

    @Test
    void getBulgariaFacts() {
        String[][] facts = getCountryFacts("Bulgaria");
        assertNotNull(facts, "Facts for Bulgaria should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Bulgaria"));
    }

    @Test
    void getCroatiaFacts() {
        String[][] facts = getCountryFacts("Croatia");
        assertNotNull(facts, "Facts for Croatia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Croatia"));
    }

    @Test
    void getDenmarkFacts() {
        String[][] facts = getCountryFacts("Denmark");
        assertNotNull(facts, "Facts for Denmark should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Denmark"));
    }

    @Test
    void getFinlandFacts() {
        String[][] facts = getCountryFacts("Finland");
        assertNotNull(facts, "Facts for Finland should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Finland"));
    }

    @Test
    void getFranceFacts() {
        String[][] facts = getCountryFacts("France");
        assertNotNull(facts, "Facts for France should not be null");
        assertTrue(Arrays.deepToString(facts).contains("France"));
    }

    @Test
    void getGermanyFacts() {
        String[][] facts = getCountryFacts("Germany");
        assertNotNull(facts, "Facts for Germany should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Germany"));
    }

    @Test
    void getGreeceFacts() {
        String[][] facts = getCountryFacts("Greece");
        assertNotNull(facts, "Facts for Greece should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Greece"));
    }

    @Test
    void getIrelandFacts() {
        String[][] facts = getCountryFacts("Ireland");
        assertNotNull(facts, "Facts for Ireland should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Ireland"));
    }

    @Test
    void getItalyFacts() {
        String[][] facts = getCountryFacts("Italy");
        assertNotNull(facts, "Facts for Italy should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Italy"));
    }

    @Test
    void getLuxembourgFacts() {
        String[][] facts = getCountryFacts("Luxembourg");
        assertNotNull(facts, "Facts for Luxembourg should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Luxembourg"));
    }

    @Test
    void getMaltaFacts() {
        String[][] facts = getCountryFacts("Malta");
        assertNotNull(facts, "Facts for Malta should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Malta"));
    }

    @Test
    void getNetherlandsFacts() {
        String[][] facts = getCountryFacts("Netherlands");
        assertNotNull(facts, "Facts for Netherlands should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Netherlands"));
    }

    @Test
    void getNorwayFacts() {
        String[][] facts = getCountryFacts("Norway");
        assertNotNull(facts, "Facts for Norway should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Norway"));
    }

    @Test
    void getPolandFacts() {
        String[][] facts = getCountryFacts("Poland");
        assertNotNull(facts, "Facts for Poland should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Poland"));
    }

    @Test
    void getPortugalFacts() {
        String[][] facts = getCountryFacts("Portugal");
        assertNotNull(facts, "Facts for Portugal should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Portugal"));
    }

    @Test
    void getRomaniaFacts() {
        String[][] facts = getCountryFacts("Romania");
        assertNotNull(facts, "Facts for Romania should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Romania"));
    }

    @Test
    void getSerbiaFacts() {
        String[][] facts = getCountryFacts("Serbia");
        assertNotNull(facts, "Facts for Serbia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Serbia"));
    }

    @Test
    void getSpainFacts() {
        String[][] facts = getCountryFacts("Spain");
        assertNotNull(facts, "Facts for Spain should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Spain"));
    }

    @Test
    void getSwedenFacts() {
        String[][] facts = getCountryFacts("Sweden");
        assertNotNull(facts, "Facts for Sweden should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Sweden"));
    }

    @Test
    void getSwitzerlandFacts() {
        String[][] facts = getCountryFacts("Switzerland");
        assertNotNull(facts, "Facts for Switzerland should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Switzerland"));
    }

    @Test
    void getUnitedKingdomFacts() {
        String[][] facts = getCountryFacts("United Kingdom");
        assertNotNull(facts, "Facts for United Kingdom should not be null");
        assertTrue(Arrays.deepToString(facts).contains("United Kingdom"));
    }
}