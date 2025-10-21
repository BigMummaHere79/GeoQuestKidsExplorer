package com.example.geoquestkidsexplorer.repositories;

/**
 * Interface for observers of user session changes.
 * Implements the Observer pattern to notify subscribers of session updates.
 */
public interface UserSessionObserver {
    /**
     * Called when the user session is updated.
     * @param username The new username.
     * @param avatar The new avatar.
     */
    void onSessionUpdated(String username, String avatar);

    /**
     * Called when the user session is cleared.
     */
    void onSessionCleared();
}