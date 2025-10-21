package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession;
import com.example.geoquestkidsexplorer.utils.NavigationHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * Controller for the profile creation UI.
 * Handles user input for name and avatar selection, validates data, and navigates to the next screen.
 * Extends BaseController for shared functionality like continent color application.
 */
public class ProfileCreationController extends BaseController {

    @FXML private TextField explorerNameField;
    @FXML private Label previewAvatarLabel;
    @FXML private Label previewNameLabel;
    @FXML private Label messageLabel;
    @FXML private ComboBox<String> avatarCombo;
    @FXML private Label avatarPreview;

    private String selectedAvatarEmoji;
    private VBox lastSelectedTile;

    /**
     * Sets the stage for this controller.
     * @param stage The JavaFX stage.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller after FXML loading.
     * Preloads session data and sets up listeners for UI elements.
     */
    @FXML
    public void initialize() {
        String sessionAvatar = UserSession.getAvatar();
        String sessionExplorerName = UserSession.getInstance().getUsername();

        selectedAvatarEmoji = (sessionAvatar != null && !sessionAvatar.isBlank()) ? sessionAvatar : "🙂";
        if (previewAvatarLabel != null) previewAvatarLabel.setText(selectedAvatarEmoji);
        if (previewNameLabel != null) previewNameLabel.setText(sessionExplorerName != null ?
                sessionExplorerName : "Choose your name!");
        if (explorerNameField != null) explorerNameField.setText(sessionExplorerName != null ?
                sessionExplorerName : "");

        if (avatarCombo != null) {
            avatarCombo.setItems(javafx.collections.FXCollections.observableArrayList(
                    "👦 Explorer Boy",
                    "👧 Explorer Girl",
                    "👨‍🎓 Student (Boy)",
                    "👩‍🎓 Student (Girl)"
            ));
            avatarCombo.valueProperty().addListener((obs, oldV, newV) -> {
                String emoji = (newV == null || newV.isBlank()) ? "🙂" : newV.split(" ")[0];
                if (avatarPreview != null) avatarPreview.setText(emoji);
                selectedAvatarEmoji = emoji;
                if (previewAvatarLabel != null) previewAvatarLabel.setText(emoji);
            });
        }
    }

    /**
     * Handles avatar tile selection.
     * Highlights the selected tile, updates the preview, and clears messages.
     * @param event The mouse event triggered by clicking an avatar tile.
     */
    @FXML
    private void selectAvatar(javafx.scene.input.MouseEvent event) {
        if (lastSelectedTile != null) {
            lastSelectedTile.getStyleClass().remove("selected-avatar");
        }

        VBox currentTile = (VBox) event.getSource();
        currentTile.getStyleClass().add("selected-avatar");
        lastSelectedTile = currentTile;

        Label avatarLabel = (Label) currentTile.getChildren().get(0);
        selectedAvatarEmoji = avatarLabel.getText();
        previewAvatarLabel.setText(selectedAvatarEmoji);

        messageLabel.setText("");
        System.out.println("Selected avatar: " + selectedAvatarEmoji);
    }

    /**
     * Creates the profile after validation and navigates to the profile created screen.
     * @param event The action event triggered by the create button.
     * @throws IOException If scene loading fails.
     */
    @FXML
    private void createProfile(ActionEvent event) throws IOException {
        String explorerName = explorerNameField.getText();

        if (explorerName == null || explorerName.isBlank()) {
            messageLabel.setText("Please enter a name for your explorer.");
            return;
        }

        if ((selectedAvatarEmoji == null || selectedAvatarEmoji.isBlank()) && avatarCombo != null) {
            String v = avatarCombo.getValue();
            if (v != null && !v.isBlank()) {
                selectedAvatarEmoji = v.split(" ")[0];
            }
        }

        if (selectedAvatarEmoji == null || selectedAvatarEmoji.isBlank()) {
            messageLabel.setText("Please select an avatar.");
            return;
        }

        UserSession.getInstance().setUsername(explorerName.trim());
        UserSession.getInstance().setAvatar(selectedAvatarEmoji);

        String username = UserSession.getUsername();
        if (username != null && !username.isBlank()) {
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "UPDATE users SET explorer_name = ?, avatar = ? WHERE username = ?")) {
                ps.setString(1, explorerName.trim());
                ps.setString(2, selectedAvatarEmoji);
                ps.setString(3, username);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.err.println("Profile save warning: " + e.getMessage());
            }
        }

        NavigationHelper.loadSceneWithConfig((Node) event.getSource(),
                "/com/example/geoquestkidsexplorer/profilecreated.fxml",
                (ProfileCreatedController controller) -> {
                    controller.setProfileData(explorerName, selectedAvatarEmoji);
                    controller.setStage(stage);
                });
    }

    /**
     * Implements continent-specific setup (required by BaseController).
     * No-op for this controller as it doesn't use continent-specific UI.
     * @param continentName The name of the continent.
     */
    @Override
    protected void setupContinent(String continentName) {
        // No continent-specific setup needed
    }

    /**
     * Sets profile data for the controller.
     * @param username The username to set.
     * @param avatar   The avatar to set.
     */
    @Override
    public void setProfileData(String username, String avatar) {
        UserSession.getInstance().setUsername(username);
        UserSession.getInstance().setAvatar(avatar);
        if (previewNameLabel != null) previewNameLabel.setText(username != null ? username : "Choose your name!");
        if (previewAvatarLabel != null) previewAvatarLabel.setText(avatar != null ? avatar : "🙂");
        if (explorerNameField != null) explorerNameField.setText(username != null ? username : "");
        selectedAvatarEmoji = avatar;
    }
}