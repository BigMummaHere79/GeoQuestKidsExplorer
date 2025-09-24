package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProgressController {
    @FXML
    private Label avatarLabel;
    @FXML
    private Label continentsUnlockedLabel;
    @FXML
    private Label levelsCompletedLabel;
    @FXML
    private Label perfectScoresLabel;
    @FXML
    private Label correctAnswersLabel;
    @FXML
    private Label levelsCompletedTileLabel;
    @FXML
    private Label continentsUnlockedTileLabel;

    /**
     * Sets the profile data (avatar and stats) received from the main controller.
     * This method is called by the MainController when the user progress page is loaded.
     *
     * @param explorerAvatar The user's selected avatar emoji.
     * @param explorerName The user's username.
     */
    public void setProfileData(String explorerName, String explorerAvatar) {
        avatarLabel.setText(explorerAvatar != null ? explorerAvatar : "🙂");

        // Initialize progress data
        int continentsUnlocked = 1; // Default to Antarctica
        int perfectScores = 0;
        int correctAnswers = 0;

        if (explorerName == null || explorerName.isEmpty()) {
            System.err.println("setProfileData: No user logged in (username is null or empty), defaulting to level 1");
        } else {
            // Get continents unlocked (user's level)
            String levelSql = "SELECT level FROM users WHERE username = ?";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(levelSql)) {
                ps.setString(1, explorerName);
                System.out.println("setProfileData: Querying level for username: " + explorerName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int level = rs.getInt("level");
                        if (rs.wasNull() || level < 1) {
                            System.err.println("setProfileData: User " + explorerName + " has null or invalid level ("
                                    + (rs.wasNull() ? "NULL" : level) + "), updating to 1");
                            updateUserLevel(explorerName, 1); // Fix NULL or invalid level
                            continentsUnlocked = 1;
                        } else {
                            continentsUnlocked = level;
                            System.out.println("setProfileData: User " + explorerName + " found with level: " + level);
                        }
                    } else {
                        System.err.println("setProfileData: No user found for username: " + explorerName
                                + ", defaulting to level 1");
                    }
                }
            } catch (SQLException e) {
                System.err.println("setProfileData error: " + e.getMessage());
                e.printStackTrace();
            }

            // Get perfect scores and correct answers
            String resultsSql = "SELECT grades FROM results WHERE username = ? AND status = 'Pass'";
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(resultsSql)) {
                ps.setString(1, explorerName);
                System.out.println("setProfileData: Fetching results for username: " + explorerName);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        double score = rs.getDouble("grades");
                        if (score >= 100.0) {
                            perfectScores++;
                        }
                        correctAnswers++;
                        System.out.println("setProfileData: Found passing result with score: " + score);
                    }
                }
            } catch (SQLException e) {
                System.err.println("setProfileData error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        int totalLevelsCompleted = correctAnswers;
        continentsUnlockedLabel.setText("Continents Unlocked: " + continentsUnlocked + "/7");
        levelsCompletedLabel.setText("Total Levels Completed: " + totalLevelsCompleted + "/7");
        perfectScoresLabel.setText("" + perfectScores);
        correctAnswersLabel.setText("" + correctAnswers);
        levelsCompletedTileLabel.setText("" + totalLevelsCompleted);
        continentsUnlockedTileLabel.setText("" + continentsUnlocked);
        System.out.println("setProfileData: Updated UI - Continents Unlocked: " + continentsUnlocked + ", Perfect Scores: "
                + perfectScores + ", Correct Answers: " + correctAnswers);
    }

    /**
     * Updates the user's level in the database.
     */
    private void updateUserLevel(String username, int level) {
        String sql = "UPDATE users SET level = ? WHERE username = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, level);
            ps.setString(2, username);
            int rows = ps.executeUpdate();
            System.out.println("updateUserLevel: Updated level to " + level + " for user " + username + ", rows affected: " + rows);
            if (rows == 0) {
                System.err.println("updateUserLevel: No user found for username: " + username);
            }
        } catch (SQLException e) {
            System.err.println("updateUserLevel error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handles the logout action.
     * It switches the scene back to the main menu or login screen.
     *
     * @param event The ActionEvent from the button click.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @FXML
    private void handleLogoutButtonAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/loginview.fxml"));
            Scene scene = new Scene(root, 1200.0, 800.0); // Match the size used in SidebarController
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setResizable(false); // Prevent resizing
            stage.show();
            // Clear UserSession on logout
            UserSession.clear(); //this is clearing the user session data after loging out.
        } catch (IOException e) {
            System.err.println("Logout error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
