package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.repositories.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LoginController's registration validation functionality.
 * Tests validateRegistrationInputs without database calls.
 */
class RegistrationControllerTest {

    @Mock
    private UserService userService;

    private LoginController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new LoginController(userService);
    }

    // Valid inputs with non-existing username/email = no error
    @Test
    void testValidInputs() {
        when(userService.userExists("wizard01", "wizard01@domain.com")).thenReturn(false);
        String error = controller.validateRegistrationInputs(
                "wizard01", "wizard01@domain.com", "Student",
                "wizard", "wizard", "👧 Explorer Girl"
        );
        assertNull(error, "Expected no error for valid inputs");
    }

    // Blank username shows error
    @Test
    void testBlankUsername() {
        String error = controller.validateRegistrationInputs(
                "", "user@domain.com", "Student",
                "pass", "pass", "👧 Explorer Girl"
        );
        assertEquals("Please fill in all the fields", error);
    }

    // Blank email shows error
    @Test
    void testBlankEmail() {
        String error = controller.validateRegistrationInputs(
                "alice", "", "Student",
                "pass123", "pass123", "👦 Explorer Boy"
        );
        assertEquals("Please fill in all the fields", error);
    }

    // Blank role shows error
    @Test
    void testBlankRole() {
        String error = controller.validateRegistrationInputs(
                "alice", "alice@example.com", "",
                "pass123", "pass123", "👦 Explorer Boy"
        );
        assertEquals("Please fill in all the fields", error);
    }

    // Blank password shows error
    @Test
    void testBlankPassword() {
        String error = controller.validateRegistrationInputs(
                "alice", "alice@example.com", "Student",
                "", "pass123", "👦 Explorer Boy"
        );
        assertEquals("Please fill in all the fields", error);
    }

    // Blank confirm password shows error
    @Test
    void testBlankConfirmPassword() {
        String error = controller.validateRegistrationInputs(
                "alice", "alice@example.com", "Student",
                "pass123", "", "👦 Explorer Boy"
        );
        assertEquals("Please fill in all the fields", error);
    }

    // Non-matching passwords show error
    @Test
    void testPasswordsDoNotMatch() {
        String error = controller.validateRegistrationInputs(
                "alice", "a@example.com", "Student",
                "password123", "different", "👧 Explorer Girl"
        );
        assertEquals("Passwords do not match", error);
    }

    // Existing username or email shows error
    @Test
    void testExistingUser() {
        when(userService.userExists("alice", "a@example.com")).thenReturn(true);
        String error = controller.validateRegistrationInputs(
                "alice", "a@example.com", "Student",
                "password123", "password123", "👧 Explorer Girl"
        );
        assertEquals("Username or email already exists.", error);
    }

    // Blank avatar shows error
    @Test
    void testBlankAvatar() {
        when(userService.userExists("bob", "b@example.com")).thenReturn(false);
        String error = controller.validateRegistrationInputs(
                "bob", "b@example.com", "Teacher",
                "password123", "password123", ""
        );
        assertEquals("Please pick an avatar", error);
    }

    // All inputs valid, non-existing user = no error
    @Test
    void testAllInputsValid() {
        when(userService.userExists("carol", "c@example.com")).thenReturn(false);
        String error = controller.validateRegistrationInputs(
                "carol", "c@example.com", "Teacher",
                "password123", "password123", "👦 Explorer Boy"
        );
        assertNull(error, "Expected no validation error for valid inputs");
    }
}