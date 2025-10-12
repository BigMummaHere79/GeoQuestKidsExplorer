package com.example.geoquestkidsexplorer.data;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class NorthAmericaFunFactsTest {

    @Test
    void getantiguaAndBarbudaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getantiguaAndBarbudaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Antigua"));
    }

    @Test
    void getbahamasFacts() {
        String [][] facts =  NorthAmericaFunFacts.getbahamasFacts();
        assertTrue(Arrays.deepToString(facts).contains("Bahamas"));
    }

    @Test
    void getbarbadosFacts() {
        String [][] facts =  NorthAmericaFunFacts.getbarbadosFacts();
        assertTrue(Arrays.deepToString(facts).contains("Barbados"));
    }

    @Test
    void getbelizeFacts() {
        String [][] facts =  NorthAmericaFunFacts.getbelizeFacts();
        assertTrue(Arrays.deepToString(facts).contains("Belize"));
    }

    @Test
    void getcanadaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getcanadaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Canada"));
    }

    @Test
    void getcostaRicaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getcostaRicaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Costa Rica"));
    }

    @Test
    void getcubaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getcubaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Cuba"));
    }

    @Test
    void getdominicaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getdominicaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Dominica"));
    }

    @Test
    void getdominicanRepublicFacts() {
        String [][] facts =  NorthAmericaFunFacts.getdominicanRepublicFacts();
        assertTrue(Arrays.deepToString(facts).contains("Dominican Republic"));
    }

    @Test
    void getelSalvadorFacts() {
        String [][] facts =  NorthAmericaFunFacts.getelSalvadorFacts();
        assertTrue(Arrays.deepToString(facts).contains("Salvador"));
    }

    @Test
    void getgrenadaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getgrenadaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Grenada"));
    }

    @Test
    void getguatemalaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getguatemalaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Guatemala"));
    }

    @Test
    void gethaitiFacts() {
        String [][] facts =  NorthAmericaFunFacts.gethaitiFacts();
        assertTrue(Arrays.deepToString(facts).contains("Haiti"));
    }

    @Test
    void gethondurasFacts() {
        String [][] facts =  NorthAmericaFunFacts.gethondurasFacts();
        assertTrue(Arrays.deepToString(facts).contains("Honduras"));
    }

    @Test
    void getjamaicaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getjamaicaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Jamaica"));
    }

    @Test
    void getmexicoFacts() {
        String [][] facts =  NorthAmericaFunFacts.getmexicoFacts();
        assertTrue(Arrays.deepToString(facts).contains("Mexico"));
    }

    @Test
    void getnicaraguaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getnicaraguaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Nicaragua"));
    }

    @Test
    void getpanamaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getpanamaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Panama"));
    }

    @Test
    void getsaintKittsAndNevisFacts() {
        String [][] facts =  NorthAmericaFunFacts.getsaintKittsAndNevisFacts();
        assertTrue(Arrays.deepToString(facts).contains("Saint Kitts and Nevis"));
    }

    @Test
    void getsaintLuciaFacts() {
        String [][] facts =  NorthAmericaFunFacts.getsaintLuciaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Saint Lucia"));
    }

    @Test
    void getsaintVincentAndTheGrenadinesFacts() {
        String [][] facts =  NorthAmericaFunFacts.getsaintVincentAndTheGrenadinesFacts();
        assertTrue(Arrays.deepToString(facts).contains("calypso festivals"));
    }

    @Test
    void gettrinidadAndTobagoFacts() {
        String [][] facts =  NorthAmericaFunFacts.gettrinidadAndTobagoFacts();
        assertTrue(Arrays.deepToString(facts).contains("Trinidad"));
    }

    @Test
    void getunitedStatesFacts() {
        String [][] facts =  NorthAmericaFunFacts.getunitedStatesFacts();
        assertTrue(Arrays.deepToString(facts).contains("USA"));
    }
}