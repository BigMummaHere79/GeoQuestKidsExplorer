package com.example.geoquestkidsexplorer;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Singleton class to manage the global state of the game, including unlocked continents.
 */
public class GameStateManager {

    private static GameStateManager instance;
    private final Set<String> unlockedContinents;

    // Define the progression order of the continents
    private static final List<String> CONTINENT_ORDER = Arrays.asList(
            "Antarctica",
            "Oceania",
            "South America",
            "North America",
            "Europe",
            "Asia",
            "Africa"
    );

    private GameStateManager() {
        this.unlockedContinents = new HashSet<>();
        // Load state from database
        loadState();
    }

    /**
     * Loads the unlocked continents based on the user's current level.
     */
    public void loadState() {
        String username = UserSession.getUsername();
        unlockedContinents.clear(); // Clear existing state
        if (username == null || username.isEmpty()) {
            System.err.println("loadState: No user logged in (username is null or empty), defaulting to Antarctica");
            unlockedContinents.add("Antarctica");
            return;
        }

        String sql = "SELECT level FROM users WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            System.out.println("loadState: Querying level for username: " + username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int level = rs.getInt("level");
                    if (rs.wasNull() || level < 1) {
                        System.err.println("loadState: User " + username + " has null or invalid level (" + (rs.wasNull() ? "NULL" : level) + "), updating to 1");
                        updateUserLevel(username, 1); // Fix NULL or invalid level
                        unlockedContinents.add("Antarctica");
                    } else {
                        System.out.println("loadState: User " + username + " found with level: " + level);
                        for (int i = 1; i <= level; i++) {
                            String continent = DatabaseManager.getContinentByLevel(i);
                            if (continent != null) {
                                unlockedContinents.add(continent);
                            } else {
                                System.err.println("loadState: No continent found for level: " + i);
                            }
                        }
                    }
                } else {
                    System.err.println("loadState: No user found for username: " + username + ", defaulting to Antarctica");
                    unlockedContinents.add("Antarctica");
                }
            }
        } catch (SQLException e) {
            System.err.println("loadState error: " + e.getMessage());
            e.printStackTrace();
            unlockedContinents.add("Antarctica"); // Fallback
        }
        System.out.println("loadState: Unlocked continents = " + unlockedContinents);
    }

    /**
     * Updates the user's level in the database.
     */
    private void updateUserLevel(String username, int level) {
        String sql = "UPDATE users SET level = ? WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, level);
            ps.setString(2, username);
            int rows = ps.executeUpdate();
            System.out.println("updateUserLevel: Updated level to " + level + " for user " + username + ", rows affected: " + rows);
            if (rows == 0) {
                System.err.println("updateUserLevel: No user found for username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("updateUserLevel error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Saves the current state of unlocked continents to the database.
     */
    public void saveState() {
        String username = UserSession.getUsername();
        if (username != null) {
            // Determine the highest unlocked level
            int highestLevel = 0;
            for (String continent : unlockedContinents) {
                String sql = "SELECT level FROM continents WHERE continent = ?";
                try (Connection conn = DatabaseManager.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, continent);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            int level = rs.getInt("level");
                            if (level > highestLevel) {
                                highestLevel = level;
                            }
                        }
                    }
                } catch (SQLException e) {
                    System.err.println("saveState error: " + e.getMessage());
                }
            }
            // Update user's level in the database
            String updateSql = "UPDATE users SET level = ? WHERE username = ?";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, highestLevel);
                ps.setString(2, username);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.err.println("saveState error: " + e.getMessage());
            }
        }
        System.out.println("State saved: Unlocked = " + unlockedContinents);
    }

    /**
     * Returns the single instance of the GameStateManager.
     *
     * @return The singleton instance.
     */
    public static GameStateManager getInstance() {
        if (instance == null) {
            instance = new GameStateManager();
        }
        return instance;
    }

    /**
     * Gets the next continent based on the current continent.
     */
    public String getNextContinent(String currentContinent) {
        String sql = "SELECT c2.continent FROM continents c1 " +
                "JOIN continents c2 ON c2.level = c1.level + 1 " +
                "WHERE c1.continent = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, currentContinent);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nextContinent = rs.getString("continent");
                    System.out.println("getNextContinent: Found " + nextContinent + " as next continent after " + currentContinent);
                    return nextContinent;
                } else {
                    System.out.println("getNextContinent: No next continent found for " + currentContinent);
                    return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("getNextContinent error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Unlocks a continent by adding it to the unlocked list.
     *  @param continent The name of the continent to unlock.
     */
    public void unlockContinent(String continent) {
        if (!unlockedContinents.contains(continent)) {
            unlockedContinents.add(continent);
            System.out.println("unlockContinent: Added " + continent + " to unlocked continents");
        }
    }

    /**
     * Checks if a continent is currently unlocked.
     *
     * @param continentName The name of the continent to check.
     * @return true if the continent is unlocked, false otherwise.
     */
    public boolean isContinentUnlocked(String continentName) {
        return continentName != null && unlockedContinents.contains(continentName);
    }

    /**
     * Checks if a continent is currently locked.
     * This is the method the StartAdventureController will use.
     *
     * @param continentName The name of the continent to check.
     * @return true if the continent is locked, false otherwise.
     */
    public boolean isContinentLocked(String continentName) {
        return !isContinentUnlocked(continentName);
    }

    /**
     * Gets the list of unlocked continents.
     *
     * @return A new Set containing the unlocked continents.
     */
    public Set<String> getUnlockedContinents() {
        return new HashSet<>(unlockedContinents);
    }
}