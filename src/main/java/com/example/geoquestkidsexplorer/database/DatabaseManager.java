package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.*;
import com.example.geoquestkidsexplorer.repositories.FeedbackService;
import com.example.geoquestkidsexplorer.repositories.QuizService;
import com.example.geoquestkidsexplorer.repositories.UserService;
import com.example.geoquestkidsexplorer.utils.DatabaseUtils;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Facade class for database operations.
 * Uses singleton pattern for instance management and delegates to specialized services
 * for OOP separation (inheritance via abstract base, polymorphism via interfaces).
 * Public static methods provide the original API; internal logic refactored to services.
 * This encapsulates the complexity of multiple managers.
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    private final UserService userService;
    private final QuizService quizService;
    private final FeedbackService feedbackService;

    private DatabaseManager() {
        this.userService = new DatabaseUserService();
        this.quizService = new DatabaseQuizService();
        this.feedbackService = new DatabaseFeedbackService();
    }

    /**
     * Gets the singleton instance.
     * @return The DatabaseManager instance.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Creates the database schema and seeds data.
     * Delegates to SchemaManager.
     */
    public static void createNewDatabase() {
        DatabaseSchemaManager.createDatabase();
    }

    /**
     * Establishes a connection to the SQLite database.
     * Delegates to DatabaseUtils.
     * @return Connection.
     * @throws SQLException On error.
     */
    public static Connection getConnection() throws SQLException {
        return DatabaseUtils.getConnection();
    }

    // User delegates
    public static void insertUser(String username, String email, String password, String avatar, String role) {
        getInstance().userService.insertUser(username, email, password, avatar, role);
    }

    public static boolean userExists(String username, String email) {
        return getInstance().userService.userExists(username, email);
    }

    public static boolean validateLogin(String email, String password) {
        return getInstance().userService.validateLogin(email, password);
    }

    public static UserProfile getUserProfileByUsername(String username) {
        return getInstance().userService.getUserProfileByUsername(username);
    }

    public static String[] getUserDetails(String username) {
        return getInstance().userService.getUserDetails(username);
    }

    public static String getUsernameByEmail(String email) {
        return getInstance().userService.getUsernameByEmail(email);
    }

    public static String getAvatarByEmail(String email) {
        return getInstance().userService.getAvatarByEmail(email);
    }

    // Quiz delegates
    public static CountryQuestion getRandomCountryByContinent(String continent) {
        return getInstance().quizService.getRandomCountryByContinent(continent);
    }

    public static PracticeQuizQuestions getPracticeQuizQuestion(String continent) {
        return getInstance().quizService.getPracticeQuizQuestion(continent);
    }

    public static TestQuizQuestions getTestQuizQuestion(String continent) {
        return getInstance().quizService.getTestQuizQuestion(continent);
    }

    public static Image getCountryImage(String countryName) {
        return getInstance().quizService.getCountryImage(countryName);
    }

    public static List<String> getNRandomCountriesByContinent(String continent, int limit, String exclude) {
        return getInstance().quizService.getNRandomCountriesByContinent(continent, limit, exclude);
    }

    public static List<Country> getCountriesByContinent(String continent) throws SQLException {
        return getInstance().quizService.getCountriesByContinent(continent);
    }

    public static String getContinentByLevel(int level) {
        return getInstance().quizService.getContinentByLevel(level);
    }

    public static String normalize(String s) {
        return QuizService.normalize(s);
    }

    // Feedback delegates
    public static int addFeedback(String username, double rating, String comment, Integer parentId) throws SQLException {
        return getInstance().feedbackService.addFeedback(username, rating, comment, parentId);
    }

    public static boolean updateFeedback(int feedbackId, double rating, String comment, String username) throws SQLException {
        return getInstance().feedbackService.updateFeedback(feedbackId, rating, comment, username);
    }

    public static boolean deleteFeedback(int feedbackId, String username) throws SQLException {
        return getInstance().feedbackService.deleteFeedback(feedbackId, username);
    }

    public static List<FeedbackRatings> getTopLevelFeedbacks() throws SQLException {
        return getInstance().feedbackService.getTopLevelFeedbacks();
    }

    public static List<FeedbackRatings> getReplies(int parentId) throws SQLException {
        return getInstance().feedbackService.getReplies(parentId);
    }

    // Progress (user-related)
    public static boolean saveQuizResultAndUpdateLevel(String username, int level, double scorePercentage) {
        return getInstance().userService.saveQuizResultAndUpdateLevel(username, level, scorePercentage);
    }

    // Internal: fixUserLevel (called from controller via static? Add static delegate if needed)
    public static void fixUserLevel(String username) {
        getInstance().userService.fixUserLevel(username);
    }
}