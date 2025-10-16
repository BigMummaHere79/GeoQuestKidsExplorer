package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.UserSession; // NEW
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;        // NEW (for optional DB update)
import java.sql.PreparedStatement; // NEW
import java.sql.SQLException;      // NEW

public class ProfileCreationController {

    @FXML
    private TextField explorerNameField;
    @FXML
    private Label previewAvatarLabel;
    @FXML
    private Label previewNameLabel;
    @FXML
    private Label messageLabel;

    // Instance variables to keep track of the selected avatar and the last selected tile
    private String selectedAvatarEmoji;
    private VBox lastSelectedTile = null;

    @FXML private ComboBox<String> avatarCombo;
    @FXML private Label avatarPreview;

    /**
     * Initializes the controller. This method is called automatically after the FXML has been loaded.
     */
    @FXML
    public void initialize() {
        // NEW: Preload from session so preview shows current values
        String sessionAvatar = UserSession.getAvatar();
        String sessionExplorerName = UserSession.getExplorerName();

        selectedAvatarEmoji = (sessionAvatar != null && !sessionAvatar.isBlank()) ? sessionAvatar : "🙂";
        if (previewAvatarLabel != null) previewAvatarLabel.setText(selectedAvatarEmoji);
        if (previewNameLabel != null)  previewNameLabel.setText(sessionExplorerName != null ? sessionExplorerName : "Choose your name!");
        if (explorerNameField != null) explorerNameField.setText(sessionExplorerName != null ? sessionExplorerName : "");

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
                selectedAvatarEmoji = emoji;                      // NEW: actually set selection
                if (previewAvatarLabel != null) previewAvatarLabel.setText(emoji); // keep preview in sync
            });
        }
    }

    /**
     * Handles the click on any of the avatar tile
     * * <p>
     * Highlights the clicked tile and, stores the
     * selected emoji, updates preview, and clears existing error messages.
     * </p>
     *
     * @param event the MouseEvent is triggered by clicking the avatar tile
     */
    @FXML
    private void selectAvatar(javafx.scene.input.MouseEvent event) {
        // First, check if a tile was previously selected and reset its style.
        if (lastSelectedTile != null) {
            lastSelectedTile.getStyleClass().remove("selected-avatar");
        }

        // Get the VBox (tile) that was clicked.
        VBox currentTile = (VBox) event.getSource();

        // Add the CSS class "selected-avatar" to the current tile to highlight it.
        currentTile.getStyleClass().add("selected-avatar");

        // Store a reference to the current tile.
        lastSelectedTile = currentTile;

        // Get the emoji from the first child (Label) of the VBox.
        Label avatarLabel = (Label) currentTile.getChildren().get(0);
        selectedAvatarEmoji = avatarLabel.getText();

        // Update the preview avatar label with the selected emoji.
        previewAvatarLabel.setText(selectedAvatarEmoji);

        // Clear any previous error messages.
        messageLabel.setText("");

        System.out.println("Selected avatar: " + selectedAvatarEmoji);
    }


    /**
     * when user ceates profile, it validates the name, avatar selection, and updates the session
     * @param event the ActionEvent triggered by the “create profile” button
     * @throws IOException if loading the FXML view fails
     */
    @FXML
    private void createProfile(ActionEvent event) throws IOException {
        String explorerName = explorerNameField.getText();

        if (explorerName == null || explorerName.isBlank()) {
            messageLabel.setText("Please enter a name for your explorer.");
            return;
        }

        // If the user used only the combo and not a tile, ensure we still have an emoji
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

        System.out.println("Creating profile for: " + explorerName + " with avatar " + selectedAvatarEmoji);

        // NEW: Save to session so UserProgress can read it on initialize()
        UserSession.setExplorerName(explorerName.trim());
        UserSession.setAvatar(selectedAvatarEmoji);

        // NEW (optional): Persist to DB if we know the login username
        String username = UserSession.getUsername(); // login identity
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

        // Get the current stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Load the Home Page view using the correct path.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/geoquestkidsexplorer/profilecreated.fxml"));
        Parent root = loader.load();

        // Pass the name and avatar data to the Home Page controller.
        ProfileCreatedController profileCreatedController = loader.getController();
        profileCreatedController.setProfileData(explorerName, selectedAvatarEmoji);
        profileCreatedController.setStage(stage);

        // Set the new scene.
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
