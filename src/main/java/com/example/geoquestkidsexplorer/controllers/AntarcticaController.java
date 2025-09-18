package com.example.geoquestkidsexplorer.controllers;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AntarcticaController {

    @FXML
    private void backToContinents(ActionEvent event) {
        try {
            // Load the StartAdventure.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/geoquestkidsexplorer/homepage.fxml"));
            Scene scene = new Scene(root);

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleGameModeClick(MouseEvent event) {
        // Get the source of the click, which is the VBox tile.
        Node clickedTile = (Node) event.getSource();
        String tileId = clickedTile.getId();

        try {
            // Use the ID to determine which game mode was selected and load the corresponding scene.
            if ("practiceModeTile".equals(tileId)) {
                loadScene("/com/example/geoquestkidsexplorer/practicequizoceania.fxml", event);
            } else if ("testModeTile".equals(tileId)) {
                // Nikki: insert testpage link and call openTestMethod
                loadScene("/com/example/geoquestkidsexplorer/testModeSouthAmerica.fxml", event);
                System.out.println("Test Mode Quiz selected!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * A private helper method to load a new FXML scene and transition to it.
     * This version is more flexible and can accept any type of Event.
     *
     * @param fxmlPath The path to the FXML file to load.
     * @param event The event that triggered the action.
     * @throws IOException If the FXML file cannot be loaded.
     */
//    private void loadScene(String fxmlPath, Event event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
//    }

    //Changed the loadScene for now, to behave like Aaliyah's OpenQuiz
    private void loadScene(String fxmlPath, Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/testModeSouthAmerica.fxml"));
        Parent root = loader.load();

        TestQuizSouthAmericaController controller = loader.getController();
        controller.setContinent("Antarctica"); // this triggers nextQuestion()

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //To open testPage for now = Aaliyah's code
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
        } catch (NoSuchMethodError | Exception ignore) { /* ok if not present */ }
        controller.setContinent(continent); // loads the first question inside controller

        stage.show();
    }

    @FXML
    private void handleFlashcards(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/FlashcardsPage.fxml"));
        Parent root = loader.load(); //Note: just modified this to use the default main frame window.

        FlashcardsController controller = loader.getController();
        controller.setRegion("Antarctica");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        /*Scene scene = new Scene(root); //NOTE: commented this both to set a new logic
        stage.setScene(scene);*/
        stage.getScene().setRoot(root); //NOTE: add this line of codes to replace the first two line that has been commented.
        stage.show();
    }
}