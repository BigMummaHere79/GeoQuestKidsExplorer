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
 * Facade class for database operations, following the Facade design pattern.
 * Uses singleton pattern for instance management and delegates to specialized services
 * for encapsulation and polymorphism.
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    private final UserService userService;
    private final QuizService quizService;
    private final FeedbackService feedbackService;

    /**
     * Private constructor to initialize services.
     */
    private DatabaseManager() {
        this.userService = new DatabaseUserService();
        this.quizService = new DatabaseQuizService();
        this.feedbackService = new DatabaseFeedbackService();
    }

    /**
     * Gets the singleton instance of the DatabaseManager.
     *
     * @return The singleton instance.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Creates the database schema and seeds initial data.
     */
    public static void createNewDatabase() {
        DatabaseSchemaManager.createDatabase();
    }

    /**
     * Establishes a connection to the SQLite database.
     *
     * @return The database connection.
     * @throws SQLException If connection fails.
     */
    public static Connection getConnection() throws SQLException {
        return DatabaseUtils.getConnection();
    }

    /**
     * Inserts a new user into the database.
     *
     * @param username Username.
     * @param email    Email.
     * @param password Password.
     * @param avatar   Avatar.
     * @param role     Role.
     */
    public static void insertUser(String username, String email, String password, String avatar, String role) {
        getInstance().userService.insertUser(username, email, password, avatar, role);
    }

    /**
     * Checks if a user exists by username or email.
     *
     * @param username Username.
     * @param email    Email.
     * @return True if user exists, false otherwise.
     */
    public static boolean userExists(String username, String email) {
        return getInstance().userService.userExists(username, email);
    }

    /**
     * Validates login credentials.
     *
     * @param email    Email.
     * @param password Password.
     * @return True if valid, false otherwise.
     */
    public static boolean validateLogin(String email, String password) {
        return getInstance().userService.validateLogin(email, password);
    }

    /**
     * Retrieves a user profile by username.
     *
     * @param username Username.
     * @return UserProfile or null if not found.
     */
    public static UserProfile getUserProfileByUsername(String username) {
        return getInstance().userService.getUserProfileByUsername(username);
    }

    /**
     * Retrieves user details (username, avatar) by username.
     *
     * @param username Username.
     * @return Array of {username, avatar} or null if not found.
     */
    public static String[] getUserDetails(String username) {
        return getInstance().userService.getUserDetails(username);
    }

    /**
     * Gets username by email.
     *
     * @param email Email.
     * @return Username or null if not found.
     */
    public static String getUsernameByEmail(String email) {
        return getInstance().userService.getUsernameByEmail(email);
    }

    /**
     * Gets avatar by email.
     *
     * @param email Email.
     * @return Avatar or null if not found.
     */
    public static String getAvatarByEmail(String email) {
        return getInstance().userService.getAvatarByEmail(email);
    }

    /**
     * Gets a random country by continent.
     *
     * @param continent Continent name.
     * @return CountryQuestion object.
     */
    public static CountryQuestion getRandomCountryByContinent(String continent) {
        return getInstance().quizService.getRandomCountryByContinent(continent);
    }

    /**
     * Gets a practice quiz question for a continent.
     *
     * @param continent Continent name.
     * @return PracticeQuizQuestions object.
     */
    public static PracticeQuizQuestions getPracticeQuizQuestion(String continent) {
        return getInstance().quizService.getPracticeQuizQuestion(continent);
    }

    /**
     * Gets a test quiz question for a continent.
     *
     * @param continent Continent name.
     * @return TestQuizQuestions object.
     */
    public static TestQuizQuestions getTestQuizQuestion(String continent) {
        return getInstance().quizService.getTestQuizQuestion(continent);
    }

    /**
     * Gets the image for a country.
     *
     * @param countryName Country name.
     * @return Image object.
     */
    public static Image getCountryImage(String countryName) {
        return getInstance().quizService.getCountryImage(countryName);
    }

    /**
     * Gets a list of random countries by continent, excluding a specified country.
     *
     * @param continent Continent name.
     * @param limit     Number of countries to return.
     * @param exclude   Country to exclude.
     * @return List of country names.
     */
    public static List<String> getNRandomCountriesByContinent(String continent, int limit, String exclude) {
        return getInstance().quizService.getNRandomCountriesByContinent(continent, limit, exclude);
    }

    /**
     * Gets all countries for a continent.
     *
     * @param continent Continent name.
     * @return List of Country objects.
     * @throws SQLException If database query fails.
     */
    public static List<Country> getCountriesByContinent(String continent) throws SQLException {
        return getInstance().quizService.getCountriesByContinent(continent);
    }

    /**
     * Gets the continent for a given level.
     *
     * @param level Level number.
     * @return Continent name.
     */
    public static String getContinentByLevel(int level) {
        return getInstance().quizService.getContinentByLevel(level);
    }

    /**
     * Normalizes a string for consistent comparison.
     *
     * @param s String to normalize.
     * @return Normalized string.
     */
    public static String normalize(String s) {
        return QuizService.normalize(s);
    }

    /**
     * Adds a feedback entry.
     *
     * @param username Username.
     * @param rating   Rating value.
     * @param comment  Comment text.
     * @param parentId Parent feedback ID (nullable).
     * @return Feedback ID.
     * @throws SQLException If database query fails.
     */
    public static int addFeedback(String username, double rating, String comment, Integer parentId) throws SQLException {
        return getInstance().feedbackService.addFeedback(username, rating, comment, parentId);
    }

    /**
     * Updates a feedback entry.
     *
     * @param feedbackId Feedback ID.
     * @param rating    New rating.
     * @param comment   New comment.
     * @param username  Username.
     * @return True if successful, false otherwise.
     * @throws SQLException If database query fails.
     */
    public static boolean updateFeedback(int feedbackId, double rating, String comment, String username) throws SQLException {
        return getInstance().feedbackService.updateFeedback(feedbackId, rating, comment, username);
    }

    /**
     * Deletes a feedback entry.
     *
     * @param feedbackId Feedback ID.
     * @param username   Username.
     * @return True if successful, false otherwise.
     * @throws SQLException If database query fails.
     */
    public static boolean deleteFeedback(int feedbackId, String username) throws SQLException {
        return getInstance().feedbackService.deleteFeedback(feedbackId, username);
    }

    /**
     * Gets top-level feedback entries.
     *
     * @return List of FeedbackRatings objects.
     * @throws SQLException If database query fails.
     */
    public static List<FeedbackRatings> getTopLevelFeedbacks() throws SQLException {
        return getInstance().feedbackService.getTopLevelFeedbacks();
    }

    /**
     * Gets replies for a parent feedback.
     *
     * @param parentId Parent feedback ID.
     * @return List of FeedbackRatings objects.
     * @throws SQLException If database query fails.
     */
    public static List<FeedbackRatings> getReplies(int parentId) throws SQLException {
        return getInstance().feedbackService.getReplies(parentId);
    }

    /**
     * Saves a quiz result and updates user level if passed.
     *
     * @param username        Username.
     * @param level           Current level.
     * @param scorePercentage Score percentage.
     * @return True if successful, false on error.
     */
    public static boolean saveQuizResultAndUpdateLevel(String username, int level, double scorePercentage) {
        return getInstance().userService.saveQuizResultAndUpdateLevel(username, level, scorePercentage);
    }

    /**
     * Fixes a user's level if null.
     *
     * @param username Username.
     */
    public static void fixUserLevel(String username) {
        getInstance().userService.fixUserLevel(username);
    }
}