/**
 * UserSession is a singleton class that manages the current user's session data.
 * It stores the user ID after successful login for use across controllers.
 */
package com.example.geoquestkidsexplorer.models;

public class UserSession {
    //private static int userId = -1;
    // Add these new fields for the username and avatar
    // Add these new fields for the username and avatar
    private static String username;
    private static String avatar;
    private static String explorerName;

    /**
     * Sets the current user data in the session.
     */
    public static void setUser(String username, String avatar) {
        UserSession.username = username;
        UserSession.avatar = avatar;
    }

    /**
     * Sets the current user ID in the session.
     *
     * @param id The user ID to set.
     */
    /*public static void setUserId(int id) {
        userId = id;
    }*/

    /**
     * Gets the current user ID from the session.
     *
     * @return The user ID, or -1 if no user is logged in.
     */
    /*public static int getUserId() {
        return userId;
    }*/

    // Add these new methods to set and get the username
    public static void setUsername(String name) {
        username = name;
    }

    public static String getUsername() {
        return username;
    }

    // Add these new methods to set and get the avatar
    public static void setAvatar(String av) {
        avatar = av;
    }

    public static String getAvatar() {
        return avatar;
    }

    public static String getExplorerName() { return explorerName; }
    public static void setExplorerName(String name) { explorerName = name; }


    /**
     * Clears the session by resetting all user data.
     */
    public static void clear() {
        //userId = -1;
        username = null;
        avatar = null;
        explorerName = null;
    }
}
