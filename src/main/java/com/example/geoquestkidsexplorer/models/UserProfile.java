package com.example.geoquestkidsexplorer.models;

/**
 * A class representing a user's profile in the GeoQuest Kids Explorer game.
 * This model holds the user's name and chosen avatar.
 */
public class UserProfile {

    private String explorerName;
    private String avatar;
    private int score; // Add this field
    private int levelsCompleted; // Add this field

    /**
     * Constructs a new UserProfile.
     * @param explorerName The name of the explorer.
     * @param avatar The avatar chosen by the explorer.
     * @param score The score user gets when they completed a level
     * @param levelsCompleted The levels the user completed.
     */
    public UserProfile(String explorerName, String avatar, int score, int levelsCompleted) {
        this.explorerName = explorerName;
        this.avatar = avatar;
        this.score = score;
        this.levelsCompleted = levelsCompleted;
    }

    /**
     * Constructs a new UserProfile with only name and avatar.
     * Use this for initial profile creation.
     */
    public UserProfile(String explorerName, String avatar, String string, int level, String role) {
        this(explorerName, avatar, 0, 0); // Call the main constructor with default values
    }

    // Getters of all fields
    public String getExplorerName() {
        return explorerName;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getScore() {
        return score;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    // Setters of all fields
   /* public void setExplorerName(String explorerName) {
        this.explorerName = explorerName;
    }*/

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setScore(int score) {
        this.score = score;
    }

  /*public void setLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }*/
}

