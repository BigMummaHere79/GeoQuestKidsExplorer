package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.UserProfile;
import com.example.geoquestkidsexplorer.models.UserProfileBuilder;
import com.example.geoquestkidsexplorer.repositories.UserService;
import com.example.geoquestkidsexplorer.utils.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for LoginController and LoginService, focusing on login logic.
 * Uses Mockito to mock UserService for isolated testing.
 */
class LoginControllerTest {
    private LoginController controller;
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Mock UserService
        userService = Mockito.mock(UserService.class);
        controller = new LoginController(userService);
    }

    @Test
    void emptyEmail() {
        boolean result = controller.validateLogin("", "password123");
        assertFalse(result, "Login should fail for empty email");
    }

    @Test
    void emptyPassword() {
        boolean result = controller.validateLogin("user@gmail.com", "");
        assertFalse(result, "Login should fail for empty password");
    }

    @Test
    void wrongEmail() {
        when(userService.validateLogin("wrongEmail@gmail.com", "test123")).thenReturn(false);
        boolean result = controller.validateLogin("wrongEmail@gmail.com", "test123");
        assertFalse(result, "Login should fail for email that doesn't exist in DB");
    }

    @Test
    void wrongPassword() {
        when(userService.validateLogin("nikki@gmail.com", "Cat432")).thenReturn(false);
        boolean result = controller.validateLogin("nikki@gmail.com", "Cat432");
        assertFalse(result, "Login should fail for incorrect password");
    }

    @Test
    void correctCredentials() {
        when(userService.validateLogin("nikki@gmail.com", "test123")).thenReturn(true);
        when(userService.getUsernameByEmail("nikki@gmail.com")).thenReturn("nikki");
        when(userService.getUserProfileByUsername("nikki")).thenReturn(
                new UserProfileBuilder()
                        .withUsername("nikki")
                        .withEmail("nikki@gmail.com")
                        .withAvatar("👧")
                        .withLevel(1)
                        .withRole("user")
                        .build()
        );
        boolean result = controller.validateLogin("nikki@gmail.com", "test123");
        assertTrue(result, "Login should succeed with valid credentials");
    }

    @Test
    void testWhiteSpaceOnlyIsError() {
        String result = controller.validateLoginInputs(" ", " ");
        assertEquals("Please enter both email and password", result, "Whitespace-only inputs should return error");
    }

    // Additional tests for LoginService directly
    @Test
    void loginServiceEmptyEmail() {
        boolean result = LoginService.validateLogin(userService, "", "password123");
        assertFalse(result, "LoginService should fail for empty email");
    }

    @Test
    void loginServiceEmptyPassword() {
        boolean result = LoginService.validateLogin(userService, "user@gmail.com", "");
        assertFalse(result, "LoginService should fail for empty password");
    }

    @Test
    void loginServiceCorrectCredentials() {
        when(userService.validateLogin("nikki@gmail.com", "test123")).thenReturn(true);
        boolean result = LoginService.validateLogin(userService, "nikki@gmail.com", "test123");
        assertTrue(result, "LoginService should succeed with valid credentials");
    }
}