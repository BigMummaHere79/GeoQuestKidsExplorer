package com.example.geoquestkidsexplorer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileCreatedController {

    @FXML
    private Label explorerNameLabel;
    @FXML
    private Label explorerAvatarLabel;

    private String explorerName;
    private String selectedAvatar;
    private Stage stage; // Add this field

    /**
     * This method is called by the previous controller to set the user's data.
     * @param name   the explorer’s display name
     * @param avatar the avatar emoji selected by the explorer
     */
    public void setProfileData(String name, String avatar) {
        this.explorerName = name;
        this.selectedAvatar = avatar;
        explorerNameLabel.setText(this.explorerName);
        explorerAvatarLabel.setText(this.selectedAvatar);
    }

    // Add a method to receive the stage from the previous controller
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles the "Start Adventure" button action.
     * This method will transition to the home page, passing profile data.
     *
     * @param event The ActionEvent from the button click.
     * @throws IOException If the FXML file cannot be loaded.
     */
    @FXML
    private void startAdventure(ActionEvent event) throws IOException {
        // Create the loader for the next scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
        Parent root = loader.load();

        // Get the controller of the new scene and pass the profile data to it
        HomePageController homePageController = loader.getController();
        homePageController.setProfileData(explorerName, selectedAvatar);

        // Get the stage from the current event's source node
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        // Set the new scene on the stage and show it
        stage.setScene(scene);
        stage.show();
    }
}

