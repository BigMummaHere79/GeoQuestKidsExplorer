package com.example.geoquestkidsexplorer.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class UserSessionTest {

    @AfterEach
    // Always reset after every test to avoid errors or problems
    void reset() {
        try {
            UserSession.clear();
        } catch (Throwable ignored) {}
    }

    @Test
    // test that user ID is minus 1
    void testUserIDMinusOne(){
        assertEquals(-1,UserSession.getUserId());
    }

    // Test that user id updates to a specific number
    @Test
    void testSetUserIdUpdates(){
        UserSession.setUserId(20);
        assertEquals(20,UserSession.getUserId());
    }

    // Test that default username is null
    @Test
    void setDefaultUsernameIsNull(){
        assertNull(UserSession.getAvatar());
    }

    // test that Avatar is updated
    @Test
    void setAvatarUpdates(){
        UserSession.setAvatar("👧 Explorer Girl");
        assertEquals("👧 Explorer Girl",UserSession.getAvatar());
    }

    // test that Id is cleared
    @Test
    void testClearUserId(){
        UserSession.setUserId(100);
        UserSession.setUsername("Tori");
        UserSession.setAvatar("👧 Explorer Girl");

        UserSession.clear();

        assertEquals(-1, UserSession.getUserId());
    }

    // test username is cleared
    @Test
    void testClearUsername(){
        UserSession.setUserId(100);
        UserSession.setUsername("Tori");
        UserSession.setAvatar("👧 Explorer Girl");

        UserSession.clear();
        assertNull(UserSession.getUsername());
    }

    // test avatar is cleared
    @Test
    void testClearAvatar(){
        UserSession.setUserId(100);
        UserSession.setUsername("Tori");
        UserSession.setAvatar("👧 Explorer Girl");

        UserSession.clear();
        assertNull(UserSession.getAvatar());
    }
}