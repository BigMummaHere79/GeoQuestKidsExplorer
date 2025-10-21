package com.example.geoquestkidsexplorer.models;

import com.example.geoquestkidsexplorer.repositories.UserSessionObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton class managing current user session data.
 * Implements the Singleton pattern to ensure a single instance and the Observer pattern
 * to notify subscribers of session changes.
 */
public class UserSession {
    private static UserSession instance;
    private static String username;
    private static String avatar;
    //private static String explorerName;
    private final List<UserSessionObserver> observers;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private UserSession() {
        this.observers = new ArrayList<>();
    }

    /**
     * Gets the singleton instance of UserSession.
     * @return The singleton instance.
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Adds an observer to be notified of session changes.
     * @param observer The observer to add.
     */
    public void addObserver(UserSessionObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the notification list.
     * @param observer The observer to remove.
     */
    public void removeObserver(UserSessionObserver observer) {
        observers.remove(observer);
    }

    /**
     * Sets the current user session data and notifies observers.
     * @param username The username to set.
     * @param avatar The avatar to set.
     */
    public void setUser(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        notifyObservers();
    }

    /**
     * Gets the username.
     * @return The username, or null if not set.
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Sets the username and notifies observers.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
        notifyObservers();
    }

    /**
     * Gets the avatar.
     * @return The avatar, or null if not set.
     */
    public static String getAvatar() {
        return avatar;
    }

    /**
     * Sets the avatar and notifies observers.
     * @param avatar The avatar to set.
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
        notifyObservers();
    }

    /**
     * Gets the explorer name.
     * @return The explorer name, or null if not set.
     */
    /*public static String getExplorerName() {
        return explorerName;
    }*/

    /**
     * Sets the explorer name.
     * @param name The explorer name to set.
     */
    /*public static void setExplorerName(String name) {
        explorerName = name;
    }*/

    /**
     * Clears all session data and notifies observers.
     */
    public void clear() {
        this.username = null;
        this.avatar = null;
        notifyCleared();
    }

    /**
     * Notifies all observers of a session update.
     */
    private void notifyObservers() {
        for (UserSessionObserver observer : observers) {
            observer.onSessionUpdated(username, avatar);
        }
    }

    /**
     * Notifies all observers of a session clear.
     */
    private void notifyCleared() {
        for (UserSessionObserver observer : observers) {
            observer.onSessionCleared();
        }
    }
}