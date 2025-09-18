package com.example.geoquestkidsexplorer;

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
        // Initialize with a default state.
        // For a full game, this would be loaded from a save file.
        this.unlockedContinents = new HashSet<>();
        this.unlockedContinents.add("Antarctica"); // Africa is unlocked by default
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
     * Unlocks a continent, making it available for future adventures.
     *
     * @param continentName The name of the continent to unlock.
     */
    public void unlockContinent(String continentName) {
        if (continentName != null && !unlockedContinents.contains(continentName)) {
            unlockedContinents.add(continentName);
            System.out.println(continentName + " unlocked!"); // Debug log
            // Optional: Save state to DB/file here
            // saveState();
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
     * Gets the next continent in the progression list.
     * @param currentContinent The name of the current continent.
     * @return The name of the next continent, or null if it's the last one.
     */
    public String getNextContinent(String currentContinent) {
        int currentIndex = CONTINENT_ORDER.indexOf(currentContinent);
        if (currentIndex != -1 && currentIndex < CONTINENT_ORDER.size() - 1) {
            return CONTINENT_ORDER.get(currentIndex + 1);
        }
        return null; // Last continent
    }

    // Optional: For persistence (stub for now)
    public void saveState() {
        // TODO: Save unlockedContinents to DB or file
        System.out.println("State saved: Unlocked = " + unlockedContinents);
    }

    public void loadState() {
        // TODO: Load unlockedContinents from DB or file
        System.out.println("State loaded.");
    }
}