/**
 * SidebarController handles the collapsible sidebar navigation.
 */
package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SidebarController {

    @FXML
    private Label avatarLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label subWelcomeLabel;

    // A private field to hold the explorer's avatar string
    private String explorerAvatar;
    private String explorerName;

    @FXML
    private VBox sidebar;

    @FXML
    private Button homeButton;

    @FXML
    private Button funFactsButton;

    @FXML
    private Button myProgressButton;

    @FXML
    private Button myAchievementsButton;

    private boolean isExpanded = false;
    private TranslateTransition transition;

    /**
     * Initializes the controller. Sets up the sidebar to collapse by default.
     */
    @FXML
    public void initialize() {
        sidebar.setPrefWidth(50.0); // Start
        sidebar.setTranslateX(0.0); // Start at the left edge
        transition = new TranslateTransition(Duration.millis(300), sidebar);
        sidebar.setOnMouseEntered(event -> expandSidebar());
        sidebar.setOnMouseExited(event -> collapseSidebar());
    }

    /**
     * Expands the sidebar by sliding it out (overlaying content).
     */
    @FXML
    private void expandSidebar() {
        if (!isExpanded) {
            isExpanded = true;
            sidebar.setPrefWidth(200.0); // Expand to 200px
            homeButton.setText("Dashboard");
            funFactsButton.setText("Fun Facts Library");
            myProgressButton.setText("My Progress");
            myAchievementsButton.setText("My Achievements");
        }
    }

    /**
     * Collapses the sidebar by sliding it back.
     */
    @FXML
    private void collapseSidebar() {
        if (isExpanded) {
            isExpanded = false;
            sidebar.setPrefWidth(50.0); // Collapse to 50px
            homeButton.setText("\uD83C\uDFE0");
            funFactsButton.setText("\uD83D\uDCDA");
            myProgressButton.setText("\uD83D\uDC64");
            myAchievementsButton.setText("\uD83C\uDFC6");
        }
    }

    /**
     * Navigates to the home page (dashboard).
     */
    @FXML
    private void navigateToHome(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/geoquestkidsexplorer/homepage.fxml", true, this  );
    }

    /**
     * Opens the Fun Facts Library.
     */
    @FXML
    private void openFunFacts(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/geoquestkidsexplorer/funfacts.fxml", false, this );
    }

    /**
     * Opens the My Progress page.
     */
    @FXML
    private void openMyProgress(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/userprogress.fxml"));
        Parent root = loader.load();
        UserProgressController progressController = loader.getController();
        // Pass profile data (retrieve from database)
        //int userId = UserSession.getUserId();
        String username = UserSession.getUsername();
        String avatar = UserSession.getAvatar();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT username, avatar FROM users WHERE username = ?")) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                explorerName = rs.getString("username");
                avatar = rs.getString("avatar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int continentsUnlocked = 1; // Placeholder
        int perfectScores = 0; // Placeholder
        int correctAnswers = 0; // Placeholder
        progressController.setProfileData(explorerName, avatar, continentsUnlocked, perfectScores, correctAnswers);
        loadScene(event, root);
    }

    /**
     * Opens My Achievements page.
     */
    @FXML
    private void openMyAchievements(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/geoquestkidsexplorer/myachievements.fxml", false, this   );
    }

    /**
     * Loads the specified scene into the current stage.
     */
    private void loadScene(ActionEvent event, String fxmlPath, boolean isHomePage, SidebarController sidebarController) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        // If it's the homepage, set profile data
        if (isHomePage) {
            HomePageController homeController = loader.getController();
            //int userId = UserSession.getUserId();
            String username = UserSession.getUsername();
            String avatar = UserSession.getAvatar();
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT explorer_name, avatar FROM users WHERE username = ?")) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    username = rs.getString("explorer_name");
                    avatar = rs.getString("avatar");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            homeController.setProfileData(username, avatar);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200.0, 800.0); // Fixed size
        stage.setScene(scene);
        stage.setResizable(false); // Prevent resizing
        stage.show();
        // Use the passed sidebarController to maintain state
        if (sidebarController != null) {
            if (sidebarController.isExpanded) {
                sidebarController.expandSidebar();
            } else {
                sidebarController.collapseSidebar();
            }
        }
    }

    /**
     * Loads the specified scene root into the current stage.
     */
    private void loadScene(ActionEvent event, Parent root) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method is called by the previous controller to set the user's data.
     * We pass both the name and the avatar to display on the home screen.
     */
    public void setProfileData(String username, String avatar) {
        this.explorerName = username;
        this.explorerAvatar = avatar;

        // Update the avatar and welcome message
        avatarLabel.setText(this.explorerAvatar);
        welcomeLabel.setText("Welcome back, " + this.explorerName + "!");
    }
}