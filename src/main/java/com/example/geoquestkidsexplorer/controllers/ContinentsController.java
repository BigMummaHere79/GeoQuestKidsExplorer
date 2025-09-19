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

    private String continentName;

    /**
     * This method is called by the homepage controller to set the continent name.
     * @param name The name of the continent (e.g., "Africa", "Asia", "Oceania").
     */
    public void setContinentName(String name) {
        this.continentName = name;
        continentLabel.setText(name); // Assuming you have a label to display the continent name
        backButton.setText("⬅️ Back to Continents"); // Use a generic back button text
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
            } else if ("testModeTile".equals(tileId)) {
                // Similarly, you would open the generic test quiz and pass the continent name.
                //openTestQuiz(event, this.continentName);
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


    /* Open quiz_view.fxml in the SAME window (no new Stage)
    private void openQuiz(Event event, String continent) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/geoquestkidsexplorer/quiz_view.fxml")
        );
        Parent root = loader.load();

        // Reuse the existing window
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.getScene() == null) {
            stage.setScene(new Scene(root, 800, 600));
        } else {
            stage.getScene().setRoot(root);
        }
        stage.setTitle(continent + " Quiz");

        // Pass data into the quiz controller (optional setStage if your controller uses it)
        QuizController controller = loader.getController();
        try {
            controller.setStage(stage);   // keep Back actions working if your controller expects a Stage
        } catch (NoSuchMethodError | Exception ignore) { *//* ok if not present *//* }
        controller.setContinent(continent); // loads the first question inside controller

        stage.show();
    }*/

<<<<<<< HEAD
//    /**
//     * Helper method to load the generic quiz page and set the continent.
//     */
//    private void openTestQuiz(Event event, String continentName) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/quiz_view.fxml"));
//        Parent root = loader.load();
//
//        QuizController quizController = loader.getController();
//        quizController.setContinent(continentName);
//
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.setTitle("Quiz - " + continentName);
//        stage.show();
//    }

      //* Helper method to load the generic quiz page and set the continent.
    private void openTestQuiz(Event event, String continentName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/testquiz.fxml"));
        Parent root = loader.load();

        TestQuizController quizController = loader.getController();
        quizController.setContinentName(continentName);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Quiz - " + continentName);
        stage.show();
    }
=======
>>>>>>> 4ff40f0253745a40cd2111518a26639520319430


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

    @FXML
    private void handleFlashcards(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/geoquestkidsexplorer/FlashcardsPage.fxml")
        );
        Parent root = loader.load();

        FlashcardsController controller = loader.getController();
        controller.setRegion("Oceania");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root);
        stage.show();
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
