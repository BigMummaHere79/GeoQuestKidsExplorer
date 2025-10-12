package com.example.geoquestkidsexplorer.data;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class SouthAmericaFunFactsTest {

    @Test
    void getargentinaFacts() {
        String [][] facts = SouthAmericaFunFacts.getargentinaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Argentina"));
    }

    @Test
    void getboliviaFacts() {
        String [][] facts = SouthAmericaFunFacts.getboliviaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Bolivia"));
    }

    @Test
    void getbrazilFacts() {
        String [][] facts = SouthAmericaFunFacts.getbrazilFacts();
        assertTrue(Arrays.deepToString(facts).contains("Brazil"));
    }

    @Test
    void getchileFacts() {
        String [][] facts = SouthAmericaFunFacts.getchileFacts();
        assertTrue(Arrays.deepToString(facts).contains("Chile"));
    }

    @Test
    void getcolombiaFacts() {
        String [][] facts = SouthAmericaFunFacts.getcolombiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Colombia"));
    }

    @Test
    void getecuadorFacts() {
        String [][] facts = SouthAmericaFunFacts.getecuadorFacts();
        assertTrue(Arrays.deepToString(facts).contains("Ecuador"));
    }

    @Test
    void getguyanaFacts() {
        String [][] facts = SouthAmericaFunFacts.getguyanaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Guyana"));
    }

    @Test
    void getparaguayFacts() {
        String [][] facts = SouthAmericaFunFacts.getparaguayFacts();
        assertTrue(Arrays.deepToString(facts).contains("Paraguay"));
    }

    @Test
    void getperuFacts() {
        String [][] facts = SouthAmericaFunFacts.getperuFacts();
        assertTrue(Arrays.deepToString(facts).contains("Peru"));
    }

    @Test
    void getsurinameFacts() {
        String [][] facts = SouthAmericaFunFacts.getsurinameFacts();
        assertTrue(Arrays.deepToString(facts).contains("Suriname"));
    }

    @Test
    void geturuguayFacts() {
        String [][] facts = SouthAmericaFunFacts.geturuguayFacts();
        assertTrue(Arrays.deepToString(facts).contains("Uruguay"));
    }

    @Test
    void getvenezuelaFacts() {
        String [][] facts = SouthAmericaFunFacts.getvenezuelaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Venezuela"));
    }
}