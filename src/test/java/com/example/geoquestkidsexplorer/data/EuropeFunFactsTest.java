package com.example.geoquestkidsexplorer.data;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class EuropeFunFactsTest {

    @Test
    void getAustriaFacts() {
        String [][] facts =  EuropeFunFacts.getAustriaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Austria"));
    }

    @Test
    void getBelgiumFacts() {
        String [][] facts =  EuropeFunFacts.getBelgiumFacts();
        assertTrue(Arrays.deepToString(facts).contains("Belgium"));

    }

    @Test
    void getBulgariaFacts() {
        String [][] facts =  EuropeFunFacts.getBulgariaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Bulgaria"));
    }

    @Test
    void getCroatiaFacts() {
        String [][] facts =  EuropeFunFacts.getCroatiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Croatia"));
    }

    @Test
    void getDenmarkFacts() {
        String [][] facts =  EuropeFunFacts.getDenmarkFacts();
        assertTrue(Arrays.deepToString(facts).contains("Denmark"));
    }

    @Test
    void getFinlandFacts() {
        String [][] facts =  EuropeFunFacts.getFinlandFacts();
        assertTrue(Arrays.deepToString(facts).contains("Finland"));
    }

    @Test
    void getFranceFacts() {
        String [][] facts =  EuropeFunFacts.getFranceFacts();
        assertTrue(Arrays.deepToString(facts).contains("France"));
    }

    @Test
    void getGermanyFacts() {
        String [][] facts =  EuropeFunFacts.getGermanyFacts();
        assertTrue(Arrays.deepToString(facts).contains("Germany"));
    }

    @Test
    void getGreeceFacts() {
        String [][] facts =  EuropeFunFacts.getGreeceFacts();
        assertTrue(Arrays.deepToString(facts).contains("Greece"));
    }

    @Test
    void getIrelandFacts() {
        String [][] facts =  EuropeFunFacts.getIrelandFacts();
        assertTrue(Arrays.deepToString(facts).contains("Ireland"));
    }

    @Test
    void getItalyFacts() {
        String [][] facts =  EuropeFunFacts.getItalyFacts();
        assertTrue(Arrays.deepToString(facts).contains("Italy"));
    }

    @Test
    void getLuxembourgFacts() {
        String [][] facts =  EuropeFunFacts.getLuxembourgFacts();
        assertTrue(Arrays.deepToString(facts).contains("Luxembourg"));
    }

    @Test
    void getMaltaFacts() {
        String [][] facts =  EuropeFunFacts.getMaltaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Malta"));
    }

    @Test
    void getNetherlandsFacts() {
        String [][] facts =  EuropeFunFacts.getNetherlandsFacts();
        assertTrue(Arrays.deepToString(facts).contains("Netherlands"));
    }

    @Test
    void getNorwayFacts() {
        String [][] facts =  EuropeFunFacts.getNorwayFacts();
        assertTrue(Arrays.deepToString(facts).contains("Norway"));
    }

    @Test
    void getPolandFacts() {
        String [][] facts =  EuropeFunFacts.getPolandFacts();
        assertTrue(Arrays.deepToString(facts).contains("Poland"));
    }

    @Test
    void getPortugalFacts() {
        String [][] facts =  EuropeFunFacts.getPortugalFacts();
        assertTrue(Arrays.deepToString(facts).contains("Portugal"));
    }

    @Test
    void getRomaniaFacts() {
        String [][] facts =  EuropeFunFacts.getRomaniaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Romania"));
    }

    @Test
    void getSerbiaFacts() {
        String [][] facts =  EuropeFunFacts.getSerbiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Serbia"));
    }

    @Test
    void getSpainFacts() {
        String [][] facts =  EuropeFunFacts.getSpainFacts();
        assertTrue(Arrays.deepToString(facts).contains("Spain"));
    }

    @Test
    void getSwedenFacts() {
        String [][] facts =  EuropeFunFacts.getSwedenFacts();
        assertTrue(Arrays.deepToString(facts).contains("Sweden"));
    }

    @Test
    void getSwitzerlandFacts() {
        String [][] facts =  EuropeFunFacts.getSwitzerlandFacts();
        assertTrue(Arrays.deepToString(facts).contains("Switzerland"));
    }

    @Test
    void getUnitedKingdomFacts() {
        String [][] facts =  EuropeFunFacts.getUnitedKingdomFacts();
        assertTrue(Arrays.deepToString(facts).contains("United Kingdom"));
    }
}