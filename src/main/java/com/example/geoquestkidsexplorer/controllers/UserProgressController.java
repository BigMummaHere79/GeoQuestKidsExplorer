package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProgressController {

    // NEW: avatar as a button (preferred)
    @FXML private Button avatarButton;

    // Legacy: keep label field in case your FXML still has the old control
    @FXML private Label avatarLabel;
    @FXML private Label welcomeLabel;

    @FXML private Label continentsUnlockedLabel;
    @FXML private Label levelsCompletedLabel;
    @FXML private Label perfectScoresLabel;
    @FXML private Label correctAnswersLabel;
    @FXML private Label levelsCompletedTileLabel;
    @FXML private Label continentsUnlockedTileLabel;

    @FXML
    public void initialize() {
        // 1) Paint avatar from session onto whichever control exists
        String avatar = UserSession.getAvatar();
        setAvatarOnUI(avatar != null ? avatar : "🙂");

        // Get user data directly from the UserSession
        String username = UserSession.getUsername();
        System.out.println("HomePageController: Current username = " + username);
        String explorerAvatar = UserSession.getAvatar();

        if (username != null && !username.isEmpty()) {
            welcomeLabel.setText(username);
        } else {
            // This handles cases where no one is logged in
            welcomeLabel.setText("Welcome, Explorer!");
        }
        loadAndRenderProgress(username);
    }

    /* ---------------------- UI Handlers ---------------------- */

    /**
     * Navigates to the profile creation pahe where the user can change their avatar.
     *
     * @param event action event triggered by the button click.
     */
    @FXML
    private void handleChangeAvatar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/profilecreation.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Change avatar nav error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the logout button is clicked.
     * Navigates back to the login page and clears the user session.
     *
     * @param event The action event triggered by the button click.
     */
    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/loginview.fxml"));
            Scene scene = new Scene(root, 1200.0, 800.0);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            UserSession.clear();
        } catch (IOException e) {
            System.err.println("Logout error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* ---------------------- Back-compat hook ---------------------- */

    /**
     * Some parts of your app may still call this method directly.
     * We treat the first arg as *explorerName* (display name) and the second as the *avatar* emoji.
     * Sets the user's profile data, including the explorer's name and avatar.
     * Used for updating the profile information.
     *
     * @param explorerName The display name of the explorer.
     * @param explorerAvatar The avatar emoji representing the explorer.
     */
    public void setProfileData(String explorerName, String explorerAvatar) {
        if (explorerAvatar != null && !explorerAvatar.isBlank()) {
            UserSession.setAvatar(explorerAvatar);
            setAvatarOnUI(explorerAvatar);
        }

        // Recompute stats for current login user:
        loadAndRenderProgress(UserSession.getUsername());
    }

    /* ---------------------- Helpers ---------------------- */

    /**
     * Sets the avatar on the user interface, and updating the avatar button or label.
     * @param emoji The avatar emoji to be displayed.
     */
    private void setAvatarOnUI(String emoji) {
        if (avatarButton != null) {
            avatarButton.setText(emoji);
        }
        if (avatarLabel != null) {
            avatarLabel.setText(emoji);
        }
    }


    /**
     * Loads and renders the user's progress data, including the number of continents unlocked,
     * levels completed, perfect scores, and correct answers.
     * This method retrieves the data from the database and updates the UI labels accordingly.
     *
     * @param username The username of the currently logged-in user.
     */
    private void loadAndRenderProgress(String username) {
        int continentsUnlocked = 1; // default baseline
        int perfectScores = 0;
        int correctAnswers = 0;

        if (username == null || username.isBlank()) {
            System.err.println("UserProgress: No logged-in username; using defaults.");
        } else {
            // Level (continents unlocked)
            String levelSql = "SELECT level FROM users WHERE username = ?";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(levelSql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int level = rs.getInt("level");
                        if (!rs.wasNull() && level >= 1) {
                            continentsUnlocked = level;
                        } else {
                            System.err.println("UserProgress: NULL/invalid level for " + username + " → setting 1");
                            updateUserLevel(username, 1);
                            continentsUnlocked = 1;
                        }
                    } else {
                        System.err.println("UserProgress: No user row for " + username + "; leaving level=1");
                    }
                }
            } catch (SQLException e) {
                System.err.println("UserProgress level error: " + e.getMessage());
            }

            // Results → perfectScores & correctAnswers
            String resultsSql = "SELECT grades FROM results WHERE username = ? AND status = 'Pass'";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(resultsSql)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        double score = rs.getDouble("grades");
                        if (score >= 100.0) perfectScores++;
                        correctAnswers++;
                    }
                }
            } catch (SQLException e) {
                System.err.println("UserProgress results error: " + e.getMessage());
            }
        }

        int totalLevelsCompleted = correctAnswers;

        // Render to UI
        if (continentsUnlockedLabel != null)
            continentsUnlockedLabel.setText("Continents Unlocked: " + continentsUnlocked + "/7");
        if (levelsCompletedLabel != null)
            levelsCompletedLabel.setText("Total Levels Completed: " + totalLevelsCompleted + "/7");
        if (perfectScoresLabel != null)
            perfectScoresLabel.setText(Integer.toString(perfectScores));
        if (correctAnswersLabel != null)
            correctAnswersLabel.setText(Integer.toString(correctAnswers));
        if (levelsCompletedTileLabel != null)
            levelsCompletedTileLabel.setText(Integer.toString(totalLevelsCompleted));
        if (continentsUnlockedTileLabel != null)
            continentsUnlockedTileLabel.setText(Integer.toString(continentsUnlocked));
    }


    /**
     * Updates the user's level in the database to ensure it is never null or invalid.
     *
     * @param username The username of the user whose level is to be updated.
     * @param level The new level to be set for the user.
     */

    private void updateUserLevel(String username, int level) {
        String sql = "UPDATE users SET level = ? WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, level);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("updateUserLevel error: " + e.getMessage());
        }
    }
}
