package com.example.geoquestkidsexplorer.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the generic continent game mode page.
 * This single controller handles all continents, replacing individual controllers like AfricaController, AsiaController, etc.
 */
public class ContinentsController {

    @FXML
    private Label continentLabel;
    @FXML
    private Button backButton;
    @FXML private Label gameModeWelcomeLabel;
    @FXML private javafx.scene.layout.VBox bgPane;
    @FXML private javafx.scene.layout.HBox navBar;


    private String continentName;

    /**
     * This method is called by the homepage controller to set the continent name.
     * @param name The name of the continent (e.g., "Africa", "Asia", "Oceania").
     */
    public void setContinentName(String name) {
        this.continentName = name;
        if (continentName == null || continentName.trim().isEmpty()) {
            System.err.println("Continent name is null or empty. Skipping quiz load.");
            gameModeWelcomeLabel.setText("Error: No continent selected.");
            return;
        }
        gameModeWelcomeLabel.setText("Game Mode Control for " + continentName + " Continent!");
        //continentLabel.setText(name); // Assuming you have a label to display the continent name
        backButton.setText("⬅️ Back to Continents"); // Use a generic back button text

        /**
         * Adding switch for colour changes
         * in different continent
         * */

        switch (name){
            case "Oceania" -> {
                bgPane.setStyle("-bg1:#f9f9f9 ; -bg2:#F4BA9B ; -bg3:#F5793A ;");
                navBar.setStyle("-nav-bg: #F5793A;");
                backButton.setStyle("-btn-bg:#F4BA9B;");
            }
            case "South America" -> {
                bgPane.setStyle("-bg1:#f9f9f9 ; -bg2:#E488DA ; -bg3:#A95AA1 ;");
                navBar.setStyle("-nav-bg: #A95AA1;");
                backButton.setStyle("-btn-bg:#E488DA;");
            }
            case "North America" -> {
                bgPane.setStyle("-bg1:#f9f9f9 ; -bg2:#FA76A7 ; -bg3:#D81B60 ;");
                navBar.setStyle("-nav-bg: #D81B60;");
                backButton.setStyle("-btn-bg:#FA76A7;");
            }
            case "Europe" -> {
                bgPane.setStyle("-bg1:#f9f9f9 ; -bg2:#4B66FF ; -bg3:#0F2080 ;");
                navBar.setStyle("-nav-bg: #0F2080;");
                backButton.setStyle("-btn-bg:#4B66FF;");
            }
        }
    }

