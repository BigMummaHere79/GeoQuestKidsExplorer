package com.example.geoquestkidsexplorer.models;

//import static com.example.geoquestkidsexplorer.models.UserSession.username;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class UserSessionTest {

    @AfterEach
    // Always reset after every test to avoid errors or problems
  /*  void reset() {
        try {
            UserSession.clear();
        } catch (Throwable ignored) {}
    }*/

    @Test
    // test that username is minus 1
    void testUsernameMinusOne(){
        assertEquals(null,UserSession.getUsername());
    }

    // Test that user id updates to a specific number
   /* @Test
    void testSetUsernamepdates(){
        UserSession.setUsername("update_user");
        String username = "";
        assertEquals("update_user",UserSession.setUsername(username));
    }*/

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

    //NOTE: Had to comment out as i am currently doing the user progress saving state, and we dont have id in user's table
    // we use username instead for primary key. Sorry Tori for modifying some of your method. Glenda!
    // test that Id is cleared
    /*@Test
    void testClearUserId(){
        UserSession.setUserId(100);
        UserSession.setUsername("Tori");
        UserSession.setAvatar("👧 Explorer Girl");

        UserSession.clear();

        assertEquals(-1, UserSession.getUserId());
    }*/

    // test username is cleared
    @Test
    void testClearUsername(){
        //UserSession.setUserId(100);
        UserSession.setUsername("Tori");
        UserSession.setAvatar("👧 Explorer Girl");

        UserSession.clear();
        assertNull(UserSession.getUsername());
    }

    // test avatar is cleared
    @Test
    void testClearAvatar(){
        //UserSession.setUserId(100);
        UserSession.setUsername("Tori");
        UserSession.setAvatar("👧 Explorer Girl");

        UserSession.clear();
        assertNull(UserSession.getAvatar());
    }

    //Test the set user test
    @Test
    void testSetUser(){
        UserSession.setUser("Tom", "👧  Girl");
        //UserSession.getUsername();
        assertEquals("Tom", UserSession.getUsername());

    }

    //Test the set Explorer name
    @Test
    void testSetExplorerName(){
        UserSession.setExplorerName("Tommy");
        assertEquals("Tommy", UserSession.getExplorerName());
    }

    //Test the get Explorer name

}