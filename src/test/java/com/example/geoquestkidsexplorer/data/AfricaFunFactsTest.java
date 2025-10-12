package com.example.geoquestkidsexplorer.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class AfricaFunFactsTest {

    @Test
    void getalgeriaFacts() {
        String [][] facts =  AfricaFunFacts.getalgeriaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Algeria"));
    }


    @Test
    void getangolaFacts() {
        String [][] facts =  AfricaFunFacts.getangolaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Angola"));
    }

    @Test
    void getbotswanaFacts() {
        String [][] facts =  AfricaFunFacts.getbotswanaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Botswana"));
    }

    @Test
    void getcentralAfricanRepublicFacts() {
        String [][] facts =  AfricaFunFacts.getcentralAfricanRepublicFacts();
        assertTrue(Arrays.deepToString(facts).contains("Republic"));
    }

    @Test
    void getegyptFacts() {
        String [][] facts =  AfricaFunFacts.getegyptFacts();
        assertTrue(Arrays.deepToString(facts).contains("Egypt"));
    }

    @Test
    void geteritreaFacts() {
        String [][] facts =  AfricaFunFacts.geteritreaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Eritrea"));
    }

    @Test
    void getethiopiaFacts() {
        String [][] facts =  AfricaFunFacts.getethiopiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Ethiopia"));
    }

    @Test
    void getghanaFacts() {
        String [][] facts =  AfricaFunFacts.getghanaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Ghana"));
    }

    @Test
    void getkenyaFacts() {
        String [][] facts =  AfricaFunFacts.getkenyaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Kenya"));
    }

    @Test
    void getliberiaFacts() {
        String [][] facts =  AfricaFunFacts.getliberiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Liberia"));
    }

    @Test
    void getlibyaFacts() {
        String [][] facts =  AfricaFunFacts.getlibyaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Libya"));
    }

    @Test
    void getmadagascarFacts() {
        String [][] facts =  AfricaFunFacts.getmadagascarFacts();
        assertTrue(Arrays.deepToString(facts).contains("Madagascar"));
    }

    @Test
    void getmauritiusFacts() {
        String [][] facts =  AfricaFunFacts.getmauritiusFacts();
        assertTrue(Arrays.deepToString(facts).contains("Mauritius"));
    }

    @Test
    void getmoroccoFacts() {
        String [][] facts =  AfricaFunFacts.getmoroccoFacts();
        assertTrue(Arrays.deepToString(facts).contains("Morocco"));
    }

    @Test
    void getnigeriaFacts() {
        String [][] facts =  AfricaFunFacts.getnigeriaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Nigeria"));
    }

    @Test
    void getsomaliaFacts() {
        String [][] facts =  AfricaFunFacts.getsomaliaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Somalia"));
    }

    @Test
    void getsouthAfricaFacts() {
        String [][] facts =  AfricaFunFacts.getsouthAfricaFacts();
        assertTrue(Arrays.deepToString(facts).contains("South Africa"));
    }

    @Test
    void getsouthSudanFacts() {
        String [][] facts =  AfricaFunFacts.getsouthSudanFacts();
        assertTrue(Arrays.deepToString(facts).contains("South Sudan"));
    }

    @Test
    void gettanzaniaFacts() {
        String [][] facts =  AfricaFunFacts.gettanzaniaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Tanzania"));
    }

    @Test
    void gettunisiaFacts() {
        String [][] facts =  AfricaFunFacts.gettunisiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Tunisia"));
    }

    @Test
    void getugandaFacts() {
        String [][] facts =  AfricaFunFacts.getugandaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Uganda"));
    }

    @Test
    void getzambiaFacts() {
        String [][] facts =  AfricaFunFacts.getzambiaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Zambia"));
    }

    @Test
    void getzimbabweFacts() {
        String [][] facts =  AfricaFunFacts.getzimbabweFacts();
        assertTrue(Arrays.deepToString(facts).contains("Zimbabwe"));
    }
}