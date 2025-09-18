package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Label avatarLabel;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label subWelcomeLabel;

    @FXML
    private SidebarController mySidebarController; // FXML loader automatically injects this.
    // A private field to hold the explorer's avatar string
    private String explorerAvatar;
    private String explorerName;

    /**
     * The initialize method should not contain business logic.
     * It's only for setting up UI elements.
     */
    @FXML
    public void initialize() {
        // Get user data directly from the UserSession
        String explorerName = UserSession.getUsername();
        String explorerAvatar = UserSession.getAvatar();

        if (explorerName != null && !explorerName.isEmpty()) {
            welcomeLabel.setText("Welcome back, " + explorerName + "!");
            avatarLabel.setText(explorerAvatar != null ? explorerAvatar : "");
            subWelcomeLabel.setText("Ready to continue your adventure?");

            // Ensure the sidebar also gets the data
            if (mySidebarController != null) {
                mySidebarController.setProfileData(explorerName, explorerAvatar);
            }
        } else {
            // This handles cases where no one is logged in
            welcomeLabel.setText("Welcome, Explorer!");
            avatarLabel.setText("🙂");
            subWelcomeLabel.setText("Ready for a new adventure!");
        }
    }

    /**
     * This is the new, centralized method for setting all user data.
     * It is called by the LoginController after a successful login.
     */
    public void setProfileData(String username, String avatar) {
        // Set the labels for the HomePageController
        this.welcomeLabel.setText("Welcome back, " + username + "!");
        this.avatarLabel.setText(avatar);
        this.subWelcomeLabel.setText("Ready to continue your adventure?");

        // Now, pass the same data to the SidebarController.
        // The FXML loader ensures mySidebarController is not null here.
        if (mySidebarController != null) {
            mySidebarController.setProfileData(username, avatar);
        }
    }

    /**
     * Handles the "Start Your New Adventure!" button action.
     * Note: This method should be called from the HomePageController itself.
     */
    private void openQuiz(String continent) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/geoquestkidsexplorer/quiz_view.fxml"));
            Parent root = loader.load();

            // Show quiz in a new window (keeps Home open)
            Stage quizStage = new Stage();
            quizStage.setTitle(continent + " Quiz");
            quizStage.setScene(new Scene(root, 800, 600));

            // Pass data into the quiz controller
            QuizController controller = loader.getController();
            controller.setStage(quizStage);       // so Back can close this window
            controller.setContinent(continent);   // loads the first question

            quizStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startNewAdventure(ActionEvent event) {
        openQuiz("South America"); // or choose based on the user’s level/last choice
    }

    /**
     * Handles the "click" event for a continent button.
     * This method now correctly retrieves the continent name from the Label inside the button's graphic.
     */
    @FXML
    private void handleContinentClick(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String continentName = null;

        // Check if the button has a graphic and if it's a VBox
        if (clickedButton.getGraphic() instanceof VBox graphicBox) {
            // Iterate through the children of the VBox to find the Label
            for (Node node : graphicBox.getChildren()) {
                if (node instanceof Label continentLabel) {
                    continentName = continentLabel.getText().trim();
                    break; // Found the label, exit the loop
                }
            }
        }

        // Now, we can proceed with the logic using the extracted name
        if (continentName != null && !continentName.isEmpty()) {
            loadGameModePage(event, continentName);
        } else {
            System.out.println("Unknown continent clicked: Could not find a text label within the button's graphic.");
        }
    }

    /**
     * Loads the generic game mode page and passes the continent name to its controller.
     *
     * @param event The mouse event that triggered the method.
     * @param continentName The name of the continent to display.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private void loadGameModePage(ActionEvent event, String continentName) throws IOException {
        // Default continent page
        String fxml = "/com/example/geoquestkidsexplorer/continentview.fxml";

        // Special case: Antarctica goes to its own file
        if ("Antarctica".equalsIgnoreCase(continentName)) {
            fxml = "/com/example/geoquestkidsexplorer/antarctica.fxml";
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        // Pass the continent to controllers that accept it (generic or Antarctica-specific)
        Object ctl = loader.getController();
        try {
            ctl.getClass().getMethod("setContinentName", String.class).invoke(ctl, continentName);
        } catch (NoSuchMethodException ignored) {
            // Controller doesn't need/accept the continent; that's fine.
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Reuse the current window (preserves size)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.getScene() == null) stage.setScene(new Scene(root, 1000, 700));
        else stage.getScene().setRoot(root);
        stage.setTitle(continentName + " Game Modes");
        stage.show();
    }

}