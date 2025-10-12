package com.example.geoquestkidsexplorer.data;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class AntarcticaFunFactsTest {

    @Test
    void getantarcticaFacts() {
        String [][] facts =  AntarcticaFunFacts.getantarcticaFacts();
        assertTrue(Arrays.deepToString(facts).contains("Antarctica"));
    }
}