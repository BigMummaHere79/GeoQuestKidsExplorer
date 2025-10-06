package com.example.geoquestkidsexplorer.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class FunFactsController {

    /**
     * Unified handler for continent clicks. Determines which continent was clicked and loads the corresponding facts screen.
     */
    @FXML
    private void loadContinentFacts(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        if (source instanceof VBox) {
            VBox continentBox = (VBox) source;
            Node nameNode = continentBox.getChildren().get(1);
            if (nameNode instanceof Label) {
                String continent = ((Label) nameNode).getText();

                String fxmlPath = null;
                boolean isMultiCountry = false;

                // Check for single-tile continents (Antarctica)
                if ("Antarctica".equals(continent)) {
                    fxmlPath = "/com/example/geoquestkidsexplorer/antarcticafacts.fxml";
                }
                // Check for multi-country continents (Africa, Asia, Europe, N/S America, Oceania)
                else if ("Africa".equals(continent) || "Asia".equals(continent) || "Europe".equals(continent) ||
                        "North America".equals(continent) || "South America".equals(continent) || "Oceania".equals(continent)) {
                    fxmlPath = "/com/example/geoquestkidsexplorer/countriesfunfacts.fxml";
                    isMultiCountry = true;
                } else {
                    System.out.println("No facts view configured for " + continent + ".");
                    return; // Stop if no FXML is found
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Parent root = loader.load();

                // If it's the general screen, pass the continent name to populate the tiles
                if (isMultiCountry) {
                    CountriesFunFactsController controller = loader.getController();
                    controller.setContinent(continent);
                }

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }
}
