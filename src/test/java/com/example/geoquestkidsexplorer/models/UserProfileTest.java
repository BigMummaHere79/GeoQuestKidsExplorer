package com.example.geoquestkidsexplorer.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Just to revalidate the login and register controllers
 * Checking and testing USerProfile
 * */

class UserProfileTest {
    private  UserProfile make(){
        return new UserProfile(
                "Tori",
                "👦 Explorer Boy",
                null,
                1,
                "Student");
    }

    // Using defaults, test that username is correct
    @Test
    void testCorrectUsername(){
        assertEquals("Tori",make().getUsername());
    }

    // Test that Avatar is correct
    @Test
    void testCorrectAvatar(){
        assertEquals("👦 Explorer Boy",make().getAvatar());
    }

    // Retrieve levels
    @Test
    void testRetrieveLevel(){
        assertEquals(0,make().getLevelsCompleted());
    }

    // Retrieve scores
    @Test
    void testRetrieveScore(){
        assertEquals(0,make().getScore());
    }

    // Test score by updating to a random number
    @Test
    void testSetScoreUpdatesValues(){
        var scoreUpdates = make();
        scoreUpdates.setScore(20);
        assertEquals(20, scoreUpdates.getScore());
    }

    // test that setting avatar works
    @Test
    void testSetAvatar(){
        var avatar = make();
        avatar.setAvatar("👦 Explorer Boy");
        assertEquals("👦 Explorer Boy",avatar.getAvatar());

    }

}