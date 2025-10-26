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
 * Facade class for database operations, implementing the Singleton and Facade patterns.
 * Uses Dependency Injection for service dependencies to promote loose coupling.
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    private final UserService userService;
    private final QuizService quizService;
    private final FeedbackService feedbackService;

    /**
     * Private constructor with dependency injection.
     * @param userService The UserService instance.
     * @param quizService The QuizService instance.
     * @param feedbackService The FeedbackService instance.
     */
    private DatabaseManager(UserService userService, QuizService quizService, FeedbackService feedbackService) {
        this.userService = userService;
        this.quizService = quizService;
        this.feedbackService = feedbackService;
    }

    /**
     * Gets the singleton instance of DatabaseManager.
     * @return The singleton instance.
     */
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager(
                    DatabaseServiceFactory.createUserService(),
                    DatabaseServiceFactory.createQuizService(),
                    DatabaseServiceFactory.createFeedbackService()
            );
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
     * @return The database connection.
     * @throws SQLException If connection fails.
     */
    public static Connection getConnection() throws SQLException {
        return DatabaseUtils.getConnection();
    }

    /**
     * Inserts a new user into the database.
     * @param username Username.
     * @param email Email.
     * @param password Password.
     * @param avatar Avatar.
     * @param role Role.
     */
    public void insertUser(String username, String email, String password, String avatar, String role) {
        userService.insertUser(username, email, password, avatar, role);
    }

    /**
     * Checks if a user exists by username or email.
     * @param username Username.
     * @param email    Email.
     * @return True if user exists, false otherwise.
     */
    public boolean userExists(String username, String email) {
        return userService.userExists(username, email);
    }

    /**
     * Validates login credentials.
     * @param email    Email.
     * @param password Password.
     * @return True if valid, false otherwise.
     */
    public boolean validateLogin(String email, String password) {
        return userService.validateLogin(email, password);
    }

    /**
     * Retrieves a user profile by username.
     * @param username Username.
     * @return UserProfile or null if not found.
     */
    public UserProfile getUserProfileByUsername(String username) {
        return userService.getUserProfileByUsername(username);
    }

    /**
     * Retrieves user details (username, avatar) by username.
     * @param username Username.
     * @return Array of {username, avatar} or null if not found.
     */
    public String[] getUserDetails(String username) {
        return userService.getUserDetails(username);
    }

    /**
     * Gets username by email.
     * @param email Email.
     * @return Username or null if not found.
     */
    public String getUsernameByEmail(String email) {
        return userService.getUsernameByEmail(email);
    }

    /**
     * Gets avatar by email.
     * @param email Email.
     * @return Avatar or null if not found.
     */
    public String getAvatarByEmail(String email) {
        return userService.getAvatarByEmail(email);
    }

    /**
     * Gets a random country by continent.
     * @param continent Continent name.
     * @return CountryQuestion object.
     */
    public CountryQuestion getRandomCountryByContinent(String continent) {
        return quizService.getRandomCountryByContinent(continent);
    }

    /**
     * Gets a practice quiz question for a continent.
     * @param continent Continent name.
     * @return PracticeQuizQuestions object.
     */
    public PracticeQuizQuestions getPracticeQuizQuestion(String continent) {
        return quizService.getPracticeQuizQuestion(continent);
    }

    /**
     * Gets a test quiz question for a continent.
     * @param continent Continent name.
     * @return TestQuizQuestions object.
     */
    public TestQuizQuestions getTestQuizQuestion(String continent) {
        return quizService.getTestQuizQuestion(continent);
    }

    /**
     * Gets the image for a country.
     * @param countryName Country name.
     * @return Image object.
     */
    public Image getCountryImage(String countryName) {
        return quizService.getCountryImage(countryName);
    }

    /**
     * Gets a list of random countries by continent, excluding a specified country.
     * @param continent Continent name.
     * @param limit     Number of countries to return.
     * @param exclude   Country to exclude.
     * @return List of country names.
     */
    public List<String> getNRandomCountriesByContinent(String continent, int limit, String exclude) {
        return quizService.getNRandomCountriesByContinent(continent, limit, exclude);
    }

    /**
     * Gets all countries for a continent.
     * @param continent Continent name.
     * @return List of Country objects.
     * @throws SQLException If database query fails.
     */
    public List<Country> getCountriesByContinent(String continent) throws SQLException {
        return quizService.getCountriesByContinent(continent);
    }

    /**
     * Gets the continent for a given level.
     * @param level Level number.
     * @return Continent name.
     */
    public String getContinentByLevel(int level) {
        return quizService.getContinentByLevel(level);
    }

    /**
     * Gets the continent from the database directly instead of hardcoding.
     * @return Continent name.
     */
    public List<String> getAllContinentsInOrder() {
        return quizService.getAllContinentsInOrder();
    }

    /**
     * Normalizes a string for consistent comparison.
     * @param s String to normalize.
     * @return Normalized string.
     */
    public String normalize(String s) {
        return QuizService.normalize(s);
    }

    /**
     * Adds a feedback entry.
     * @param username Username.
     * @param rating   Rating value.
     * @param comment  Comment text.
     * @param parentId Parent feedback ID (nullable).
     * @return Feedback ID.
     * @throws SQLException If database query fails.
     */
    public int addFeedback(String username, double rating, String comment, Integer parentId) throws SQLException {
        return feedbackService.addFeedback(username, rating, comment, parentId);
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
    public boolean updateFeedback(int feedbackId, double rating, String comment, String username) throws SQLException {
        return feedbackService.updateFeedback(feedbackId, rating, comment, username);
    }

    /**
     * Deletes a feedback entry.
     * @param feedbackId Feedback ID.
     * @param username   Username.
     * @return True if successful, false otherwise.
     * @throws SQLException If database query fails.
     */
    public boolean deleteFeedback(int feedbackId, String username) throws SQLException {
        return feedbackService.deleteFeedback(feedbackId, username);
    }

    /**
     * Gets top-level feedback entries.
     * @return List of FeedbackRatings objects.
     * @throws SQLException If database query fails.
     */
    public List<FeedbackRatings> getTopLevelFeedbacks() throws SQLException {
        return feedbackService.getTopLevelFeedbacks();
    }

    /**
     * Gets replies for a parent feedback.
     * @param parentId Parent feedback ID.
     * @return List of FeedbackRatings objects.
     * @throws SQLException If database query fails.
     */
    public List<FeedbackRatings> getReplies(int parentId) throws SQLException {
        return feedbackService.getReplies(parentId);
    }

    /**
     * Saves a quiz result and updates user level if passed.
     * @param username        Username.
     * @param level           Current level.
     * @param scorePercentage Score percentage.
     * @return True if successful, false on error.
     */
    public boolean saveQuizResultAndUpdateLevel(String username, int level, double scorePercentage) {
        return userService.saveQuizResultAndUpdateLevel(username, level, scorePercentage);
    }

    /**
     * Fixes a user's level if null.
     * @param username Username.
     */
    public void fixUserLevel(String username) {
        userService.fixUserLevel(username);
    }
}