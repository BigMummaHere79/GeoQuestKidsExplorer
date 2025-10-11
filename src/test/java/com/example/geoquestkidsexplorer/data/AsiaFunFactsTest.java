package com.example.geoquestkidsexplorer.data;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class AsiaFunFactsTest {

    @Test
    void getafghanistanFacts() {
        String [][] facts =  AsiaFunFacts.getafghanistanFacts();
        assertTrue(Arrays.deepToString(facts).contains("Afghanistan"));
    }

    @Test
    void getbahrainFacts() {
        String [][] facts =  AsiaFunFacts.getbahrainFacts();
        assertTrue(Arrays.deepToString(facts).contains("Bahrain"));
    }

    @Test
    void getbangladeshFacts() {
        String [][] facts =  AsiaFunFacts.getbangladeshFacts();
        assertTrue(Arrays.deepToString(facts).contains("Bangladesh"));
    }

    @Test
    void getbruneiFacts() {
        String [][] facts =  AsiaFunFacts.getbruneiFacts();
        assertTrue(Arrays.deepToString(facts).contains("Brunei"));
    }

    @Test
    void getcambodiaFacts() {
        String [][] facts =  AsiaFunFacts.getcambodiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Cambodia"));
    }

    @Test
    void getchinaFacts() {
        String [][] facts =  AsiaFunFacts.getchinaFacts();
        assertTrue(Arrays.deepToString(facts).contains("China"));
    }

    @Test
    void getindiaFacts() {
        String [][] facts =  AsiaFunFacts.getindiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("India"));
    }

    @Test
    void getindonesiaFacts() {
        String [][] facts =  AsiaFunFacts.getindonesiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Indonesia"));
    }

    @Test
    void getisraelFacts() {
        String [][] facts =  AsiaFunFacts.getisraelFacts();
        assertTrue(Arrays.deepToString(facts).contains("Israel"));
    }

    @Test
    void getjapanFacts() {
        String [][] facts =  AsiaFunFacts.getjapanFacts();
        assertTrue(Arrays.deepToString(facts).contains("Japan"));
    }

    @Test
    void getlebanonFacts() {
        String [][] facts =  AsiaFunFacts.getlebanonFacts();
        assertTrue(Arrays.deepToString(facts).contains("Lebanon"));
    }

    @Test
    void getmalaysiaFacts() {
        String [][] facts =  AsiaFunFacts.getmalaysiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Malaysia"));
    }

    @Test
    void getmaldivesFacts() {
        String [][] facts =  AsiaFunFacts.getmaldivesFacts();
        assertTrue(Arrays.deepToString(facts).contains("Maldives"));
    }

    @Test
    void getnepalFacts() {
        String [][] facts =  AsiaFunFacts.getnepalFacts();
        assertTrue(Arrays.deepToString(facts).contains("Nepal"));
    }

    @Test
    void getphilippinesFacts() {
        String [][] facts =  AsiaFunFacts.getphilippinesFacts();
        assertTrue(Arrays.deepToString(facts).contains("Philippines"));
    }

    @Test
    void getqatarFacts() {
        String [][] facts =  AsiaFunFacts.getqatarFacts();
        assertTrue(Arrays.deepToString(facts).contains("Qatar"));
    }

    @Test
    void getsaudiArabiaFacts() {
        String [][] facts =  AsiaFunFacts.getsaudiArabiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Saudi Arabia"));
    }

    @Test
    void getsouthKoreaFacts() {
        String [][] facts =  AsiaFunFacts.getsouthKoreaFacts();
        assertTrue(Arrays.deepToString(facts).contains("South Korea"));
    }

    @Test
    void getsriLankaFacts() {
        String [][] facts =  AsiaFunFacts.getsriLankaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Sri Lanka"));
    }

    @Test
    void getthailandFacts() {
        String [][] facts =  AsiaFunFacts.getthailandFacts();
        assertTrue(Arrays.deepToString(facts).contains("Thailand"));
    }

    @Test
    void getturkeyFacts() {
        String [][] facts =  AsiaFunFacts.getturkeyFacts();
        assertTrue(Arrays.deepToString(facts).contains("Turkey"));
    }

    @Test
    void getUAEFacts() {
        String [][] facts =  AsiaFunFacts.getUAEFacts();
        assertTrue(Arrays.deepToString(facts).contains("UAE"));
    }

    @Test
    void getvietnamFacts() {
        String [][] facts =  AsiaFunFacts.getvietnamFacts();
        assertTrue(Arrays.deepToString(facts).contains("Vietnam"));
    }
}