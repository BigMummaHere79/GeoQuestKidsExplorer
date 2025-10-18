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

    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    public static void setAvatar(String av) {
        avatar = av;
    }

    public static String getAvatar() {
        return avatar;
    }

    public static String getExplorerName() {
        return explorerName;
    }

    public static void setExplorerName(String name) {
        explorerName = name;
    }

    /**
     * Clears the session.
     */
    public static void clear() {
        username = null;
        avatar = null;
        explorerName = null;
    }
}