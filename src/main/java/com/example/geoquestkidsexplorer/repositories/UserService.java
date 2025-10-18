package com.example.geoquestkidsexplorer.repositories;

import com.example.geoquestkidsexplorer.models.UserProfile;

/**
 * Interface defining operations for user management.
 * Abstracts user-related database interactions for polymorphism (e.g., mock implementations for testing).
 */
public interface UserService {
    /**
     * Inserts a new user into the database.
     * @param username The user's username.
     * @param email The user's email.
     * @param password The user's password.
     * @param avatar The user's avatar.
     * @param role The user's role.
     * @throws RuntimeException If username or email already exists or insertion fails.
     */
    void insertUser(String username, String email, String password, String avatar, String role);

    /**
     * Checks if a user with the given username or email exists.
     * @param username The username to check.
     * @param email The email to check.
     * @return true if exists, false otherwise.
     */
    boolean userExists(String username, String email);

    /**
     * Validates login credentials.
     * @param email The email.
     * @param password The password.
     * @return true if valid, false otherwise.
     */
    boolean validateLogin(String email, String password);

    /**
     * Retrieves a user's profile by username.
     * @param username The username.
     * @return UserProfile or null if not found.
     */
    UserProfile getUserProfileByUsername(String username);

    /**
     * Retrieves basic user details (username, avatar) by username.
     * @param username The username.
     * @return Array {username, avatar} or null if not found.
     */
    String[] getUserDetails(String username);

    /**
     * Gets username by email.
     * @param email The email.
     * @return Username or null.
     */
    String getUsernameByEmail(String email);

    /**
     * Gets avatar by email.
     * @param email The email.
     * @return Avatar or null.
     */
    String getAvatarByEmail(String email);

    /**
     * Fixes null user level by setting it to 1.
     * @param username The username.
     */
    void fixUserLevel(String username);

    /**
     * Saves quiz result and updates level if passed (80%+).
     * @param username The username.
     * @param level The current level.
     * @param scorePercentage The score.
     * @return true if successful, false on error.
     */
    boolean saveQuizResultAndUpdateLevel(String username, int level, double scorePercentage);
}