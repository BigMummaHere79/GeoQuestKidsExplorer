package com.example.geoquestkidsexplorer.data;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class OceaniaFunFactsTest {

    @Test
    void getaustraliaFacts() {
        String [][] facts =  OceaniaFunFacts.getaustraliaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Australia"));
    }

    @Test
    void getfijiFacts() {
        String [][] facts =  OceaniaFunFacts.getfijiFacts();
        assertTrue(Arrays.deepToString(facts).contains("Fiji"));
    }

    @Test
    void getkiribatiFacts() {
        String [][] facts =  OceaniaFunFacts.getkiribatiFacts();
        assertTrue(Arrays.deepToString(facts).contains("Kiriba"));
    }

    @Test
    void getmarshallIslandsFacts() {
        String [][] facts =  OceaniaFunFacts.getmarshallIslandsFacts();
        assertTrue(Arrays.deepToString(facts).contains("Marshall"));
    }

    @Test
    void getmicronesiaFacts() {
        String [][] facts =  OceaniaFunFacts.getmicronesiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Micronesia"));
    }

    @Test
    void getnauruFacts() {
        String [][] facts =  OceaniaFunFacts.getnauruFacts();
        assertTrue(Arrays.deepToString(facts).contains("Nauru"));
    }

    @Test
    void getnewZealandFacts() {
        String [][] facts =  OceaniaFunFacts.getnewZealandFacts();
        assertTrue(Arrays.deepToString(facts).contains("New Zealand"));
    }

    @Test
    void getpalauFacts() {
        String [][] facts =  OceaniaFunFacts.getpalauFacts();
        assertTrue(Arrays.deepToString(facts).contains("Palau"));
    }

    @Test
    void getpapuaNewGuineaFacts() {
        String [][] facts =  OceaniaFunFacts.getpapuaNewGuineaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Papua New Guinea"));
    }

    @Test
    void getsamoaFacts() {
        String [][] facts =  OceaniaFunFacts.getsamoaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Samoa"));
    }

    @Test
    void getsolomonIslandsFacts() {
        String [][] facts =  OceaniaFunFacts.getsolomonIslandsFacts();
        assertTrue(Arrays.deepToString(facts).contains("Solomon Islands"));
    }

    @Test
    void gettongaFacts() {
        String [][] facts =  OceaniaFunFacts.gettongaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Tonga"));
    }

    @Test
    void gettuvaluFacts() {
        String [][] facts =  OceaniaFunFacts.gettuvaluFacts();
        assertTrue(Arrays.deepToString(facts).contains("Tuvalu"));
    }

    @Test
    void getvanuatuFacts() {
        String [][] facts =  OceaniaFunFacts.getvanuatuFacts();
        assertTrue(Arrays.deepToString(facts).contains("Vanuatu"));
    }
}