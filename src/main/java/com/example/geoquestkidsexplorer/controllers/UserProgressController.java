package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the user progress UI.
 * Displays user profile information such as level, score, and completed continents.
 * Extends BaseController for shared functionality like stage management and continent color application.
 */
public class UserProgressController extends BaseController {

    @FXML private Button avatarButton;
    @FXML private Label avatarLabel;
    @FXML private Label welcomeLabel;
    @FXML private Label continentsUnlockedLabel;
    @FXML private Label levelsCompletedLabel;
    @FXML private Label perfectScoresLabel;
    @FXML private Label correctAnswersLabel;
    @FXML private Label levelsCompletedTileLabel;
    @FXML private Label continentsUnlockedTileLabel;

    private String username;
    private String avatar;

    /**
     * Sets the stage for this controller.
     *
     * @param stage The JavaFX stage.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the user progress UI with profile data.
     */
    @FXML
    public void initialize() {
        setAvatarOnUI(avatar != null ? avatar : UserSession.getAvatar() != null ? UserSession.getAvatar() : "🙂");
        if (username != null && !username.isBlank()) {
            if (welcomeLabel != null) {
                welcomeLabel.setText(username);
            }
            loadAndRenderProgress(username);
        } else {
            System.err.println("initialize: No logged-in username, using defaults");
            if (welcomeLabel != null) {
                welcomeLabel.setText("Welcome, Explorer!");
            }
            loadAndRenderProgress(null);
        }
        // Apply default continent colors (e.g., Oceania for consistency)
        applyContinentColors();
    }

    /**
     * Sets profile data for the controller.
     *
     * @param username The username to set.
     * @param avatar   The avatar to set.
     */
    @Override
    public void setProfileData(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        UserSession.setUser(username, avatar);
        initialize();
    }

    /**
     * Implements continent-specific setup.
     * Applies default colors (e.g., Oceania) as this page is not continent-specific.
     *
     * @param continentName The name of the continent (unused).
     */
    @Override
    protected void setupContinent(String continentName) {
        applyContinentColors();
    }

    /**
     * Applies continent-specific colors to UI elements (default to Oceania).
     */
    private void applyContinentColors() {
        Map<String, Node> nodes = new HashMap<>();
        if (welcomeLabel != null) nodes.put("welcomeLabel", welcomeLabel);
        if (avatarButton != null) nodes.put("avatarButton", avatarButton);
        if (avatarLabel != null) nodes.put("avatarLabel", avatarLabel);
        applyContinentColors("Oceania", nodes);
    }

    /**
     * Navigates to the profile creation page to change the avatar.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void handleChangeAvatar(ActionEvent event) throws IOException {
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/profilecreation.fxml",
                (ProfileCreationController controller) -> {
                    controller.setProfileData(username, avatar);
                    controller.setStage(stage);
                });
    }

    /**
     * Handles logout, clears UserSession, and navigates to the login page.
     *
     * @param event The action event triggered by the button click.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void handleLogoutButtonAction(ActionEvent event) throws IOException {
        UserSession.clear();
        NavigationHelper.loadScene((Node) event.getSource(), "/com/example/geoquestkidsexplorer/loginview.fxml");
    }

    /**
     * Sets the avatar on the UI, updating the avatar button or label.
     *
     * @param emoji The avatar emoji to display.
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
     * Loads and renders the user's progress data.
     *
     * @param username The username of the currently logged-in user.
     */
    private void loadAndRenderProgress(String username) {
        int continentsUnlocked = 1;
        int perfectScores = 0;
        int correctAnswers = 0;

        if (username != null && !username.isBlank()) {
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
                            System.err.println("loadAndRenderProgress: NULL/invalid level for " + username + " → setting 1");
                            updateUserLevel(username, 1);
                            continentsUnlocked = 1;
                        }
                    } else {
                        System.err.println("loadAndRenderProgress: No user row for " + username + "; leaving level=1");
                    }
                }
            } catch (SQLException e) {
                System.err.println("loadAndRenderProgress level error: " + e.getMessage());
            }

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
                System.err.println("loadAndRenderProgress results error: " + e.getMessage());
            }
        }

        int totalLevelsCompleted = correctAnswers;

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
     * Updates the user's level in the database.
     *
     * @param username The username.
     * @param level    The new level.
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