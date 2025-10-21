package com.example.geoquestkidsexplorer.models;

/**
 * Model for a user's profile, matching database schema.
 * Encapsulates user data with private fields and public getters/setters.
 * Supports construction via {@link UserProfileBuilder} for flexibility.
 */
public class UserProfile {
    private String username;  // Fixed: was explorerName
    private String email;
    private String avatar;
    private int level;
    private String role;
    private int score;  // Default/computed
    private int levelsCompleted;  // Default/computed (e.g., query results table)

    /**
     * Constructor for creating a user profile with all fields.
     * Use {@link UserProfileBuilder} for a fluent API.
     * @param username The username.
     * @param email The email, nullable.
     * @param avatar The avatar.
     * @param level The current level.
     * @param role The role.
     * @param score The score.
     * @param levelsCompleted The number of levels completed.
     */
    protected UserProfile(String username, String email, String avatar, int level, String role, int score, int levelsCompleted) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.level = level;
        this.role = role;
        this.score = score;
        this.levelsCompleted = levelsCompleted;
    }

    /**
     * Gets the username.
     * @return The username.
     */
    public String getUsername() { return username; }

    /**
     * Sets the username.
     * @param username The username to set.
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * Gets the email.
     * @return The email.
     */
    public String getEmail() { return email; }

    /**
     * Sets the email.
     * @param email The email to set.
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets the avatar.
     * @return The avatar.
     */
    public String getAvatar() { return avatar; }

    /**
     * Sets the avatar.
     * @param avatar The avatar to set.
     */
    public void setAvatar(String avatar) { this.avatar = avatar; }

    /**
     * Gets the level.
     * @return The level.
     */
    public int getLevel() { return level; }

    /**
     * Sets the level.
     * @param level The level to set.
     */
    public void setLevel(int level) { this.level = level; }

    /**
     * Gets the role.
     * @return The role.
     */
    public String getRole() { return role; }

    /**
     * Sets the role.
     * @param role The role to set.
     */
    public void setRole(String role) { this.role = role; }

    /**
     * Gets the score.
     * @return The score.
     */
    public int getScore() { return score; }

    /**
     * Sets the score.
     * @param score The score to set.
     */
    public void setScore(int score) { this.score = score; }

    /**
     * Gets the number of levels completed.
     * @return The number of levels completed.
     */
    public int getLevelsCompleted() { return levelsCompleted; }

    /**
     * Sets the number of levels completed.
     * @param levelsCompleted The number of levels completed to set.
     */
    public void setLevelsCompleted(int levelsCompleted) { this.levelsCompleted = levelsCompleted; }
}