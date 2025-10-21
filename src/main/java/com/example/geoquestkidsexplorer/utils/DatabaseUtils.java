package com.example.geoquestkidsexplorer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for low-level database connection management.
 * Provides static methods for obtaining connections and enabling constraints.
 * This class encapsulates SQLite-specific details.
 */
public class DatabaseUtils {
    private static final String DATABASE_URL = "jdbc:sqlite:geoquest.db";

    /**
     * Establishes a connection to the SQLite database.
     * @return A Connection object to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
    }

    /**
     * Enables foreign key constraints on the given connection.
     * @param conn The database connection.
     * @throws SQLException If an error occurs while executing the PRAGMA statement.
     */
    public static void enableForeignKeys(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys=ON");
        }
    }
}