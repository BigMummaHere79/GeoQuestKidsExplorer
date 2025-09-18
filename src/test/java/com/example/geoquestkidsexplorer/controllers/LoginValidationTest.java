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

/*

class LoginControllerTest1 {

    //Input Validation
    //Call exceptions on controllers
    void CheckInputEmpty(){

    }

 //Credential Validation
 void wrongEmail(){
  LoginController controller = new LoginController();
  boolean result = controller.handleLogin("wrongEmail@gmail.com", "password123");     //check method for login
  assertFalse(result, "Login should fail for email that doesn't exist");

 }

    void CheckPassword(){

    }

    //Account Status Checks
    void CheckAccountStatus(){

    }

    void CheckLoginAttempts(){

    }


} */

class LoginValidationTest{
    private final LoginController controller = new LoginController();

    @Test
    void testEmptyEmailErrorMessage(){
        String err = controller.validateLoginInputs("","password");
        assertEquals("Please enter both email and passwords", err);
    }

    @Test
    void testEmptyPasswordErrorMessage(){
        String err = controller.validateLoginInputs("valid1@domain.com","");
        assertEquals("Please enter both email and passwords", err);
    }

    @Test
    void testWhenBothFieldsAreEmptyMessage(){
        String err = controller.validateLoginInputs("","");
        assertEquals("Please enter both email and passwords", err);
    }

    @Test
    void testWhenBothInputsAreValid(){
        String err = controller.validateLoginInputs("email1@domain.com","Password123");
        assertNull(err);
    }

    @Test
    void testWhiteSpaceOnlyIsError(){
        String err = controller.validateLoginInputs(" "," ");
        assertEquals("Please enter both email and passwords", err);
    }
}