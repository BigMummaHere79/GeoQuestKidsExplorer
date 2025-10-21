package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.UserProfile;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.repositories.UserService;
import com.example.geoquestkidsexplorer.repositories.UserSessionObserver;
import com.example.geoquestkidsexplorer.utils.LoginService;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the login and registration UI.
 * Manages user authentication, validation, and navigation using Dependency Injection for services.
 * Implements UserSessionObserver to react to session changes.
 */
public class LoginController extends BaseController implements UserSessionObserver {
    private final UserService userService;

    @FXML private VBox loginForm;
    @FXML private VBox registerForm;
    @FXML private VBox loginWelcomeMessage;
    @FXML private VBox registerWelcomeMessage;
    @FXML private Label messageLabel;

    @FXML private TextField loginEmailField;
    @FXML private PasswordField loginPasswordField;

    @FXML private TextField registerUsernameField;
    @FXML private TextField registerEmailField;
    @FXML private ComboBox<String> roleCombo;
    @FXML private PasswordField registerPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> avatarCombo;

    private Stage stage;

    /**
     * Constructor with dependency injection for UserService.
     * @param userService The UserService to handle user operations.
     */
    public LoginController(UserService userService) {
        this.userService = userService;
        UserSession.getInstance().addObserver(this);
    }

    /**
     * Sets the stage for this controller.
     * @param stage The JavaFX stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the UI panels and combo boxes.
     */
    @FXML
    public void initialize() {
        try {
            togglePanels(false);
            if (loginWelcomeMessage != null) {
                loginWelcomeMessage.setVisible(true);
                loginWelcomeMessage.setManaged(true);
            }
            if (registerWelcomeMessage != null) {
                registerWelcomeMessage.setVisible(false);
                registerWelcomeMessage.setManaged(false);
            }

            if (roleCombo != null) {
                roleCombo.setItems(FXCollections.observableArrayList("Student", "Teacher"));
            }
            if (avatarCombo != null) {
                avatarCombo.setItems(FXCollections.observableArrayList(
                        "👦 Explorer Boy",
                        "👧 Explorer Girl",
                        "👨‍🎓 Student (Boy)",
                        "👩‍🎓 Student (Girl)"
                ));
            }
        } catch (Exception e) {
            displayError("Initialization error: " + e.getMessage());
        }
    }

    /**
     * Handles login validation and navigation to the home page.
     * @param event The action event triggered by the login button.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = text(loginEmailField);
        String password = text(loginPasswordField);

        String err = validateLoginInputs(email, password);
        if (err != null) {
            displayError(err);
            return;
        }

        if (LoginService.validateLogin(userService, email, password)) {
            UserProfile profile = userService.getUserProfileByUsername(userService.getUsernameByEmail(email));
            if (profile == null) {
                displayError("User not found.");
                return;
            }
            UserSession.getInstance().setUser(profile.getUsername(), profile.getAvatar());
            userService.fixUserLevel(profile.getUsername());
            try {
                NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                        "/com/example/geoquestkidsexplorer/homepage.fxml",
                        (HomePageController controller) -> controller.setProfileData(profile.getUsername(), profile.getAvatar()));
            } catch (IOException e) {
                displayError("Navigation error: " + e.getMessage());
            }
        } else {
            displayError("Invalid email or password.");
        }
    }

    /**
     * Validates login inputs for unit testing.
     * @param email    The email input.
     * @param password The password input.
     * @return Error message or null if valid.
     */
    public String validateLoginInputs(String email, String password) {
        return LoginService.validateLoginInputs(email, password);
    }

    /**
     * Validates login against the database.
     * @param email    The email input.
     * @param password The password input.
     * @return True if valid, false otherwise.
     */
    public boolean validateLogin(String email, String password) {
        return LoginService.validateLogin(userService, email, password);
    }

    /**
     * Handles registration and navigates to "login" on success.
     * @param event The action event triggered by the register button.
     */
    @FXML
    private void handleRegister(ActionEvent event) {
        String username = text(registerUsernameField);
        String email = text(registerEmailField);
        String role = roleCombo == null ? "user" : roleCombo.getValue();
        String password = text(registerPasswordField);
        String confirm = text(confirmPasswordField);
        String avatarDisplay = avatarCombo == null ? null : avatarCombo.getValue();

        String err = validateRegistrationInputs(username, email, role, password, confirm, avatarDisplay);
        if (err != null) {
            displayError(err);
            return;
        }

        String avatarEmoji = extractAvatarEmoji(avatarDisplay);

        try {
            userService.insertUser(username, email, password, avatarEmoji, role);
            displaySuccess("Registration successful! Please login.");
            clearRegisterFields();
            switchToLogin(event);
        } catch (RuntimeException e) {
            displayError("Registration error: " + e.getMessage());
        }
    }

