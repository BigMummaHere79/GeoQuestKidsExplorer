package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.FlashCard;
import com.example.geoquestkidsexplorer.models.FlashCardDeck;
import com.example.geoquestkidsexplorer.repositories.FlashCardRepository;
import com.example.geoquestkidsexplorer.models.FlashCardRealRepository;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the flashcards UI.
 * Manages flashcard interactions for a specific continent.
 */
public class FlashcardsController extends BaseController {

    @FXML private Label regionLabel;
    @FXML private ImageView countryImageView;
    @FXML private Label backTextLabel;
    @FXML private StackPane flashcardStack;
    @FXML private Label quizWelcomeLabel;
    @FXML private Button backButton;
    @FXML private Button flipCardButton;
    @FXML private Button nextCardButton;

    private final FlashCardRepository repository;
    private FlashCardDeck deck;
    private String continentName;
    private String username;
    private String avatar;

    /**
     * Sets the stage for this controller.
     * @param stage The JavaFX stage.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @Override
    protected void setupContinent(String continentName) {
        this.continentName = continentName;
    }

    public FlashcardsController() {
        this(new FlashCardRealRepository());
    }

    public FlashcardsController(FlashCardRepository repository) {
        this.repository = repository;
    }

    /**
     * Sets the region (continent) and loads the corresponding data (countries) for it.
     *
     * @param region The name of the region/continent to load flashcards for.
     */
    public void setRegion(String region) {
        this.continentName = region;
        if (regionLabel != null) {
            regionLabel.setText("Flashcards for " + region);
        }
        if (backButton != null) {
            backButton.setText("⬅️ Back to " + continentName + " Game Mode");
        }
        if (quizWelcomeLabel != null) {
            quizWelcomeLabel.setText("Flashcards for " + region + " Continent!");
        }
        List<FlashCard> cards = repository.loadByRegion(region);
        deck = new FlashCardDeck(cards);
        render();
    }

    /**
     * Sets profile data for the controller.
     *
     * @param username The username to set.
     * @param avatar   The avatar to set.
     */
    public void setProfileData(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
        UserSession.setUser(username, avatar);
    }

    /**
     * Actions the "next country", which flips to the next country card.
     *
     * @param event The JavaFX ActionEvent triggered by the next button.
     */
    @FXML
    private void handleNextCountry(ActionEvent event) {
        if (deck == null || deck.isEmpty()) return;
        if (deck.showingFront()) {
            deck.flip();
        } else {
            deck.next();
        }
        render();
    }

    /**
     * Actions events for card interaction (flip or next).
     * Ensures the front (image) is shown when moving to the next card.
     *
     * @param event The JavaFX ActionEvent triggered by flip or next button.
     */
    @FXML
    private void handleCardAction(ActionEvent event) {
        if (deck == null || deck.isEmpty()) return;
        Button source = (Button) event.getSource();
        String buttonId = source.getId();
        if ("flipCardButton".equals(buttonId)) {
            deck.flip();
        } else if ("nextCardButton".equals(buttonId)) {
            deck.next();
            if (!deck.showingFront()) {
                deck.flip();
            }
        }
        render();
    }

    /**
     * Renders the current flashcard state.
     */
    private void render() {
        if (deck == null || deck.isEmpty()) {
            countryImageView.setImage(null);
            countryImageView.setVisible(false);
            backTextLabel.setText("Sorry, no countries yet.");
            backTextLabel.setVisible(true);
            return;
        }
        FlashCard current = deck.current();
        if (deck.showingFront()) {
            countryImageView.setImage(current.image());
            countryImageView.setVisible(true);
            backTextLabel.setText("");
            backTextLabel.setVisible(false);
        } else {
            countryImageView.setVisible(false);
            backTextLabel.setText("This is " + current.countryName());
            backTextLabel.setVisible(true);
        }
    }

    /**
     * Navigates back to the continent view.
     *
     * @param event The JavaFX ActionEvent triggered by the back button.
     * @throws IOException If loading the FXML file fails.
     */
    @FXML
    private void handleBackToContinent(ActionEvent event) throws IOException {
        NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/continentview.fxml",
                (ContinentsController controller) -> {
                    controller.setContinentName(continentName);
                    controller.setProfileData(username, avatar);
                    controller.setStage((Stage) ((Node) event.getSource()).getScene().getWindow());
                });
    }
}
