package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.CountryQuestion;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple database connection test
 * Provided in modules
 * **/

public class DatabaseTest {

    //Test Database Connection
    @Test
    public void testConnection() throws SQLException {
        Connection conn = DatabaseManager.getConnection();
        assertEquals(true, conn != null);
    }


    // Check If Oceania is stored in database
    @Test
    void testIfRealDatabaseHasOceania(){
        // gates to DatabaseManager + geoquest
        var real = new RealQuizDataSource();
        CountryQuestion continent = real.getRandomCountryByContinent("Oceania");
        assertNotNull(continent, "Expected Oceania to be in database");
    }
}