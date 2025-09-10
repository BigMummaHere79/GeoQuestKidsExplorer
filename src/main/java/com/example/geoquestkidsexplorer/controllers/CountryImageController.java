package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class CountryImageController {

    @FXML private Label countryNameLabel;
    @FXML private ImageView countryImageView;

    private Stage stage;

    // Called by the parent controller when opening this page
    public void setCountry(String countryName, Stage stage) {
        this.stage = stage;
        countryNameLabel.setText(countryName);

        Image img = DatabaseManager.getCountryImage(countryName);
        if (img != null) {
            countryImageView.setImage(img);
        } else {
            countryNameLabel.setText(countryName + " (no image found)");
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        // simply close this window
        if (stage != null) {
            stage.close();
        }
    }
}
