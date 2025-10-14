package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.models.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.ByteArrayInputStream;
import java.sql.*;
import javafx.scene.image.Image;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:geoquest.db";

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return A Connection object to the database.
     * @throws SQLException If a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL);
        //Note: Added this for the sidebar navigation icons!
    }

    /**
     * Retrieves the avatar and explorer name for a given user ID.
     *
     * @param username The name of the user.
     * @return An array with [username, avatar], or null if not found.
     */
    public static String[] getUserDetails(String username) {
        String sql = "SELECT username, avatar FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new String[]{rs.getString("username"), rs.getString("avatar")};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ===========================
    // Init / Schema
    // ===========================
    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            if (conn != null) {
                enableForeignKeys(conn);

                createContinentsTable(conn);
                seedContinents(conn);

                createCountriesTable(conn);
                createUsersTable(conn);
                createResultsTable(conn);
                createFeedbackTable(conn);

                System.out.println("Database created/connected.");
            }
        } catch (SQLException e) {
            System.err.println("DB init error: " + e.getMessage());
        }
    }

    private static void enableForeignKeys(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys=ON");
        }
    }
    // Continents
    private static void createContinentsTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS continents (
                continent TEXT PRIMARY KEY,
                level     INTEGER NOT NULL UNIQUE CHECK(level BETWEEN 1 AND 7)
            );
            """;
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    private static void seedContinents(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) AS c FROM continents")) {
            if (rs.next() && rs.getInt("c") > 0) return; // already seeded
        }

        String sql = "INSERT INTO continents (continent, level) VALUES (?, ?)";
        String[] names = {"Antarctica","Oceania","South America","North America","Europe","Asia","Africa"};
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

    // Countries
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
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    // Users
    private static void createUsersTable(Connection conn) throws SQLException {
        // If table already exists, SQLite will keep the existing schema.
        // New setups will get UNIQUE(email).
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
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    private static void createFeedbackTable(Connection conn) throws SQLException {
        // --------- Feedbacks Table ---------
        String sql = """
                CREATE TABLE IF NOT EXISTS feedback (
                    feedback_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL,
                    rating REAL NOT NULL,
                    comment TEXT NOT NULL,
                    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
                    parent_id INTEGER,
                    FOREIGN KEY(username) REFERENCES users(username),
                    FOREIGN KEY(parent_id) REFERENCES feedbacks(feedback_id)
                );
            """;
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    // Results
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
        try (Statement st = conn.createStatement()) { st.execute(sql); }
    }

    // ===========================
    // Users API
    // ===========================

    /** Duplicate-safe insert used by Register. */
    public static void insertUser(String username, String email, String password, String avatar, String role) {
        if (userExists(username, email)) {
            throw new RuntimeException("Username or email already exists.");
        }

        String sql = "INSERT INTO users(username, email, password, avatar, level, role) VALUES (?,?,?,?,1,?)";
        try (Connection conn = getConnection()) {
            enableForeignKeys(conn);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.setString(4, avatar != null ? avatar : "🙂"); // Ensure non-null avatar
                ps.setString(5, role != null ? role : "user"); // Ensure non-null role
                int rows = ps.executeUpdate();
                System.out.println("insertUser: User " + username + " inserted with level 1, rows affected: " + rows);

                // Verify insertion
                try (PreparedStatement verifyPs = conn.prepareStatement("SELECT level FROM users WHERE username = ?")) {
                    verifyPs.setString(1, username);
                    try (ResultSet rs = verifyPs.executeQuery()) {
                        if (rs.next()) {
                            int level = rs.getInt("level");
                            if (rs.wasNull() || level != 1) {
                                System.err.println("insertUser: User " + username + " has unexpected level: " + (rs.wasNull() ? "NULL" : level));
                            } else {
                                System.out.println("insertUser: Verified user " + username + " has level: 1");
                            }
                        } else {
                            System.err.println("insertUser: User " + username + " not found after insertion");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("insertUser: Error inserting user " + username + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to insert user: " + e.getMessage());
        }
    }

    /** Returns true if a user with matching username OR email exists. */
    public static boolean userExists(String username, String email) {
        final String sql = "SELECT 1 FROM users WHERE username = ? OR email = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("userExists error: " + e.getMessage());
            return false;
        }
    }

    /** Simple credential check for Login. */
    public static boolean validateLogin(String email, String password) {
        final String sql = "SELECT 1 FROM users WHERE email = ? AND password = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, email);
            ps.setString(2, password); // ⚠️ Compare hashes in production
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Login validation error: " + e.getMessage());
            return false;
        }
    }

    /** Optional helper to load a profile by username. */
    public static UserProfile getUserProfileByUsername(String username) {
        final String sql = "SELECT username, email, avatar, level, role FROM users WHERE username = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Adjust to your actual UserProfile constructor/fields
                    return new UserProfile(
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("avatar"),
                            rs.getInt("level"),
                            rs.getString("role")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("getUserProfileByUsername error: " + e.getMessage());
        }
        return null;
    }

    public static Image getCountryImage(String countryName) {
        String sql = "SELECT country_picture FROM countries WHERE country = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, countryName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] imgBytes = rs.getBytes("country_picture");
                if (imgBytes != null) {
                    return new Image(new ByteArrayInputStream(imgBytes));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getUsernameByEmail(String email) {
        final String sql = "SELECT username FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:geoquest.db");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("username");
            }
        } catch (SQLException e) {
            System.err.println("getUsernameByEmail error: " + e.getMessage());
        }
        return null;
    }

    public static String getAvatarByEmail(String email) {
        final String sql = "SELECT avatar FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:geoquest.db");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("avatar");
            }
        } catch (SQLException e) {
            System.err.println("getAvatarByEmail error: " + e.getMessage());
        }
        return null;
    }
//For Quiz mode

    public static class CountryQuestion {
        public final String countryName;
        public final Image image;          // JavaFX image to show
        public final String hints;
        public CountryQuestion(String countryName, Image image, String hints) {
            this.countryName = countryName;
            this.image = image;
            this.hints = hints;
        }
    }

    /** Get a random country+image from a given continent. */
    public static CountryQuestion getRandomCountryByContinent(String continent) {
        final String sql = """
        SELECT country, country_picture, hints
        FROM countries
        WHERE continent = ?
        ORDER BY RANDOM()
        LIMIT 1
        """;
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            enableForeignKeys(conn);
            ps.setString(1, continent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String country = rs.getString("country");
                    String hints = rs.getString ("hints");
                    byte[] bytes = rs.getBytes("country_picture");
                    Image img = (bytes != null && bytes.length > 0)
                            ? new Image(new ByteArrayInputStream(bytes))
                            : null;
                    return new CountryQuestion(country, img, hints);
                }
            }
        } catch (SQLException e) {
            System.err.println("getRandomCountryByContinent error: " + e.getMessage());
        }
        return null;
    }

    /** Optional: normalize strings for answer checking (basic). */
    public static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

//---------------------------------------------------------------------------------------------------
//Note: Added for Practice Mode Quiz with country images. GLENDA

    // This method will generate a complete quiz question with an image and multiple-choice options.
    public static PracticeQuizQuestions getPracticeQuizQuestion(String continent) {
        //Get the correct country and its image for the question
        CountryQuestion correctQuestion = getRandomCountryByContinent(continent);

        if (correctQuestion == null) {
            return null; // No countries found
        }

        String correctAnswer = correctQuestion.countryName;
        Image countryImage = correctQuestion.image;
        String hints = correctQuestion.hints;

        //Get 3 random incorrect country names to use as distractors
        List<String> choices = getNRandomCountriesByContinent(continent, 3, correctAnswer);

        //Add the correct answer to the list of choices
        choices.add(correctAnswer);
        Collections.shuffle(choices); // Randomize the order of all 4 choices

        //(Optional) Create a simple question text and fun fact.
        // You can hard-code a generic question or use a more sophisticated method.
        String questionText = "Which country is this?";
        String funFact = "This is " + correctAnswer + ". " + hints;

        return new PracticeQuizQuestions(questionText, choices, correctAnswer, funFact, countryImage);
    }

    /** Gets a list of N random country names from a given continent.
     * Excludes the given 'exclude' country name if provided.
     */
    public static List<String> getNRandomCountriesByContinent(String continent, int limit, String exclude) {
        List<String> countries = new ArrayList<>();
        // The `AND country != ?` part handles the exclusion of the correct answer.
        String sql = "SELECT country FROM countries WHERE continent = ? AND country != ? ORDER BY RANDOM() LIMIT ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, continent);
            ps.setString(2, exclude);
            ps.setInt(3, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    countries.add(rs.getString("country"));
                }
            }
        } catch (SQLException e) {
            System.err.println("getNRandomCountriesByContinent error: " + e.getMessage());
        }
        return countries; // Returns the list, even if it's empty
    }

    //For TestMode--------------------------------------------------------------------------------------------------------------
    // This method will generate a complete quiz question with an image.
    public static TestQuizQuestions getTestQuizQuestion(String continent) {
        //Get the correct country and its image for the question
        CountryQuestion correctQuestion = getRandomCountryByContinent(continent);

        if (correctQuestion == null) {
            return null; // No countries found
        }

        //Set up
        String questionText = "Which country is this?";
        String correctAnswer = correctQuestion.countryName;
        Image countryImage = correctQuestion.image;

        // Populate choice list for ComboBox
        List<String> choices = new ArrayList<>();
        //Add correct answer
        choices.add(correctAnswer);
        //get three incorrect answers
        List<String> incorrectAnswers = getNRandomCountriesByContinent(continent, 3, correctAnswer);
        //Add incorrect answers to list
        choices.addAll(incorrectAnswers);
        //Shuffle list
        Collections.shuffle(choices);

        // Provide the score value for the test question
        int scoreValue = 10; // Or whatever value you want to assign

        // Return a new TestQuizQuestions object with all required arguments
        return new TestQuizQuestions(questionText, choices, correctAnswer, scoreValue, countryImage);
    }

    //---------------------------------------------------------------------------------------------------------------
    // NOTE: Adding this for UserProgress method and logic

    /**
     * Saves a test mode quiz result and updates the user's level if they pass with 80% or higher.
     * @param username The user's username.
     * @param level The continent level (1 to 7).
     * @param scorePercentage The quiz score (percentage, e.g., 85.0 for 85%).
     * @return true if the continent was unlocked, false otherwise.
     */
    public static boolean saveQuizResultAndUpdateLevel(String username, int level, double scorePercentage) {
        boolean isPassing = scorePercentage >= 80.0;
        String status = isPassing ? "Pass" : "Fail";

        // Insert result into results table
        String resultSql = "INSERT INTO results (username, level, grades, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(resultSql)) {
            ps.setString(1, username);
            ps.setInt(2, level);
            ps.setDouble(3, scorePercentage);
            ps.setString(4, status);
            int rows = ps.executeUpdate();
            System.out.println("saveQuizResultAndUpdateLevel: Inserted result for user " + username + ", level "
                    + level + ", score " + scorePercentage + ", status " + status + ", rows affected: " + rows);
        } catch (SQLException e) {
            System.err.println("saveQuizResultAndUpdateLevel: Error inserting result for user " + username + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        // Update user level if passing
        if (isPassing) {
            String updateSql = "UPDATE users SET level = ? WHERE username = ?";
            try (Connection conn = getConnection();
                 PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, level + 1); // Move to next level
                ps.setString(2, username);
                int rows = ps.executeUpdate();
                System.out.println("saveQuizResultAndUpdateLevel: Updated level to " + (level + 1) + " for user "
                        + username + ", rows affected: " + rows);
                return rows > 0;
            } catch (SQLException e) {
                System.err.println("saveQuizResultAndUpdateLevel: Error updating level for user " + username + ": "
                        + e.getMessage());
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Helper method to get continent name by level.
     * @param level The continent level (1 to 7).
     * @return The continent name or null if invalid.
     */
    public static String getContinentByLevel(int level) {
        String sql = "SELECT continent FROM continents WHERE level = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, level);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("continent");
                }
            }
        } catch (SQLException e) {
            System.err.println("getContinentByLevel error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
//---------------------------------------------------------------------------------------------------------------
    //NOTE: Adding for CRUD functionality. Glenda
    /**
     * Adds a new feedback or reply to the database.
     *
     * @param username The name of the user leaving the feedback.
     * @param rating The rating (e.g., 4.5).
     * @param comment The comment text.
     * @param parentId The parent feedback ID if this is a reply (null for top-level).
     * @return The generated feedback ID, or -1 on failure.
     * @throws SQLException If a database error occurs.
     */
    public static int addFeedback(String username, double rating, String comment, Integer parentId) throws SQLException {
        String sql = "INSERT INTO feedback (username, rating, comment, parent_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            //pstmt.setDouble(2, rating);
            pstmt.setDouble(2, Double.parseDouble(String.format("%.2f", rating))); // Round to 2 decimal places
            pstmt.setString(3, comment);
            if (parentId != null) {
                pstmt.setInt(4, parentId);
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * Updates an existing feedback (only if owned by the user).
     *
     * @param feedbackId The ID of the feedback to update.
     * @param rating The new rating.
     * @param comment The new comment.
     * @param username The name of the user (for ownership check).
     * @return true if updated successfully, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public static boolean updateFeedback(int feedbackId, double rating, String comment, String username) throws SQLException {
        String sql = "UPDATE feedback SET rating = ?, comment = ? WHERE feedback_id = ? AND username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //pstmt.setDouble(1, rating);
            pstmt.setDouble(1, Double.parseDouble(String.format("%.2f", rating))); // Round to 2 decimal places
            pstmt.setString(2, comment);
            pstmt.setInt(3, feedbackId);
            pstmt.setString(4, username);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    /**
     * Deletes a feedback (only if owned by the user). Replies are not deleted (they become top-level if needed).
     *
     * @param feedbackId The ID of the feedback to delete.
     * @param username The name of the user (for ownership check).
     * @return true if deleted successfully, false otherwise.
     * @throws SQLException If a database error occurs.
     */
    public static boolean deleteFeedback(int feedbackId, String username) throws SQLException {
        String sql = "DELETE FROM feedback WHERE feedback_id = ? AND username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, feedbackId);
            pstmt.setString(2, username);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    /**
     * Retrieves all top-level feedbacks (no parent).
     *
     * @return List of top-level Feedback objects.
     * @throws SQLException If a database error occurs.
     */
    public static List<FeedbackRatings> getTopLevelFeedbacks() throws SQLException {
        List<FeedbackRatings> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, u.username, u.avatar " +
                "FROM feedback f " +
                "JOIN users u ON f.username = u.username " +
                "WHERE f.parent_id IS NULL " +
                "ORDER BY f.timestamp DESC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                feedbacks.add(extractFeedback(rs));
            }
        }
        return feedbacks;
    }

    /**
     * Retrieves replies for a specific parent feedback.
     *
     * @param parentId The parent feedback ID.
     * @return List of reply Feedback objects.
     * @throws SQLException If a database error occurs.
     */
    public static List<FeedbackRatings> getReplies(int parentId) throws SQLException {
        List<FeedbackRatings> replies = new ArrayList<>();
        String sql = "SELECT f.*, u.username, u.avatar " +
                "FROM feedback f " +
                "JOIN users u ON f.username = u.username " +
                "WHERE f.parent_id = ? " +
                "ORDER BY f.timestamp ASC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, parentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    replies.add(extractFeedback(rs));
                }
            }
        }
        return replies;
    }

    private static FeedbackRatings extractFeedback(ResultSet rs) throws SQLException {
        FeedbackRatings fb = new FeedbackRatings();
        fb.setFeedbackId(rs.getInt("feedback_id"));
        fb.setUsername(rs.getString("username"));
        fb.setRating(rs.getDouble("rating"));
        fb.setComment(rs.getString("comment"));
        fb.setTimestamp(rs.getString("timestamp"));
        fb.setParentId(rs.wasNull() ? null : rs.getInt("parent_id"));  // Handle null parent_id
        //fb.setExplorerName(rs.getString("explorer_name"));
        fb.setAvatar(rs.getString("avatar"));
        return fb;
    }

    //--------------------------------------------------------------------------------------------------------------//
    //NOTE: Added for fetching data's from the database. Glenda.
    public static List<Country> getCountriesByContinent(String continent) throws SQLException {
        List<Country> countries = new ArrayList<>();
        String sql = "SELECT country, continent FROM countries WHERE continent = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, continent);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("country");
                    String continentName = rs.getString("continent");
                    String flagFileName = name.replaceAll(" ", "") + ".png"; // Derive flag filename
                    String funFactsJson = rs.getString("funFacts");
                    String[][] funFacts = parseFunFacts(funFactsJson);
                    countries.add(new Country(name, continentName, flagFileName, funFacts));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching countries for " + continent + ": " + e.getMessage());
            throw e;
        }
        return countries;
    }

    private static String[][] parseFunFacts(String funFactsStr) {
        // Example: Parse fun_facts as JSON or delimited string
        // For simplicity, assume fun_facts is a JSON array of arrays [[text, icon], ...]
        // Implement parsing logic here or fallback to empty array
        return new String[0][];
    }
}