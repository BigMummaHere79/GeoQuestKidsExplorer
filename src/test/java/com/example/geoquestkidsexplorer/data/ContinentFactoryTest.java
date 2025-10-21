package com.example.geoquestkidsexplorer.data;

import com.example.geoquestkidsexplorer.models.Continents;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContinentFactoryTest {

    @Test
    void testGetAllContinent() {
        List<String> continents = ContinentFactory.getAllContinentNames();
        assertNotNull(continents,"Name list should not be null");

    }

    @Test
    void testGetContinentNames() {
        assertNotNull(ContinentFactory.getContinent("Oceania"));
    }
}