package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.data.AntarcticaFunFacts;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AntarcticaFactsController {

    @FXML
    private VBox factsContainer;

    /**
     * Initializes the controller and loads the Antarctica facts automatically.
     */
    @FXML
    public void initialize() {
        loadAntarcticaFacts();
    }

    /**
     * Loads and displays the 5 fun facts for Antarctica.
     */
    private void loadAntarcticaFacts() {
        factsContainer.getChildren().clear();

        // Add continent title (centered at top)
        Label continentTitle = new Label("Antarctica Fun Facts");
        continentTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        continentTitle.setAlignment(Pos.CENTER);
        VBox.setMargin(continentTitle, new Insets(0, 0, 5, 0));
        factsContainer.getChildren().add(continentTitle);

        // Fetch facts using FunFactsProvider
        AntarcticaFunFacts funFactsProvider = new AntarcticaFunFacts();
        List<Country> countries = funFactsProvider.getCountries();
        String[][] facts = (countries != null && !countries.isEmpty())
                ? countries.get(0).getFunFacts()
                : new String[0][];

        if (facts.length == 0) {
            Label noFactsLabel = new Label("No facts available for Antarctica");
            noFactsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #999999; -fx-max-height: none; -fx-max-width: none;");
            noFactsLabel.setAlignment(Pos.CENTER);
            factsContainer.getChildren().add(noFactsLabel);
            return;
        }

        for (String[] fact : facts) {
            VBox factBox = createFactVBox(fact[0], fact[1]);
            factsContainer.getChildren().add(factBox);
        }
    }

    /**
     * Creates a VBox for a single fact (REUSED LOGIC).
     * @param text The  fun fact text
     * @param icon The icon associated with the fun fact.
     * @return A styled VBox containing the icon and corresponding fact text.
     */
    private VBox createFactVBox(String text, String icon) {
        // This is the exact, working logic copied from MultiCountryController (formerly AfricaFunFactsController)
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
        textLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555; -fx-padding: 5px 15px 15px 15px;");
        textLabel.setAlignment(Pos.CENTER);
        textLabel.setPrefWidth(360.0);
        VBox.setVgrow(textLabel, Priority.NEVER);

        factBox.getChildren().addAll(iconLabel, textLabel);
        VBox.setMargin(factBox, new Insets(5, 0, 5, 0));
        return factBox;
    }

    /**
     * Handles the "Back to Continents" button action, by navigating back to homepage
     * @param event The action event triggered by clicking the back button.
     * @throws IOException If loading the FXML file fails.
     */
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