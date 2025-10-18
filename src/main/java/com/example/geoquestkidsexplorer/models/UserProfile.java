package com.example.geoquestkidsexplorer.models;

/**
 * Model for a user's profile, matching database schema.
 * Encapsulates user data with private fields and public getters/setters.
 * Supports basic construction and DB-loaded construction.
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
     * Full constructor from DB query.
     * @param username Username.
     * @param email Email.
     * @param avatar Avatar.
     * @param level Current level.
     * @param role Role.
     */
    public UserProfile(String username, String email, String avatar, int level, String role) {
        this.username = username;
        this.email = email;
        this.avatar = avatar;
        this.level = level;
        this.role = role;
        this.score = 0;
        this.levelsCompleted = 0;  // Compute via service if needed
    }

    /**
     * Basic constructor for new profiles.
     * @param username Username.
     * @param avatar Avatar.
     */
    public UserProfile(String username, String avatar) {
        this(username, null, avatar, 1, "user");
    }

    // Getters and setters for encapsulation
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getLevelsCompleted() { return levelsCompleted; }
    public void setLevelsCompleted(int levelsCompleted) { this.levelsCompleted = levelsCompleted; }
}