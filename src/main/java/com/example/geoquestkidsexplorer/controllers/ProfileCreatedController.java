package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the profile created confirmation UI.
 * Displays the created profile and navigates to the home page.
 * Extends BaseController for shared functionality.
 */
public class ProfileCreatedController extends BaseController {

    @FXML private Label explorerNameLabel;
    @FXML private Label explorerAvatarLabel;

    private String explorerName;
    private String selectedAvatar;

    /**
     * Sets the stage for this controller.
     * @param stage The JavaFX stage.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Sets the profile data for display.
     * @param username The explorer's username.
     * @param avatar   The selected avatar emoji.
     */
    @Override
    public void setProfileData(String username, String avatar) {
        this.explorerName = username;
        this.selectedAvatar = avatar;
        explorerNameLabel.setText(this.explorerName);
        explorerAvatarLabel.setText(this.selectedAvatar);
    }

    /**
     * Handles the start adventure button, navigating to the home page.
     * @param event The action event triggered by the button.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void startAdventure(ActionEvent event) throws IOException {
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/homepage.fxml",
                (HomePageController controller) -> {
                    controller.setProfileData(explorerName, selectedAvatar);
                    controller.setStage(stage);
                });
    }

    /**
     * Implements continent-specific setup (required by BaseController).
     * No-op for this controller as it doesn't use continent-specific UI.
     * @param continentName The name of the continent.
     */
    @Override
    protected void setupContinent(String continentName) {
        // No continent-specific setup needed
    }
}