    @FXML
    private void backToContinents(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the click event for the Practice Mode tile.
     * It loads the generic practice quiz page and passes the current continent name to it.
     */
    @FXML
    private void handleGameModeClick(MouseEvent event) {
        Node n = (Node) event.getTarget();
        while (n != null && n.getId() == null) n = n.getParent();
        String tileId = (n != null) ? n.getId() : null;
        if (tileId == null) {
            System.out.println("handleGameModeClick: no tile id found");
            return;
        }

        try {
            if ("practiceModeTile".equals(tileId)) {
                openPracticeQuiz(event, this.continentName);
            }else if ("flashCardsModeTile".equals(tileId)) {
                openFlashCardMode(event, this.continentName);
            } else if ("testModeTile".equals(tileId)) {
                // Similarly, you would open the generic test quiz and pass the continent name.
                openTestQuiz(event, this.continentName);
                System.out.println("Test Mode Quiz has been click! " + tileId);
            } else {
                System.out.println("handleGameModeClick: unknown tile id " + tileId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Helper method to load the generic practice quiz page and set the continent. */
    private void openPracticeQuiz(MouseEvent event, String continent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/practicequiz.fxml"));
        Parent root = loader.load();

        //Input colour pallete within different continents
        //Through css and fxml
        // Can be found in practisequiz.fxml and styles.css

        Node navBar = root.lookup("#navBar");
        Node backBtn = root.lookup("#backButton");
        Node score = root.lookup("#scoreLabel");

        if(navBar != null && backBtn != null){
            switch (continent){
                case "Oceania" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #F5793A;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#F4BA9B;");
                    if (score != null)score.setStyle("-score-bg:#F4BA9B;");
                }
                case "South America" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #A95AA1;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#E488DA;");
                    if (score != null)score.setStyle("-score-bg:#E488DA;");
                }
                case "North America" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #D81B60;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#FA76A7;");
                    if (score != null)score.setStyle("-score-bg:#FA76A7;");

                }
                case "Europe" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #0F2080;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#4B66FF;");
                    if (score != null)score.setStyle("-score-bg:#4B66FF;");
                }
            }
        }

        //Practise Quiz
        // Get the controller and set the continent name
        PracticeQuizController quizController = loader.getController();
        quizController.setContinentName(continent);

        // Switch the scene to the new quiz view
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Practice Quiz for: " + continent);
        stage.show();
    }

    /** Helper method to load the generic flash card mode page and set the continent. */
    @FXML
    private void openFlashCardMode(MouseEvent event, String continent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/FlashcardsPage.fxml"));
        Parent root = loader.load();

        //Input colour pallete within different continents
        //Through css and fxml
        // Can be found in flashcard.fxml and styles.css

        Node navBar = root.lookup("#navBar");
        Node backBtn = root.lookup("#backButton");
        Node score = root.lookup("#scoreLabel");
        Node timer = root.lookup("timerLabel");

        if(navBar != null && backBtn != null) {
            switch (continent) {
                case "Oceania" -> {
                    if (navBar != null) navBar.setStyle("-nav-bg: #F5793A;");
                    if (backBtn != null) backBtn.setStyle("-btn-bg:#F4BA9B;");
                }
                case "South America" -> {
                    if (navBar != null) navBar.setStyle("-nav-bg: #A95AA1;");
                    if (backBtn != null) backBtn.setStyle("-btn-bg:#E488DA;");
                }
                case "North America" -> {
                    if (navBar != null) navBar.setStyle("-nav-bg: #D81B60;");
                    if (backBtn != null) backBtn.setStyle("-btn-bg:#FA76A7;");
                }
                case "Europe" -> {
                    if (navBar != null) navBar.setStyle("-nav-bg: #0F2080;");
                    if (backBtn != null) backBtn.setStyle("-btn-bg:#4B66FF;");
                }
            }
        }

        // Get the controller and set the continent name
        FlashcardsController controller = loader.getController();
        controller.setRegion(continent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Flash Cards Mode for:" + continent);
        stage.show();
    }

    //* Helper method to load the generic quiz page and set the continent.
    private void openTestQuiz(Event event, String continent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/testmode.fxml"));
        Parent root = loader.load();

        //Input colour pallete within different continents
        //Through css and fxml
        // Can be found in testmode.fxml and styles.css

        Node navBar = root.lookup("#navBar");
        Node backBtn = root.lookup("#backButton");
        Node score = root.lookup("#scoreLabel");
        Node timer = root.lookup("timerLabel");

        if(navBar != null && backBtn != null){
            switch (continent){
                case "Oceania" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #F5793A;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#F4BA9B;");
                    if (score != null)score.setStyle("-score-bg:#F4BA9B;");
                    if (timer != null) timer.setStyle("-timer-bg:#F4BA9B;");
                }
                case "South America" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #A95AA1;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#E488DA;");
                    if (score != null)score.setStyle("-score-bg:#E488DA;");
                    if (timer != null) timer.setStyle("-timer-bg:#E488DA;");
                }
                case "North America" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #D81B60;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#FA76A7;");
                    if (score != null)score.setStyle("-score-bg:#FA76A7;");
                    if (timer != null) timer.setStyle("-timer-bg:#FA76A7;");

                }
                case "Europe" -> {
                    if(navBar != null) navBar.setStyle("-nav-bg: #0F2080;");
                    if(backBtn !=null) backBtn.setStyle("-btn-bg:#4B66FF;");
                    if (score != null)score.setStyle("-score-bg:#4B66FF;");
                    if (timer != null) timer.setStyle("-timer-bg:#4B66FF;");
                }
            }
        }
        TestModeController quizController = loader.getController();
        quizController.setContinentName(continent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quiz - " + continent);
        stage.show();
    }

    // (unchanged) Opens country test page in a new window — not used by quiz branch
    private void openTestPage(String continent, String country) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/geoquestkidsexplorer/country_image.fxml")
        );
        Parent root = loader.load();

        CountryImageController controller = loader.getController();
        Stage testStage = new Stage();
        testStage.setTitle(continent + " – " + country);
        testStage.setScene(new Scene(root, 600, 400));
        controller.setCountry(country, testStage);

        testStage.show();
    }

    /** Helper: load an FXML into the current window */
    private void loadScene(String fxmlPath, Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
