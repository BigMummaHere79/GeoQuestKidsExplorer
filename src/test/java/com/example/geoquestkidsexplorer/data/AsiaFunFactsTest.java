package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AsiaFunFactsTest {

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
    void getafghanistanFacts() {
        String[][] facts = getCountryFacts("Afghanistan");
        assertNotNull(facts, "Facts for Afghanistan should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Afghanistan"));
    }

    @Test
    void getbahrainFacts() {
        String[][] facts = getCountryFacts("Bahrain");
        assertNotNull(facts, "Facts for Bahrain should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Bahrain"));
    }

    @Test
    void getbangladeshFacts() {
        String[][] facts = getCountryFacts("Bangladesh");
        assertNotNull(facts, "Facts for Bangladesh should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Bangladesh"));
    }

    @Test
    void getbruneiFacts() {
        String[][] facts = getCountryFacts("Brunei");
        assertNotNull(facts, "Facts for Brunei should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Brunei"));
    }

    @Test
    void getcambodiaFacts() {
        String[][] facts = getCountryFacts("Cambodia");
        assertNotNull(facts, "Facts for Cambodia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Cambodia"));
    }

    @Test
    void getchinaFacts() {
        String[][] facts = getCountryFacts("China");
        assertNotNull(facts, "Facts for China should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Chine"));
    }

    @Test
    void getindiaFacts() {
        String[][] facts = getCountryFacts("India");
        assertNotNull(facts, "Facts for India should not be null");
        assertTrue(Arrays.deepToString(facts).contains("India"));
    }

    @Test
    void getindonesiaFacts() {
        String[][] facts = getCountryFacts("Indonesia");
        assertNotNull(facts, "Facts for Indonesia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Indonesia"));
    }

    @Test
    void getisraelFacts() {
        String[][] facts = getCountryFacts("Israel");
        assertNotNull(facts, "Facts for Israel should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Israel"));
    }

    @Test
    void getjapanFacts() {
        String[][] facts = getCountryFacts("Japan");
        assertNotNull(facts, "Facts for Japan should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Japan"));
    }

    @Test
    void getlebanonFacts() {
        String[][] facts = getCountryFacts("Lebanon");
        assertNotNull(facts, "Facts for Lebanon should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Lebanon"));
    }

    @Test
    void getmalaysiaFacts() {
        String[][] facts = getCountryFacts("Malaysia");
        assertNotNull(facts, "Facts for Malaysia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Malaysia"));
    }

    @Test
    void getmaldivesFacts() {
        String[][] facts = getCountryFacts("Maldives");
        assertNotNull(facts, "Facts for Maldives should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Maldives"));
    }

    @Test
    void getnepalFacts() {
        String[][] facts = getCountryFacts("Nepal");
        assertNotNull(facts, "Facts for Nepal should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Nepal"));
    }

    @Test
    void getphilippinesFacts() {
        String[][] facts = getCountryFacts("Philippines");
        assertNotNull(facts, "Facts for Philippines should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Philippines"));
    }

    @Test
    void getqatarFacts() {
        String[][] facts = getCountryFacts("Qatar");
        assertNotNull(facts, "Facts for Qatar should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Qatar"));
    }

    @Test
    void getsaudiArabiaFacts() {
        String[][] facts = getCountryFacts("Saudi Arabia");
        assertNotNull(facts, "Facts for Saudi Arabia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Saudi Arabia"));
    }

    @Test
    void getsouthKoreaFacts() {
        String[][] facts = getCountryFacts("South Korea");
        assertNotNull(facts, "Facts for South Korea should not be null");
        assertTrue(Arrays.deepToString(facts).contains("South Korea"));
    }

    @Test
    void getsriLankaFacts() {
        String[][] facts = getCountryFacts("Sri Lanka");
        assertNotNull(facts, "Facts for Sri Lanka should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Sri Lanka"));
    }

    @Test
    void getthailandFacts() {
        String[][] facts = getCountryFacts("Thailand");
        assertNotNull(facts, "Facts for Thailand should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Thailand"));
    }

    @Test
    void getturkeyFacts() {
        String[][] facts = getCountryFacts("Turkey");
        assertNotNull(facts, "Facts for Turkey should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Turkey"));
    }

    @Test
    void getUAEFacts() {
        String[][] facts = getCountryFacts("UAE");
        assertNotNull(facts, "Facts for UAE should not be null");
        assertTrue(Arrays.deepToString(facts).contains("UAE"));
    }

    @Test
    void getvietnamFacts() {
        String[][] facts = getCountryFacts("Vietnam");
        assertNotNull(facts, "Facts for Vietnam should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Vietnam"));
    }
}