package com.example.geoquestkidsexplorer.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract base class for database services.
 * Provides a common method for acquiring connections with foreign keys enabled,
 * encapsulating repetitive connection logic and promoting inheritance.
 * Subclasses can extend this for shared behavior while implementing specific interfaces.
 */
public abstract class DatabaseService {
    /**
     * Acquires a database connection and enables foreign key constraints.
     * @return A configured Connection object.
     * @throws SQLException If a connection error occurs.
     */
    protected Connection getConnection() throws SQLException {
        Connection conn = DatabaseUtils.getConnection();
        DatabaseUtils.enableForeignKeys(conn);
        return conn;
    }
}