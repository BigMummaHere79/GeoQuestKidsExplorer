package com.example.geoquestkidsexplorer.models;

//import static com.example.geoquestkidsexplorer.models.UserSession.username;
import static org.junit.jupiter.api.Assertions.*;

import com.example.geoquestkidsexplorer.repositories.UserSessionObserver;
import org.junit.jupiter.api.*;

class UserSessionTest {


    private static final class CountingObserver implements UserSessionObserver{
        int updates = 0;
        int clears = 0;
        String lastUser, lastAvatar;

        @Override
        public void onSessionUpdated(String username, String avatar) {
            updates++;
            lastUser = username;
            lastAvatar = avatar;
        }

        @Override
        public void onSessionCleared() {
            clears++;
        }
    }

    @AfterEach
    // Always reset after every test to avoid errors or problems
    void reset() {
        try {
            UserSession.getInstance().clear();
        } catch (Throwable ignored) {}
    }

    @Test
    // test that username is minus 1
    void testUsernameMinusOne(){
        assertNull(UserSession.getUsername());
    }

    // Test that default username is null
    @Test
    void setDefaultUsernameIsNull(){
        assertNull(UserSession.getAvatar());
    }

    // test that Avatar is updated
    @Test
    void setAvatarUpdates(){
        UserSession.getInstance().setAvatar("👧 Explorer Girl");
        assertEquals("👧 Explorer Girl",UserSession.getAvatar());
    }

    // test username is cleared
    @Test
    void testClearUsername(){
        //UserSession.setUserId(100);
        UserSession.getInstance().setUsername("Tori");
        UserSession.getInstance().setAvatar("👧 Explorer Girl");

        UserSession.getInstance().clear();
        assertNull(UserSession.getUsername());
    }

    // test avatar is cleared
    @Test
    void testClearAvatar(){
        //UserSession.setUserId(100);
        UserSession.getInstance().setUsername("Tori");
        UserSession.getInstance().setAvatar("👧 Explorer Girl");

        UserSession.getInstance().clear();
        assertNull(UserSession.getAvatar());
    }

    //Test the set user test
    @Test
    void testSetUser(){
        UserSession.getInstance().setUser("Tom", "👧  Girl");
        //UserSession.getUsername();
        assertEquals("Tom", UserSession.getUsername());

    }

    @Test
    void testAddObserver(){
        var session = UserSession.getInstance();
        session.clear();

        var obs = new CountingObserver();
        session.addObserver(obs);
        session.setUser("Tori","👧  Girl");
        session.clear();

        assertEquals(1, obs.updates);
        assertEquals(1, obs.clears);
        assertEquals("Tori", obs.lastUser);
        assertEquals("👧  Girl", obs.lastAvatar);
    }

    @Test
    void testRemoveobserver(){
        var session = UserSession.getInstance();
        session.clear();

        var obs = new CountingObserver();
        session.addObserver(obs);
        session.removeObserver(obs);
        session.setUser("Tori","👧  Girl");
        session.clear();

        assertEquals(0,obs.updates);
        assertEquals(0,obs.clears);
    }
}