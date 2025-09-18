package com.example.geoquestkidsexplorer.controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*Login Unit Testing

 //Credential Validation
 //Account Status Checks
 //Security/Rate Limiting
 //Optional/Edge Cases

 //Need to test for (Note to self):
 For each method/stimulation of user input:
 True outcomes (success cases):
  Ensures method does what it’s supposed to do in normal scenarios.
  Confirms that users can actually log in when credentials are valid.
 False outcomes (failure cases)
  Ensures code doesn’t allow invalid operations.
  Prevents security flaws (like letting someone log in with wrong credentials).
  Confirms that errors/exceptions are handled correctly.


// Explanation:
Focuses on accessing an existing account:

Are all required fields filled?  (similar to registration)

Does the username/email exist?

Does the password match the stored password?

Is the account locked, disabled, or unverified?

Are there too many failed attempts? */

// look into: regex


//Note to self:
//I just want to test the login logic directly not it's UI, thus have to add another method in login controller

class LoginControllerTest {
    private final LoginController controller = new LoginController();

    //Credential Validation
//Creating a LoginController object in each method to call the method
    // for later to have a cleaner design: create a separate class that only handles the login logic.
    //In this class methods will be static

    @Test
    void emptyEmail(){
        LoginController controller = new LoginController();
        boolean result = controller.validateLoginInputs("", "password123");
        assertFalse(result, "Login should fail for empty email");

    }

    @Test
    void emptyPassword(){
        LoginController controller = new LoginController();
        boolean result = controller.validateLoginInputs("user@gmail.com", "");
        assertFalse(result, "Login should fail for empty password");
    }

    @Test
    void wrongEmail(){
        LoginController controller = new LoginController();
        boolean result = controller.validateLoginInputs("wrongEmail@gmail.com", "test123");
        assertFalse(result, "Login should fail for email that doesn't exist in DB");
    }

    @Test
    void wrongPassword(){
        LoginController controller = new LoginController();
        boolean result = controller.validateLoginInputs("nikki@gmail.com", "Cat432");
    }

    @Test
    void correctCredentials(){
        LoginController controller = new LoginController();
        boolean result = controller.validateLoginInputs("nikki@gmail.com", "test123");
        assertTrue(result, "Login should succeed with valid credentials");
    }
//
//    @Test
//    void testWhiteSpaceOnlyIsError(){
//        controller.validateLoginInputs(" "," ");
//        assertEquals("Please enter both email and passwords");
//    }

    //AccountStatus
    //locked, disabled, verified
    //Too many failed attempts
}