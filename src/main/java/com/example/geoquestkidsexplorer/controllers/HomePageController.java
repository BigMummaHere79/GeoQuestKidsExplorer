package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.GameStateManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller for the home page UI, managing continent selection and lock status.
 * Extends BaseController for shared functionality like continent color application.
 */
public class HomePageController extends BaseController {

    @FXML private Pane africaPane;
    @FXML private Pane asiaPane;
    @FXML private Pane northAmericaPane;
    @FXML private Pane southAmericaPane;
    @FXML private Pane antarcticaPane;
    @FXML private Pane europePane;
    @FXML private Pane oceaniaPane;

    @FXML private Label africaLockedLabel;
    @FXML private Label asiaLockedLabel;
    @FXML private Label northAmericaLockedLabel;
    @FXML private Label southAmericaLockedLabel;
    @FXML private Label antarcticaLockedLabel;
    @FXML private Label europeLockedLabel;
    @FXML private Label oceaniaLockedLabel;

    @FXML private Label africaLockedTextLabel;
    @FXML private Label asiaLockedTextLabel;
    @FXML private Label northAmericaLockedTextLabel;
    @FXML private Label southAmericaLockedTextLabel;
    @FXML private Label antarcticaLockedTextLabel;
    @FXML private Label europeLockedTextLabel;
    @FXML private Label oceaniaLockedTextLabel;

    @FXML private Label avatarLabel;
    @FXML private Label welcomeLabel;
    @FXML private Label subWelcomeLabel;

    @FXML private SidebarController mySidebarController;

    // Consolidated maps for continent elements
    private final Map<String, Pane> continentPanes = new HashMap<>();
    private final Map<String, Label> continentLockLabels = new HashMap<>();
    private final Map<String, Label> continentTextLabels = new HashMap<>();

    /**
     * Sets the stage for this controller.
     * @param stage The JavaFX stage.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the UI with session data and continent maps.
     */
    @FXML
    public void initialize() {
        // Populate maps to avoid repetitive code
        continentPanes.put("Africa", africaPane);
        continentPanes.put("Asia", asiaPane);
        continentPanes.put("North America", northAmericaPane);
        continentPanes.put("South America", southAmericaPane);
        continentPanes.put("Antarctica", antarcticaPane);
        continentPanes.put("Europe", europePane);
        continentPanes.put("Oceania", oceaniaPane);

        continentLockLabels.put("Africa", africaLockedLabel);
        continentLockLabels.put("Asia", asiaLockedLabel);
        continentLockLabels.put("North America", northAmericaLockedLabel);
        continentLockLabels.put("South America", southAmericaLockedLabel);
        continentLockLabels.put("Antarctica", antarcticaLockedLabel);
        continentLockLabels.put("Europe", europeLockedLabel);
        continentLockLabels.put("Oceania", oceaniaLockedLabel);

        continentTextLabels.put("Africa", africaLockedTextLabel);
        continentTextLabels.put("Asia", asiaLockedTextLabel);
        continentTextLabels.put("North America", northAmericaLockedTextLabel);
        continentTextLabels.put("South America", southAmericaLockedTextLabel);
        continentTextLabels.put("Antarctica", antarcticaLockedTextLabel);
        continentTextLabels.put("Europe", europeLockedTextLabel);
        continentTextLabels.put("Oceania", oceaniaLockedTextLabel);

        String explorerName = UserSession.getUsername();
        String explorerAvatar = UserSession.getAvatar();
        setProfileData(explorerName, explorerAvatar);
        refreshContinentLocks();
    }

    /**
     * Sets profile data for the welcome labels.
     * @param username The username.
     * @param avatar   The avatar.
     */
    @Override
    public void setProfileData(String username, String avatar) {
        if (username != null && !username.isEmpty()) {
            welcomeLabel.setText("Welcome back, " + username + "!");
            avatarLabel.setText(avatar != null ? avatar : "");
            subWelcomeLabel.setText("Ready to continue your adventure?");
        } else {
            welcomeLabel.setText("Welcome, Explorer!");
            avatarLabel.setText("🙂");
            subWelcomeLabel.setText("Ready for a new adventure!");
        }
    }

