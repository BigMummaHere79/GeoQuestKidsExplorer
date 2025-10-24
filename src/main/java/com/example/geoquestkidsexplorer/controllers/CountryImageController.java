package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

/**
 * Sets and handles Country images
 * */

public class CountryImageController {

    @FXML private Label countryNameLabel;
    @FXML private ImageView countryImageView;


    private Stage stage;

    /**
     * Sets the country name and displays its image
     * Called by the parent controller when opening this page/window.
     * @param countryName the name of the country to display
     * @param stage the Stage (window) in which this controller is shown
     */
    public void setCountry(String countryName, Stage stage) {
        this.stage = stage;
        countryNameLabel.setText(countryName);

        Image img = DatabaseManager.getInstance().getCountryImage(countryName);
        if (img != null) {
            countryImageView.setImage(img);
        } else {
            countryNameLabel.setText(countryName + " (no image found)");
        }
    }

    /**
     * Handles the “Back” button click event.
     * Closes the current window (stage) if available.
     * @param event the ActionEvent triggered by clicking the Back button
     */
    @FXML
    private void handleBack(ActionEvent event) {
        // simply close this window
        if (stage != null) {
            stage.close();
        }
    }
}
