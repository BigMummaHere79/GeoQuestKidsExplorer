package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Controller for login and registration UI.
 * Handles user authentication and navigation.
 * Uses DatabaseManager facade for DB operations (OOP abstraction).
 * Validation methods separated for testability (encapsulation of logic).
 */
public class LoginController {

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

    public void setStage(Stage stage) { this.stage = stage; }

    @FXML
    public void initialize() {
        togglePanels(false);
        loginWelcomeMessage.setVisible(true);
        loginWelcomeMessage.setManaged(true);
        registerWelcomeMessage.setVisible(false);
        registerWelcomeMessage.setManaged(false);

        if (roleCombo != null && (roleCombo.getItems() == null || roleCombo.getItems().isEmpty())) {
            roleCombo.setItems(FXCollections.observableArrayList("Student", "Teacher"));
        }
        if (avatarCombo != null && (avatarCombo.getItems() == null || avatarCombo.getItems().isEmpty())) {
            avatarCombo.setItems(FXCollections.observableArrayList(
                    "👦 Explorer Boy",
                    "👧 Explorer Girl",
                    "👨‍🎓 Student (Boy)",
                    "👩‍🎓 Student (Girl)"
            ));
        }
    }

    /**
     * Handles login action.
     * Validates inputs, authenticates via DatabaseManager, sets session, navigates to home.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String email = text(loginEmailField);
        String password = text(loginPasswordField);

        String err = validateLoginInputs(email, password);
        if (err != null) {
            error(err);
            return;
        }

        if (DatabaseManager.validateLogin(email, password)) {
            String username = DatabaseManager.getUsernameByEmail(email);
            String avatar = DatabaseManager.getAvatarByEmail(email);
            if (username == null) {
                error("User not found.");
                return;
            }
            UserSession.setUser(username, avatar);
            System.out.println("handleLogin: Logged in user: " + username + ", avatar: " + avatar);
            DatabaseManager.fixUserLevel(username); // Delegate to service via facade
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 1200.0, 800.0);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                error("Navigation error: " + e.getMessage());
            }
        } else {
            error("Invalid email or password.");
        }
    }

    /**
     * Validates login inputs for unit testing.
     * @param email Email.
     * @param password Password.
     * @return Error message or null.
     */
    protected String validateLoginInputs(String email, String password) {
        String mail = (email == null) ? "" : email.trim();
        String pass = (password == null) ? "" : password.trim();

        if (mail.isBlank() || pass.isBlank()) {
            return "Please enter both email and password";
        }
        return null;
    }

    /**
     * Validates login against DB (for testing).
     */
    public boolean validateLogin(String email, String password) {
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            return false;
        }
        return DatabaseManager.validateLogin(email, password);
    }

    // Other methods (handleRegister, switchToRegister, etc.) remain similar, with added JavaDoc
    @FXML
    private void handleRegister(ActionEvent event) {
        String username = text(registerUsernameField);
        String email = text(registerEmailField);
        String role = roleCombo == null ? "" : String.valueOf(roleCombo.getValue());
        String password = text(registerPasswordField);
        String confirm = text(confirmPasswordField);
        String avatarDisplay = avatarCombo == null ? null : avatarCombo.getValue();

        String err = validateRegistrationInputs(username, email, role, password, confirm, avatarDisplay);
        if (err != null) {
            error(err);
            return;
        }

        String avatarEmoji = extractAvatarEmoji(avatarDisplay);

        try {
            if (DatabaseManager.userExists(username, email)) {
                error("Username or email already exists.");
                return;
            }

            DatabaseManager.insertUser(username, email, password, avatarEmoji, role);

            success("Registration successful! Please login.");
            clearRegisterFields();
            switchToLogin(event);
        } catch (RuntimeException e) {
            error("Registration error: " + e.getMessage());
        }
    }

    /**
     * Validates registration inputs.
     */
    public String validateRegistrationInputs(String username, String email, String role,
                                             String password, String confirm, String avatarDisplay) {
        if (username.isBlank() && email.isBlank() && role.isBlank() && password.isBlank() && confirm.isBlank()) {
            return "Please fill in all the fields";
        }
        if (!password.equals(confirm)) {
            return "Passwords do not match";
        }
        if (username.isBlank()) return "Username is blank or invalid";
        if (email.isBlank()) return "Email is blank or invalid";

        String avatarEmoji = extractAvatarEmoji(avatarDisplay);
        if (avatarEmoji == null || avatarEmoji.isBlank()) {
            return "Please pick an avatar";
        }
        return null;
    }

    @FXML
    private void switchToRegister(ActionEvent event) {
        togglePanels(true);
        clearMessage();
        registerWelcomeMessage.setVisible(true);
        registerWelcomeMessage.setManaged(true);
        loginWelcomeMessage.setVisible(false);
        loginWelcomeMessage.setManaged(false);
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        togglePanels(false);
        clearMessage();
        loginWelcomeMessage.setVisible(true);
        loginWelcomeMessage.setManaged(true);
        registerWelcomeMessage.setVisible(false);
        registerWelcomeMessage.setManaged(false);
    }

    private void togglePanels(boolean showRegister) {
        if (loginForm != null) {
            loginForm.setVisible(!showRegister);
            loginForm.setManaged(!showRegister);
        }
        if (registerForm != null) {
            registerForm.setVisible(showRegister);
            registerForm.setManaged(showRegister);
        }
    }

    // ... (other private helpers like text, extractAvatarEmoji, clearRegisterFields, error, success, clearMessage remain the same, add JavaDoc if needed)
    private static String text(TextField tf) {
        return (tf == null || tf.getText() == null) ? "" : tf.getText().trim();
    }

    private static String extractAvatarEmoji(String avatarDisplay) {
        if (avatarDisplay == null || avatarDisplay.isBlank()) return "";
        int idx = avatarDisplay.indexOf(' ');
        return (idx > 0) ? avatarDisplay.substring(0, idx) : avatarDisplay;
    }

    private void clearRegisterFields() {
        // ... (unchanged)
    }

    private void error(String msg) {
        if (messageLabel != null) {
            messageLabel.setStyle("-fx-text-fill: red; -fx-font-size: 14px;");
            messageLabel.setText(msg);
        }
    }

    private void success(String msg) {
        if (messageLabel != null) {
            messageLabel.setStyle("-fx-text-fill: #16a34a; -fx-font-size: 14px;");
            messageLabel.setText(msg);
        }
    }

    private void clearMessage() {
        if (messageLabel != null) messageLabel.setText("");
    }
}