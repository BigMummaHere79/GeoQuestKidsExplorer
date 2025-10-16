package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.data.ContinentFactory;
import com.example.geoquestkidsexplorer.models.Continents;
import com.example.geoquestkidsexplorer.models.Country;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class CountriesFunFactsController {

    @FXML private FlowPane countryTilesContainer;
    @FXML private Label continentFactsWelcomeLabel;
    @FXML private VBox factsContainer;

    private Continents currentContinent;

    public void setContinent(String continentName) {
        this.currentContinent = ContinentFactory.getContinent(continentName);
        if (currentContinent == null || currentContinent.getName().isEmpty()) {
            System.err.println("Continent name is null or empty. Skipping load.");
            continentFactsWelcomeLabel.setText("Error: No continent selected.");
            return;
        }
        continentFactsWelcomeLabel.setText("Fun Facts for " + currentContinent.getName() + " Continent!");
        loadContinentCountryTiles();
    }

    private void loadContinentCountryTiles() {
        if (currentContinent == null || currentContinent.getCountries() == null) {
            System.err.println("Error: No countries available for " + currentContinent.getName());
            countryTilesContainer.getChildren().clear();
            countryTilesContainer.getChildren().add(new Label("No country data available."));
            return;
        }

        countryTilesContainer.getChildren().clear();
        for (Country country : currentContinent.getCountries()) {
            VBox countryBox = createCountryTile(country);
            countryTilesContainer.getChildren().add(countryBox);
        }

        factsContainer.getChildren().clear();
        Label promptLabel = new Label("Select a country from " + currentContinent.getName() + " to see facts!");
        promptLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #3b82f6; -fx-padding: 20px;");
        promptLabel.setAlignment(Pos.CENTER);

        // Create a VBox to hold the image, similar to countryBox
        VBox imageBox = new VBox(15);
        imageBox.setAlignment(Pos.CENTER);
        imageBox.setPrefHeight(350.0);
        imageBox.setPrefWidth(225.0);
        imageBox.setStyle("-fx-background-color: " + currentContinent.getBackgroundColor() + "; -fx-background-radius: 45px; " +
                "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");

        // Create ImageView for the continent image
        ImageView continentImageView = new ImageView();
        continentImageView.setFitWidth(400.0); //520
        continentImageView.setFitHeight(400.0); //410
        continentImageView.setPreserveRatio(false); // Match homepage: stretch to fill
        continentImageView.setStyle("-fx-background-color: transparent;"); // Prevent black border

        // Apply a rounded rectangle clip to the ImageView
        Rectangle clip = new Rectangle(400.0, 400.0); //500 350
        clip.setArcHeight(85.0);
        clip.setArcWidth(85.0);
        continentImageView.setClip(clip);

        String imagePath = getContinentImagePath(currentContinent.getName());
        try {
            Image continentImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            continentImageView.setImage(continentImage);
            continentImageView.setSmooth(true); // Improve rendering quality
        } catch (Exception e) {
            System.err.println("ERROR: Could not load continent image for " + currentContinent.getName() + ". Path: " + imagePath);
            continentImageView.setImage(null); // Clear image if loading fails
        }
        imageBox.getChildren().add(continentImageView);

        // Add promptLabel and imageBox to factsContainer (image below label)
        factsContainer.getChildren().addAll(promptLabel, imageBox);
    }

    private String getContinentImagePath(String continentName) {
        String basePath = "/com/example/geoquestkidsexplorer/images/";
        return switch (continentName.toLowerCase()) {
            case "africa" -> basePath + "african.png";
            case "asia" -> basePath + "asian.png";
            case "oceania" -> basePath + "australian.png";
            case "europe" -> basePath + "european.png";
            case "north america" -> basePath + "north-american.png";
            case "south america" -> basePath + "south-american.png";
            default -> basePath + "default.png"; // Fallback image (optional, create if needed)
        };
    }

    private VBox createCountryTile(Country country) {
        VBox countryBox = new VBox(5);
        countryBox.getStyleClass().add("countries-tile-container");
        countryBox.setAlignment(Pos.CENTER);
        countryBox.setPrefHeight(200.0);
        countryBox.setPrefWidth(200.0);
        countryBox.setStyle("-fx-background-color: " + currentContinent.getBackgroundColor() + "; -fx-background-radius: 15px; " +
                "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 0);");
        countryBox.setOnMouseClicked(event -> loadCountryFacts(country));

        String imagePath = "/com/example/geoquestkidsexplorer/" + currentContinent.getFlagDirectory() + country.getFlagFileName();
        ImageView flagImageView;
        try {
            Image flagImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            flagImageView = new ImageView(flagImage);
            flagImageView.setFitWidth(100.0);
            flagImageView.setFitHeight(65.0);
            flagImageView.setStyle("-fx-padding: 10px;");
        } catch (Exception e) {
            System.err.println("ERROR: Could not load image for " + country.getName() + ". Path: " + imagePath);
            Label errorLabel = new Label("🚩");
            errorLabel.setStyle("-fx-font-size: 50px; -fx-padding: 10px;");
            countryBox.getChildren().addAll(errorLabel, new Label(country.getName()));
            return countryBox;
        }

        Label nameLabel = new Label(country.getName());
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        countryBox.getChildren().addAll(flagImageView, nameLabel);
        return countryBox;
    }

    private void loadCountryFacts( Country country) {
        factsContainer.getChildren().clear();
        Label countryTitle = new Label(country.getName() + " Fun Facts");
        countryTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        countryTitle.setAlignment(Pos.CENTER);
        VBox.setMargin(countryTitle, new Insets(0, 0, 5, 0));
        factsContainer.getChildren().add(countryTitle);

        String[][] facts = country.getFunFacts();
        if (facts != null && facts.length > 0) {
            for (String[] fact : facts) {
                VBox factBox = createFactVBox(fact[0], fact[1]);
                factsContainer.getChildren().add(factBox);
            }
        } else {
            Label noFactsLabel = new Label("No facts available for " + country.getName());
            noFactsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #999999; -fx-max-height: none; -fx-max-width: none;");
            noFactsLabel.setAlignment(Pos.CENTER);
            factsContainer.getChildren().add(noFactsLabel);
        }
    }

    private VBox createFactVBox(String text, String icon) {
        VBox factBox = new VBox(0);
        factBox.setPrefWidth(380.0);
        factBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-border-color: #e0e0e0; " +
                "-fx-border-width: 1px; -fx-border-radius: 10px; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.05), 3, 0, 0, 2);");
        factBox.setAlignment(Pos.CENTER);
        factBox.setFillWidth(true);

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 50px; -fx-padding: 10px 10px 0 10px;");
        iconLabel.setAlignment(Pos.CENTER);
        iconLabel.setPrefWidth(380.0);
        VBox.setVgrow(iconLabel, Priority.NEVER);

        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555555; -fx-padding: 5px 15px 15px 15px;");
        textLabel.setAlignment(Pos.CENTER);
        textLabel.setPrefWidth(360.0);
        VBox.setVgrow(textLabel, Priority.NEVER);

        factBox.getChildren().addAll(iconLabel, textLabel);
        VBox.setMargin(factBox, new Insets(5, 0, 5, 0));
        return factBox;
    }

    @FXML
    private void backToFunFacts(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/funFacts.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}