    /**
     * Refreshes the lock status of continents based on game state.
     */
    public void refreshContinentLocks() {
        GameStateManager.getInstance().loadState();
        Set<String> unlockedContinents = GameStateManager.getInstance().getUnlockedContinents();
        System.out.println("refreshContinentLocks: Unlocked continents = " + unlockedContinents);

        Map<String, Boolean> continents = new HashMap<>();
        for (String continent : Arrays.asList("Antarctica", "Oceania", "South America", "North America", "Europe", "Asia", "Africa")) {
            continents.put(continent, !unlockedContinents.contains(continent));
        }
        setContinentLocks(continents);
    }

    /**
     * Sets the lock status for each continent using maps to avoid repetition.
     * @param continents Map of continent names to lock status (true if locked).
     */
    public void setContinentLocks(Map<String, Boolean> continents) {
        for (Map.Entry<String, Boolean> entry : continents.entrySet()) {
            String continentName = entry.getKey();
            boolean isLocked = entry.getValue();
            Pane pane = continentPanes.get(continentName);
            Label lockLabel = continentLockLabels.get(continentName);
            Label textLabel = continentTextLabels.get(continentName);

            if (pane != null && lockLabel != null && textLabel != null) {
                pane.setOpacity(isLocked ? 0.5 : 1.0);
                lockLabel.setVisible(isLocked);
                textLabel.setVisible(true);
                if (isLocked) {
                    textLabel.setText("This continent is locked. Complete previous level to unlock.");
                    textLabel.setStyle("-fx-text-fill: red;");
                    textLabel.setLayoutY(0);
                } else {
                    textLabel.setText("This continent is now unlocked.");
                    textLabel.setStyle("-fx-text-fill: green; -fx-padding: 5;");
                    textLabel.setLayoutY(pane.getHeight() - 10);
                }
                System.out.println("setContinentLocks: " + (isLocked ? "Locked" : "Unlocked") + " " + continentName);
            } else {
                System.err.println("setContinentLocks: Missing pane or label for " + continentName);
            }
        }
    }

    /**
     * Handles continent tile clicks, checking lock status and navigating if unlocked.
     * @param event The mouse event.
     */
    @FXML
    private void handleContinentClick(MouseEvent event) {
        Pane clickedPane = (Pane) event.getSource();
        String continentName = getContinentNameFromPane(clickedPane.getId());
        System.out.println("handleContinentClick: continentName = " + continentName);

        if (GameStateManager.getInstance().isContinentUnlocked(continentName)) {
            try {
                String fxml = continentName.equals("Antarctica") ? "/com/example/geoquestkidsexplorer/antarctica.fxml"
                        : "/com/example/geoquestkidsexplorer/continentview.fxml";
                NavigationHelper.loadSceneWithConfig((Node) event.getSource(), fxml,
                        (BaseController controller) -> {
                            controller.setProfileData(UserSession.getUsername(), UserSession.getAvatar());
                            controller.setStage(stage);
                            controller.setupContinent(continentName);
                        });
            } catch (IOException e) {
                System.err.println("Error loading game mode page for " + continentName + ": " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Continent Locked");
            alert.setHeaderText(null);
            alert.setContentText("This continent is locked! Please unlock it by completing a previous challenge.");
            alert.showAndWait();
        }
    }

    /**
     * Maps pane ID to continent name.
     *
     * @param paneId The pane ID.
     * @return The continent name or null if invalid.
     */
    private String getContinentNameFromPane(String paneId) {
        switch (paneId) {
            case "antarcticaPane": return "Antarctica";
            case "oceaniaPane": return "Oceania";
            case "southAmericaPane": return "South America";
            case "northAmericaPane": return "North America";
            case "europePane": return "Europe";
            case "asiaPane": return "Asia";
            case "africaPane": return "Africa";
            default: return null;
        }
    }

    /**
     * Implements continent-specific setup (required by BaseController).
     * Applies continent colors if applicable.
     *
     * @param continentName The name of the continent.
     */
    @Override
    protected void setupContinent(String continentName) {
        Map<String, Node> nodes = new HashMap<>();
        // Populate nodes if needed for continent-specific styling
        applyContinentColors(continentName, nodes);
    }
}