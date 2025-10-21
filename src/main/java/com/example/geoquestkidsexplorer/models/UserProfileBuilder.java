package com.example.geoquestkidsexplorer.models;

/**
 * Builder class for constructing {@link UserProfile} objects.
 * Implements the Builder pattern to provide a fluent API for creating user profiles with default values.
 */
public class UserProfileBuilder {
    private String username;
    private String email;
    private String avatar;
    private int level = 1;
    private String role = "user";
    private int score = 0;
    private int levelsCompleted = 0;

    /**
     * Sets the username for the user profile.
     * @param username The username to set.
     * @return This builder instance for method chaining.
     */
    public UserProfileBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * Sets the email for the user profile.
     * @param email The email to set.
     * @return This builder instance for method chaining.
     */
    public UserProfileBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    /**
     * Sets the avatar for the user profile.
     * @param avatar The avatar to set.
     * @return This builder instance for method chaining.
     */
    public UserProfileBuilder withAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    /**
     * Sets the level for the user profile.
     * @param level The level to set.
     * @return This builder instance for method chaining.
     */
    public UserProfileBuilder withLevel(int level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the role for the user profile.
     * @param role The role to set.
     * @return This builder instance for method chaining.
     */
    public UserProfileBuilder withRole(String role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the score for the user profile.
     * @param score The score to set.
     * @return This builder instance for method chaining.
     */
    public UserProfileBuilder withScore(int score) {
        this.score = score;
        return this;
    }

    /**
     * Sets the number of levels completed for the user profile.
     * @param levelsCompleted The number of levels completed to set.
     * @return This builder instance for method chaining.
     */
    public UserProfileBuilder withLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
        return this;
    }

    /**
     * Builds and returns a new {@link UserProfile} instance.
     * @return A new UserProfile object with the specified attributes.
     * @throws IllegalStateException If required fields (username, avatar) are not set.
     */
    public UserProfile build() {
        if (username == null || username.isBlank() || avatar == null || avatar.isBlank()) {
            throw new IllegalStateException("Username and avatar are required");
        }
        return new UserProfile(username, email, avatar, level, role, score, levelsCompleted);
    }
}