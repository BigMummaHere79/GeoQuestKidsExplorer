package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomePageController {

    // You will need to add an @FXML field for each continent tile you have in startadventure.fxml
    @FXML
    private Pane africaPane;
    @FXML
    private Pane asiaPane;
    @FXML
    private Pane northAmericaPane;
    @FXML
    private Pane southAmericaPane;
    @FXML
    private Pane antarcticaPane;
    @FXML
    private Pane europePane;
    @FXML
    private Pane oceaniaPane;

    // You will also need a label to show the locked text
    @FXML
    private Label africaLockedLabel;
    @FXML
    private Label asiaLockedLabel;
    @FXML
    private Label northAmericaLockedLabel;
    @FXML
    private Label southAmericaLockedLabel;
    @FXML
    private Label antarcticaLockedLabel;
    @FXML
    private Label europeLockedLabel;
    @FXML private Label oceaniaLockedLabel;

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
     * This method is called by HomePageController to set the lock status of each continent.
     */
    public void setContinentLocks(Map<String, Boolean> continents) {
        // Map of continent names to their corresponding panes
        Map<String, Pane> continentPanes = new HashMap<>();
        continentPanes.put("Antarctica", antarcticaPane);
        continentPanes.put("Oceania", oceaniaPane);
        continentPanes.put("South America", southAmericaPane);
        continentPanes.put("North America", northAmericaPane);
        continentPanes.put("Europe", europePane);
        continentPanes.put("Asia", asiaPane);
        continentPanes.put("Africa", africaPane);

        // Map of continent names to their corresponding locked labels
        Map<String, Label> continentLabels = new HashMap<>();
        continentLabels.put("Antarctica", antarcticaLockedLabel);
        continentLabels.put("Oceania", oceaniaLockedLabel);
        continentLabels.put("South America", southAmericaLockedLabel);
        continentLabels.put("North America", northAmericaLockedLabel);
        continentLabels.put("Europe", europeLockedLabel);
        continentLabels.put("Asia", asiaLockedLabel);
        continentLabels.put("Africa", africaLockedLabel);

        // Iterate through the continents and set their state based on the map
        for (Map.Entry<String, Boolean> entry : continents.entrySet()) {
            String continentName = entry.getKey();
            boolean isLocked = entry.getValue();

            Pane pane = continentPanes.get(continentName);
            Label label = continentLabels.get(continentName);

            if (pane != null) {
                if (isLocked) {
                    pane.setOpacity(0.5); // Make it visually "locked"
                    // Add an event filter to prevent clicks on locked tiles
                    // Removed the event filter to handle it inside the method instead
                    if (label != null) {
                        label.setVisible(true); // Show a "locked" label
                    }
                } else {
                    pane.setOpacity(1.0); // Make it visually "unlocked"
                    if (label != null) {
                        label.setVisible(false); // Hide the locked label
                    }
                }
            }
        }
    }

    /**
     * Handles the click event on a continent tile.
     * Prevents action if the tile is locked.
     */
    @FXML
    private void handleContinentClick(MouseEvent event) {

        Pane clickedPane = (Pane) event.getSource();
        String continentName = null;

        // Determine which pane was clicked and check its lock status.
        if (clickedPane.getId().equals("antarcticaPane")) {
            continentName = "Antarctica";
        } else if (clickedPane.getId().equals("oceaniaPane")) {
            continentName = "Oceania";
        } else if (clickedPane.getId().equals("southAmericaPane")) {
            continentName = "South America";
        } else if (clickedPane.getId().equals("northAmericaPane")) {
            continentName = "North America";
        } else if (clickedPane.getId().equals("europePane")) {
            continentName = "Europe";
        } else if (clickedPane.getId().equals("asiaPane")) {
            continentName = "Asia";
        } else if (clickedPane.getId().equals("africaPane")) {
            continentName = "Africa";
        }

        System.out.println("handleContinentClick: continentName = " + continentName);

        // Special case for Antarctica
        if ("Antarctica".equals(continentName)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/antarctica.fxml"));
                if (loader.getLocation() == null) {
                    System.err.println("Error: antarctica.fxml resource not found");
                    return;
                }
                Parent root = loader.load();
                // Assuming AntarcticaController exists; adjust if needed
                AntarcticaController controller = loader.getController();
                controller.setContinentName(continentName); // Uncomment if AntarcticaController needs continentName
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle("Antarctica");
                stage.show();
            } catch (IOException e) {
                System.err.println("Error loading antarctica.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        }
        // Handle other continents
        else if (GameStateManager.getInstance().isContinentUnlocked(continentName)) {
            try {
                loadGameModePage(event, continentName);
            } catch (IOException e) {
                System.err.println("Error loading game mode page for " + continentName + ": " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("This continent is locked! Please unlock it by completing a previous challenge.");
            // Optionally show a dialog to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Continent Locked");
            alert.setHeaderText(null);
            alert.setContentText("This continent is locked! Please unlock it by completing a previous challenge.");
            alert.showAndWait();
        }
    }

    /**
     * Loads the generic game mode page and passes the continent name to its controller.
     *
     * @param event The mouse event that triggered the method.
     * @param continentName The name of the continent to display.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private void loadGameModePage(MouseEvent event, String continentName) throws IOException {
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