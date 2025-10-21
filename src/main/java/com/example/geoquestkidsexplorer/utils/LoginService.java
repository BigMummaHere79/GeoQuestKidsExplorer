package com.example.geoquestkidsexplorer.utils;

import com.example.geoquestkidsexplorer.repositories.UserService;

/**
 * Service class for handling login-related logic.
 * Provides static methods to validate login inputs and credentials, decoupling logic from UI.
 */
public class LoginService {

    /**
     * Validates login inputs for emptiness.
     * @param email The email input.
     * @param password The password input.
     * @return Error message or null if valid.
     */
    public static String validateLoginInputs(String email, String password) {
        String mail = (email == null) ? "" : email.trim();
        String pass = (password == null) ? "" : password.trim();
        return (mail.isBlank() || pass.isBlank()) ? "Please enter both email and password" : null;
    }

    /**
     * Validates login credentials against the database.
     * @param userService The UserService to query the database.
     * @param email The email input.
     * @param password The password input.
     * @return True if credentials are valid, false otherwise.
     */
    public static boolean validateLogin(UserService userService, String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        return userService.validateLogin(email, password);
    }
}