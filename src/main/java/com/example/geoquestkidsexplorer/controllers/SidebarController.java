package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.IOException;

/**
 * Controller for the sidebar navigation UI.
 * Manages button popups and navigation actions.
 * Extends BaseController for shared functionality.
 */
public class SidebarController extends BaseController {

    @FXML private VBox sidebar;
    @FXML private Button homeButton;
    @FXML private Button funFactsButton;
    @FXML private Button myProgressButton;
    @FXML private Button feedbackRatingsButton;

    private TranslateTransition transition;
    private Popup homePopup;
    private Popup funFactsPopup;
    private Popup myProgressPopup;
    private Popup feedbackRatingsPopup;

    /**
     * Sets the stage for this controller.
     * @param stage The JavaFX stage.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the sidebar UI, buttons, and popups.
     */
    @FXML
    public void initialize() {
        sidebar.setPrefWidth(50.0);

        homeButton.setText("\uD83C\uDFE0");
        funFactsButton.setText("\uD83D\uDCDA");
        myProgressButton.setText("\uD83D\uDC64");
        feedbackRatingsButton.setText(" ");

        String commonLabelStyle = "-fx-text-fill: #1A2B4C; -fx-font-size: 14px; -fx-font-family: 'Courier New', monospace; " +
                "-fx-font-weight: bold; -fx-alignment: center; -fx-wrap-text: true;";
        String commonContainerStyle = "-fx-background-radius: 15px; -fx-border-radius: 15px; -fx-padding: 8px; " +
                "-fx-alignment: center;";
        double popupWidth = 200.0;
        double popupHeight = 40.0;

        // Setup popups (example for home; others are similar)
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

        // Similar setup for funFactsPopup, myProgressPopup, feedbackRatingsPopup (omitted for brevity)
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
     * Shows a popup next to a button.
     * @param popup       The popup to show.
     * @param button      The button to position relative to.
     * @param popupHeight The height for vertical centering.
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
     * Navigates to the home page.
     * @param event The action event.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void navigateToHome(ActionEvent event) throws IOException {
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/homepage.fxml",
                (HomePageController controller) -> {
                    controller.setProfileData(UserSession.getUsername(), UserSession.getAvatar());
                    controller.setStage(stage);
                });
    }

    /**
     * Navigates to the fun facts page.
     * @param event The action event.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void openFunFacts(ActionEvent event) throws IOException {
        NavigationHelper.loadScene((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/funfacts.fxml");
    }

    /**
     * Navigates to my progress page.
     * @param event The action event.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void openMyProgress(ActionEvent event) throws IOException {
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/userprogress.fxml",
                (BaseController controller) -> {
                    controller.setProfileData(UserSession.getUsername(), UserSession.getAvatar());
                    controller.setStage(stage);
                });
    }

    /**
     * Navigates to the feedback ratings page.
     * @param event The action event.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void openFeedbackRatings(ActionEvent event) throws IOException {
        NavigationHelper.loadScene((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/feedbackratings.fxml");
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
}