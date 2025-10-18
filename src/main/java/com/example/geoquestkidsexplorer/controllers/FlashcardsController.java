package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.FlashCard;
import com.example.geoquestkidsexplorer.models.FlashCardDeck;
import com.example.geoquestkidsexplorer.repositories.FlashCardRepository;
import com.example.geoquestkidsexplorer.models.FlashCardRealRepository;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.util.List;

/**
 * A lot of these controllers were refactored into different methods
 * Still keeping all logic that were called previously
 * This allows for easier code visibility and testing
 * **/

public class FlashcardsController {

    @FXML
    private Label regionLabel;

    @FXML
    private ImageView countryImageView;

    @FXML
    private Label backTextLabel;

    @FXML
    private StackPane flashcardStack;
    @FXML private Label quizWelcomeLabel;
    @FXML private Button backButton;
    @FXML
    private Button flipCardButton; // New field for Flip button
    @FXML
    private Button nextCardButton; // New field for Next button

    private final FlashCardRepository repository;
    private FlashCardDeck deck;
    private String continentName;

    public FlashcardsController(){
        this(new FlashCardRealRepository());
    }
    public FlashcardsController(FlashCardRepository repository){
        this.repository = repository;
    }

    private static final String DATABASE_URL = "jdbc:sqlite:geoquest.db";

    /**
     * Sets the region (continent) and loads the corresponding data (countries) for it.
     * @param region the name of the region/continent to load flashcards for
     */
    public void setRegion(String region) {

        /** Refactored - Extract Method
         So that the method  has both the has cards and no cards
         - Tori
        **/
        this.continentName = region;
        regionLabel.setText("Flashcards for " + region);
        backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        List<FlashCard> cards = repository.loadByRegion(region);
        deck = new FlashCardDeck(cards);

        // Show first card or an empty message
        render();
    }


    /**
     * Actions the “next country”, which  flips to the next country card
     * @param event the JavaFX ActionEvent triggered by the next-button
     */
    @FXML
    private void handleNextCountry(ActionEvent event) {
        // Refactored countries into deck
        if (deck == null || deck.isEmpty()) return;

        if (deck.showingFront()) {
            //Flip deck front to back
            deck.flip();
        } else {
            deck.next();
        }
        render();
    }


    /**
     * correspondingly actions events for card interaction (flip or next),
     * Ensures when next card is actioned the front (image) is shown
     *
     * @param event the JavaFX ActionEvent triggered by flip or next button
     */
    // Adding this method to handle both buttons in the flash card fxml. Glenda
    @FXML
    private void handleCardAction(ActionEvent event) {
        if (deck == null || deck.isEmpty()) return;

        Button source = (Button) event.getSource();
        String buttonId = source.getId();

        if ("flipCardButton".equals(buttonId)) {
            // Flip the card (front to back or back to front)
            deck.flip();
        } else if ("nextCardButton".equals(buttonId)) {
            // Move to the next card and ensure it shows the front
            deck.next();
            if (!deck.showingFront()) {
                deck.flip(); // Ensure the front is shown
            }
        }

        render();
    }

    /** Single Place that maps the deck state that you had above
     * I've just input that into one method called render
     * Same call, just different method
     * Tori**/

    private void render(){
        if (deck == null || deck.isEmpty()){
            //Empty deck
            countryImageView.setImage(null);
            countryImageView.setVisible(false);
            backTextLabel.setText("Sorry, no countries yet.");
            backTextLabel.setVisible(true);
            return;
        }

        FlashCard current = deck.current();
        if(deck.showingFront()){
            countryImageView.setImage(current.image());
            countryImageView.setVisible(true);
            backTextLabel.setText("");
            backTextLabel.setVisible(false);
        }
        else{
            countryImageView.setVisible(false);
            backTextLabel.setText("This is " + current.countryName());
            backTextLabel.setVisible(true);
        }
    }


    /**
     * actions navigation back to the continent view. Loads the
     * continent FXML, passes the continent name to the controller
     *
     * @param event the JavaFX ActionEvent triggered by the back button
     * @throws IOException if loading the FXML file fails
     */
    @FXML
    private void handleBackToContinent(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/continentview.fxml"));
        Parent root = loader.load();
        ContinentsController controller = loader.getController();
        controller.setContinentName(continentName); // Pass continentName back
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
