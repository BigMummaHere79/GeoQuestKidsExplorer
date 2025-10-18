package com.example.geoquestkidsexplorer.database;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {

    @Test
    void getInstance() {
        assertNotNull(DatabaseManager.getInstance(), "Database should be available");
    }
}