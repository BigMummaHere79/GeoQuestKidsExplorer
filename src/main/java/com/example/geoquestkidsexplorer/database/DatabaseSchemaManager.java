package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.utils.DatabaseUtils;

import java.sql.*;

/**
 * Manages database schema creation, table setup, and initial data seeding.
 * This class encapsulates all DDL operations and seeding logic to separate concerns from business services.
 * All methods are static for simplicity in initialization.
 */
public class DatabaseSchemaManager {

    /**
     * Creates the database schema and seeds initial data (continents).
     * Note: Countries table is created but not seeded here—implement seedCountries if needed.
     * Also, add ALTER TABLE for missing columns like fun_facts if required by queries.
     */
    public static void createDatabase() {
        try (Connection conn = DatabaseUtils.getConnection()) {
            if (conn != null) {
                DatabaseUtils.enableForeignKeys(conn);

                createContinentsTable(conn);
                seedContinents(conn);

                createCountriesTable(conn);
                createUsersTable(conn);
                createResultsTable(conn);
                createFeedbackTable(conn);  // Fixed self-ref FK to 'feedback'

                System.out.println("Database created/connected.");
            }
        } catch (SQLException e) {
            System.err.println("DB init error: " + e.getMessage());
        }
    }

    private static void createContinentsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS continents (
                continent TEXT PRIMARY KEY,
                level     INTEGER NOT NULL UNIQUE CHECK(level BETWEEN 1 AND 7)
            );
            """;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    private static void seedContinents(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) AS c FROM continents")) {
            if (rs.next() && rs.getInt("c") > 0) return; // already seeded
        }

        String sql = "INSERT INTO continents (continent, level) VALUES (?, ?)";
        String[] names = {"Antarctica", "Oceania", "South America", "North America", "Europe", "Asia", "Africa"};
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (int i = 0; i < names.length; i++) {
                ps.setString(1, names[i]);
                ps.setInt(2, i + 1); // levels 1..7
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
        }
    }

    private static void createCountriesTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS countries (
                country         TEXT PRIMARY KEY,
                continent       TEXT NOT NULL,
                country_picture BLOB,
                hints           TEXT,
                FOREIGN KEY (continent) REFERENCES continents(continent)
                    ON UPDATE CASCADE ON DELETE RESTRICT
            );
            """;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    private static void createUsersTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                username TEXT PRIMARY KEY,
                avatar   TEXT NOT NULL,
                email    TEXT UNIQUE,
                password TEXT NOT NULL,
                level    INTEGER,
                role     TEXT,
                FOREIGN KEY (level) REFERENCES continents(level)
                    ON UPDATE CASCADE ON DELETE SET NULL
            );
            """;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    private static void createResultsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS results (
                id_result INTEGER PRIMARY KEY AUTOINCREMENT,
                username  TEXT NOT NULL,
                level     INTEGER NOT NULL,
                grades    REAL,
                status    TEXT CHECK (status IN ('Pass','Fail')),
                FOREIGN KEY (username) REFERENCES users(username)
                    ON UPDATE CASCADE ON DELETE CASCADE,
                FOREIGN KEY (level) REFERENCES continents(level)
                    ON UPDATE CASCADE ON DELETE RESTRICT
            );
            """;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    private static void createFeedbackTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS feedback (
                feedback_id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL,
                rating REAL NOT NULL,
                comment TEXT NOT NULL,
                timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                parent_id INTEGER,
                FOREIGN KEY(username) REFERENCES users(username),
                FOREIGN KEY(parent_id) REFERENCES feedback(feedback_id)  -- Fixed: was 'feedbacks'
            );
            """;
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }
}