    /**
     * Validates registration inputs.
     * @param username The username input.
     * @param email The email input.
     * @param role The role input.
     * @param password The password input.
     * @param confirm The "confirm" password input.
     * @param avatarDisplay The avatar display text.
     * @return Error message or null if valid.
     */
    public String validateRegistrationInputs(String username, String email, String role,
                                             String password, String confirm, String avatarDisplay) {
        if (username.isBlank() || email.isBlank() || role.isBlank() || password.isBlank() || confirm.isBlank()) {
            return "Please fill in all the fields";
        }
        if (!password.equals(confirm)) {
            return "Passwords do not match";
        }
        if (userService.userExists(username, email)) {
            return "Username or email already exists.";
        }
        String avatarEmoji = extractAvatarEmoji(avatarDisplay);
        return (avatarEmoji == null || avatarEmoji.isBlank()) ? "Please pick an avatar" : null;
    }

    /**
     * Switches to the registration panel.
     * @param event The action event.
     */
    @FXML
    private void switchToRegister(ActionEvent event) {
        togglePanels(true);
        clearMessage();
        /*registerWelcomeMessage.setVisible(true);
        registerWelcomeMessage.setManaged(true);
        loginWelcomeMessage.setVisible(false);
        loginWelcomeMessage.setManaged(false);*/
    }

    /**
     * Switches to the login panel.
     * @param event The action event.
     */
    @FXML
    private void switchToLogin(ActionEvent event) {
        togglePanels(false);
        clearMessage();
        /*loginWelcomeMessage.setVisible(true);
        loginWelcomeMessage.setManaged(true);
        registerWelcomeMessage.setVisible(false);
        registerWelcomeMessage.setManaged(false);*/
    }

    /**
     * Toggles visibility of login and registration panels.
     * @param showRegister True to show registration panel, false for login.
     */
    private void togglePanels(boolean showRegister) {
        if (loginForm != null) {
            loginForm.setVisible(!showRegister);
            loginForm.setManaged(!showRegister);
        }
        if (registerForm != null) {
            registerForm.setVisible(showRegister);
            registerForm.setManaged(showRegister);
        }
        if (loginWelcomeMessage != null) {
            loginWelcomeMessage.setVisible(!showRegister);
            loginWelcomeMessage.setManaged(!showRegister);
        }
        if (registerWelcomeMessage != null) {
            registerWelcomeMessage.setVisible(showRegister);
            registerWelcomeMessage.setManaged(showRegister);
        }
    }

    /**
     * Extracts text from a TextField or PasswordField, handling null cases.
     * @param field The TextField or PasswordField.
     * @return The text content, or empty string if null.
     */
    private String text(javafx.scene.control.TextInputControl field) {
        return field != null ? field.getText() : "";
    }

    /**
     * Extracts the emoji from the avatar display text.
     * @param avatarDisplay The avatar display text.
     * @return The emoji or empty string if invalid.
     */
    private static String extractAvatarEmoji(String avatarDisplay) {
        if (avatarDisplay == null || avatarDisplay.isBlank()) return "";
        int idx = avatarDisplay.indexOf(' ');
        return (idx > 0) ? avatarDisplay.substring(0, idx) : avatarDisplay;
    }

    /**
     * Clears the registration form fields.
     */
    private void clearRegisterFields() {
        if (registerUsernameField != null) registerUsernameField.clear();
        if (registerEmailField != null) registerEmailField.clear();
        if (registerPasswordField != null) registerPasswordField.clear();
        if (confirmPasswordField != null) confirmPasswordField.clear();
        if (avatarCombo != null) avatarCombo.setValue(null);
        if (roleCombo != null) roleCombo.setValue(null);
    }

    /**
     * Displays an error message.
     * @param msg The error message.
     */
    private void displayError(String msg) {
        if (messageLabel != null) {
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            messageLabel.setText(msg);
        }
    }

    /**
     * Displays a success message.
     * @param msg The success message.
     */
    private void displaySuccess(String msg) {
        if (messageLabel != null) {
            messageLabel.setStyle("-fx-text-fill: #16a34a; -fx-font-size: 14px;");
            messageLabel.setText(msg);
        }
    }

    /**
     * Clears the message label.
     */
    private void clearMessage() {
        if (messageLabel != null) messageLabel.setText("");
    }

    /**
     * Implements continent-specific setup (required by BaseController).
     * No-op for this controller.
     * @param continentName The name of the continent.
     */
    @Override
    protected void setupContinent(String continentName) {
        // No continent-specific setup needed
    }

    /**
     * Sets profile data for the controller.
     * @param username The username to set.
     * @param avatar   The avatar to set.
     */
    @Override
    public void setProfileData(String username, String avatar) {
        // No-op for this controller
    }

    /**
     * Handles session update notifications.
     * @param username The new username.
     * @param avatar The new avatar.
     */
    @Override
    public void onSessionUpdated(String username, String avatar) {
        // Update UI if needed
    }

    /**
     * Handles session clear notifications.
     */
    @Override
    public void onSessionCleared() {
        clearMessage();
        togglePanels(false);
    }
}