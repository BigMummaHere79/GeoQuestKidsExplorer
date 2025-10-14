package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Country;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AfricaFunFactsTest {

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
    void getalgeriaFacts() {
        String[][] facts = getCountryFacts("Algeria");
        assertNotNull(facts, "Facts for Algeria should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Algeria"));
    }

    @Test
    void getangolaFacts() {
        String[][] facts = getCountryFacts("Angola");
        assertNotNull(facts, "Facts for Angola should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Angola"));
    }

    @Test
    void getbotswanaFacts() {
        String[][] facts = getCountryFacts("Botswana");
        assertNotNull(facts, "Facts for Botswana should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Botswana"));
    }

    @Test
    void getcentralAfricanRepublicFacts() {
        String[][] facts = getCountryFacts("Central African Republic");
        assertNotNull(facts, "Facts for Central African Republic should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Republic"));
    }

    @Test
    void getegyptFacts() {
        String[][] facts = getCountryFacts("Egypt");
        assertNotNull(facts, "Facts for Egypt should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Egypt"));
    }

    @Test
    void geteritreaFacts() {
        String[][] facts = getCountryFacts("Eritrea");
        assertNotNull(facts, "Facts for Eritrea should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Eritrea"));
    }

    @Test
    void getethiopiaFacts() {
        String[][] facts = getCountryFacts("Ethiopia");
        assertNotNull(facts, "Facts for Ethiopia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Ethiopia"));
    }

    @Test
    void getghanaFacts() {
        String[][] facts = getCountryFacts("Ghana");
        assertNotNull(facts, "Facts for Ghana should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Ghana"));
    }

    @Test
    void getkenyaFacts() {
        String[][] facts = getCountryFacts("Kenya");
        assertNotNull(facts, "Facts for Kenya should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Kenya"));
    }

    @Test
    void getliberiaFacts() {
        String[][] facts = getCountryFacts("Liberia");
        assertNotNull(facts, "Facts for Liberia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Liberia"));
    }

    @Test
    void getlibyaFacts() {
        String[][] facts = getCountryFacts("Libya");
        assertNotNull(facts, "Facts for Libya should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Libya"));
    }

    @Test
    void getmadagascarFacts() {
        String[][] facts = getCountryFacts("Madagascar");
        assertNotNull(facts, "Facts for Madagascar should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Madagascar"));
    }

    @Test
    void getmauritiusFacts() {
        String[][] facts = getCountryFacts("Mauritius");
        assertNotNull(facts, "Facts for Mauritius should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Mauritius"));
    }

    @Test
    void getmoroccoFacts() {
        String[][] facts = getCountryFacts("Morocco");
        assertNotNull(facts, "Facts for Morocco should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Morocco"));
    }

    @Test
    void getnigeriaFacts() {
        String[][] facts = getCountryFacts("Nigeria");
        assertNotNull(facts, "Facts for Nigeria should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Nigeria"));
    }

    @Test
    void getsomaliaFacts() {
        String[][] facts = getCountryFacts("Somalia");
        assertNotNull(facts, "Facts for Somalia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Somalia"));
    }

    @Test
    void getsouthAfricaFacts() {
        String[][] facts = getCountryFacts("South Africa");
        assertNotNull(facts, "Facts for South Africa should not be null");
        assertTrue(Arrays.deepToString(facts).contains("South Africa"));
    }

    @Test
    void getsouthSudanFacts() {
        String[][] facts = getCountryFacts("South Sudan");
        assertNotNull(facts, "Facts for South Sudan should not be null");
        assertTrue(Arrays.deepToString(facts).contains("South Sudan"));
    }

    @Test
    void gettanzaniaFacts() {
        String[][] facts = getCountryFacts("Tanzania");
        assertNotNull(facts, "Facts for Tanzania should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Tanzania"));
    }

    @Test
    void gettunisiaFacts() {
        String[][] facts = getCountryFacts("Tunisia");
        assertNotNull(facts, "Facts for Tunisia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Tunisia"));
    }

    @Test
    void getugandaFacts() {
        String[][] facts = getCountryFacts("Uganda");
        assertNotNull(facts, "Facts for Uganda should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Uganda"));
    }

    @Test
    void getzambiaFacts() {
        String[][] facts = getCountryFacts("Zambia");
        assertNotNull(facts, "Facts for Zambia should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Zambia"));
    }

    @Test
    void getzimbabweFacts() {
        String[][] facts = getCountryFacts("Zimbabwe");
        assertNotNull(facts, "Facts for Zimbabwe should not be null");
        assertTrue(Arrays.deepToString(facts).contains("Zimbabwe"));
    }
}