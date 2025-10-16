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
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SidebarController {

    @FXML
    private VBox sidebar;
    @FXML
    private Button homeButton;
    @FXML
    private Button funFactsButton;
    @FXML
    private Button myProgressButton;
    @FXML
    private Button feedbackRatingsButton;

    private TranslateTransition transition;
    private Popup homePopup;
    private Popup funFactsPopup;
    private Popup myProgressPopup;
    private Popup feedbackRatingsPopup;

    /**
     * Initializes the controller. Sets up the sidebar to collapse by default.
     */
    @FXML
    public void initialize() {
        sidebar.setPrefWidth(50.0); // Start

        // Clear text for all buttons to show only icons
        homeButton.setText("\uD83C\uDFE0");
        funFactsButton.setText("\uD83D\uDCDA");
        myProgressButton.setText("\uD83D\uDC64");
        feedbackRatingsButton.setText(" ");

        // Common label style
        String commonLabelStyle = "-fx-text-fill: #1A2B4C; " +
                "-fx-font-size: 14px; " +
                "-fx-font-family: 'Courier New', monospace; " +
                "-fx-font-weight: bold; " +
                "-fx-alignment: center; " + // Center text in label
                "-fx-wrap-text: true;";

        // Common container style (excluding background color)
        String commonContainerStyle = "-fx-background-radius: 15px; " +
                "-fx-border-radius: 15px; " +
                "-fx-padding: 8px; " +
                "-fx-alignment: center;";

        // Fixed size for all containers
        double popupWidth = 200.0;
        double popupHeight = 40.0;

        // Home popup
        Label homeLabel = new Label("Dashboard");
        homeLabel.setStyle(commonLabelStyle);
        homeLabel.setPrefWidth(popupWidth);
        HBox homeContainer = new HBox(homeLabel);
        homeContainer.setStyle(commonContainerStyle + "-fx-background-color: #e1bee7;");
        homeContainer.setPrefWidth(popupWidth);
        homeContainer.setPrefHeight(popupHeight);
        homePopup = new Popup();
        homePopup.getContent().add(homeContainer);
        homeButton.setOnMouseEntered(event -> showPopup(homePopup, homeButton, popupHeight));
        homeButton.setOnMouseExited(event -> homePopup.hide());

        // Fun Facts popup
        Label funFactsLabel = new Label("Fun Facts Libraries");
        funFactsLabel.setStyle(commonLabelStyle);
        funFactsLabel.setPrefWidth(popupWidth);
        HBox funFactsContainer = new HBox(funFactsLabel);
        funFactsContainer.setStyle(commonContainerStyle + "-fx-background-color: #ffcdd2;");
        funFactsContainer.setPrefWidth(popupWidth);
        funFactsContainer.setPrefHeight(popupHeight);
        funFactsPopup = new Popup();
        funFactsPopup.getContent().add(funFactsContainer);
        funFactsButton.setOnMouseEntered(event -> showPopup(funFactsPopup, funFactsButton, popupHeight));
        funFactsButton.setOnMouseExited(event -> funFactsPopup.hide());

        // My Progress popup
        Label myProgressLabel = new Label("My Progress");
        myProgressLabel.setStyle(commonLabelStyle);
        myProgressLabel.setPrefWidth(popupWidth);
        HBox myProgressContainer = new HBox(myProgressLabel);
        myProgressContainer.setStyle(commonContainerStyle + "-fx-background-color: #b3e5fc;");
        myProgressContainer.setPrefWidth(popupWidth);
        myProgressContainer.setPrefHeight(popupHeight);
        myProgressPopup = new Popup();
        myProgressPopup.getContent().add(myProgressContainer);
        myProgressButton.setOnMouseEntered(event -> showPopup(myProgressPopup, myProgressButton, popupHeight));
        myProgressButton.setOnMouseExited(event -> myProgressPopup.hide());

        // Feedback Ratings popup
        Label feedbackRatingsLabel = new Label("Feedback and Ratings");
        feedbackRatingsLabel.setStyle(commonLabelStyle);
        feedbackRatingsLabel.setPrefWidth(popupWidth);
        HBox feedbackRatingsContainer = new HBox(feedbackRatingsLabel);
        feedbackRatingsContainer.setStyle(commonContainerStyle + "-fx-background-color: #FCE4EC;");
        feedbackRatingsContainer.setPrefWidth(popupWidth);
        feedbackRatingsContainer.setPrefHeight(popupHeight);
        feedbackRatingsPopup = new Popup();
        feedbackRatingsPopup.getContent().add(feedbackRatingsContainer);
        feedbackRatingsButton.setOnMouseEntered(event -> showPopup(feedbackRatingsPopup, feedbackRatingsButton, popupHeight));
        feedbackRatingsButton.setOnMouseExited(event -> feedbackRatingsPopup.hide());
    }


    /**
     * Shows the popup to the right of the button, centered vertically.
     *
     * @param popup  the Popup to be shown
     * @param button the Button beside which the popup appears
     * @param popupHeight the height of the popup, used to center it vertically
     */
    private void showPopup(Popup popup, Button button, double popupHeight) {
        if (popup.isShowing()) {
            popup.hide();
        }
        Window window = button.getScene().getWindow();
        Point2D buttonScreenPos = button.localToScreen(0, 0);
        double screenX = buttonScreenPos.getX() + button.getWidth() + 10;
        double screenY = buttonScreenPos.getY() + (button.getHeight() - popupHeight) / 2;
        popup.show(window, screenX, screenY);
    }

    /**
     * Navigates to the homepage
     *
     * @param event the ActionEvent triggered by clicking the home button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void navigateToHome(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/geoquestkidsexplorer/homepage.fxml", true);
    }

    /**
     * navigates to the Fun Facts page .
     *
     * @param event the ActionEvent triggered by clicking the fun facts button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void openFunFacts(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/geoquestkidsexplorer/funfacts.fxml", false);
    }

    /**
     * navigates to the My Progress page correspodning to user logged in and passes profile data to its controller.
     *
     * @param event the ActionEvent triggered by clicking the my progress button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void openMyProgress(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/userprogress.fxml"));
        Parent root = loader.load();
        UserProgressController progressController = loader.getController();
        String username = UserSession.getUsername();
        String avatar = UserSession.getAvatar();
        progressController.setProfileData(username, avatar);
        loadScene(event, root);
    }


    /**
     * naviagtes to the Feedback Ratings page.
     *
     * @param event the ActionEvent triggered by clicking the feedback ratings button
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void openFeedbackRatings(ActionEvent event) throws IOException {
        loadScene(event, "/com/example/geoquestkidsexplorer/feedbackratings.fxml", false);
    }


    /**
     * Loads the specified scene into the current stage.     *
     * @param event     the ActionEvent triggering the scene switch
     * @param fxmlPath  the path to the FXML file to load
     * @param isHomePage whether the target scene is the home/dashboard view
     * @throws IOException if loading the FXML fails
     */
    private void loadScene(ActionEvent event, String fxmlPath, boolean isHomePage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        // If it's the homepage, set profile data
        if (isHomePage) {
            HomePageController homeController = loader.getController();
            //int userId = UserSession.getUserId();
            String username = UserSession.getUsername();
            String avatar = UserSession.getAvatar();
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("SELECT username, avatar FROM users WHERE username = ?")) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    username = rs.getString("username");
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
    }

    /**
     * Loads the specified scene root into the current stage.
     *
     * @param event the ActionEvent triggering the scene switch
     * @param root  the root Parent node already loaded
     * @throws IOException (not typically thrown here) if handling fails
     */
    private void loadScene(ActionEvent event, Parent root) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }
}