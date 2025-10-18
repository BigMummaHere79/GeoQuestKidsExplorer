package com.example.geoquestkidsexplorer.models;

/**
 * Singleton-like static class managing current user session data.
 * Encapsulates username and avatar using private static fields with public static accessors.
 * Use static methods for session management (no instance needed).
 */
public class UserSession {
    private static String username;
    private static String avatar;
    private static String explorerName;

    /**
     * Sets the current user session data.
     * @param username Username.
     * @param avatar Avatar.
     */
    public static void setUser(String username, String avatar) {
        UserSession.username = username;
        UserSession.avatar = avatar;
    }

    /**
     * Gets the username.
     * @return The username, or null if not set.
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param name The username to set.
     */
    public static void setUsername(String name) {
        username = name;
    }

    /**
     * Gets the avatar.
     * @return The avatar, or null if not set.
     */
    public static String getAvatar() {
        return avatar;
    }

    /**
     * Sets the avatar.
     * @param av The avatar to set.
     */
    public static void setAvatar(String av) {
        avatar = av;
    }

    /**
     * Gets the explorer name.
     *
     * @return The explorer name, or null if not set.
     */
    public static String getExplorerName() {
        return explorerName;
    }

    /**
     * Sets the explorer name.
     * @param name The explorer name to set.
     */
    public static void setExplorerName(String name) {
        explorerName = name;
    }

    /**
     * Clears all session data.
     */
    public static void clear() {
        username = null;
        avatar = null;
        explorerName = null;
    }
}