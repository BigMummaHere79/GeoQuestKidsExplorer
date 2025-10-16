package com.example.geoquestkidsexplorer.controllers;

import com.example.geoquestkidsexplorer.database.DatabaseManager;
import com.example.geoquestkidsexplorer.models.FeedbackRatings;
import com.example.geoquestkidsexplorer.models.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.controlsfx.control.Rating;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FeedbackController {

    @FXML
    private Label avatarLabel;

    @FXML
    private HBox ratingContainer;

    @FXML
    private TextArea commentArea;

    @FXML
    private VBox feedbacksVBox;

    @FXML
    private Button submitButton;

    private Rating ratingControl;

    private String currentUsername;

    public void initialize() {
        // Get current username from UserSession
        currentUsername = UserSession.getUsername();

        // Initialize rating control
        ratingControl = new Rating(5);
        ratingControl.setPartialRating(true);
        ratingControl.setUpdateOnHover(false);
        ratingControl.setStyle("-fx-effect: none;");
        ratingControl.setRating(0); // Ensure no stars are filled
        ratingControl.setDisable(true);
        ratingControl.setDisable(false);
        ratingContainer.getChildren().add(ratingControl);

        // Check if user is logged in
        if (currentUsername == null) {
            avatarLabel.setText("🔒");
            commentArea.setDisable(true);
            ratingControl.setDisable(true);
            submitButton.setDisable(true);
            showAlert("Information", "Please log in to leave feedback.");
        } else {
            // Set avatar for current user
            String[] userDetails = DatabaseManager.getUserDetails(currentUsername);
            if (userDetails != null) {
                avatarLabel.setText(userDetails[1]); // Avatar
            } else {
                avatarLabel.setText("😊");
            }
        }
        // Load feedbacks
        loadFeedbacks();
    }


    /**
     * Submits the user's feedback if it meets the validation requirments
     * @param event the JavaFX ActionEvent triggered by clicking the submit button
     */
    @FXML
    private void submitFeedback(ActionEvent event) {
        if (currentUsername == null) {
            showAlert("Error", "You must be logged in to submit feedback.");
            return;
        }

        double rating = ratingControl.getRating();
        String comment = commentArea.getText().trim();
        if (rating > 0 && !comment.isEmpty()) {
            try {
                DatabaseManager.addFeedback(currentUsername, rating, comment, null);
                commentArea.clear();
                ratingControl.setRating(0);
                ratingControl.setDisable(true);
                ratingControl.setDisable(false);
                loadFeedbacks();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to submit feedback.");
            }
        } else {
            showAlert("Warning", "Please provide a rating and comment.");
        }
    }

    private void loadFeedbacks() {
        feedbacksVBox.getChildren().clear();
        try {
            List<FeedbackRatings> topLevelFeedbacks = DatabaseManager.getTopLevelFeedbacks();
            for (FeedbackRatings fb : topLevelFeedbacks) {
                VBox feedbackBox = createFeedbackBox(fb, false, 0);
                feedbacksVBox.getChildren().add(feedbackBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load feedbacks.");
        }
    }



    /**
     * Creates a VBox UI element representing a single feedback (or reply),
     * with its header (avatar, name, rating, timestamp), comment, and actions.
     *
     * @param fb     the FeedbackRatings object which has the feedback data
     * @param isReply determines whether the provided feedback is a reply
     * @param level  the nesting level (depth) of this reply for indentation
     * @return a VBox in the user interface showing the feedback
     */
    private VBox createFeedbackBox(FeedbackRatings fb, boolean isReply, int level) {
        VBox box = new VBox(5);
        box.setStyle("-fx-background-color: #CAD6CA; -fx-padding: 10; -fx-border-color: #ddd; -fx-border-radius: 5; " +
                "-fx-background-radius: 5;");
        if (isReply) {
            box.setPadding(new Insets(0, 0, 0, 40 * level));
        }

        // Header: Avatar, Name, Rating, Timestamp
        HBox header = new HBox(10);
        Label avatarLbl = new Label(fb.getAvatar());
        avatarLbl.setFont(new Font(30));
        Label nameLbl = new Label(fb.getUsername());
        nameLbl.setStyle("-fx-font-weight: bold;");
        Rating displayRating = new Rating(5);
        displayRating.setRating(fb.getRating());
        displayRating.setPartialRating(true);
        displayRating.setDisable(true);
        Label timeLbl = new Label(fb.getTimestamp());
        timeLbl.setStyle("-fx-text-fill: gray;");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(avatarLbl, nameLbl, displayRating, spacer, timeLbl);

        // Comment
        Label commentLbl = new Label(fb.getComment());
        commentLbl.setWrapText(true);

        // Actions
        HBox actions = new HBox(10);
        boolean isOwnComment = currentUsername != null && fb.getUsername().equals(currentUsername);
        if (isOwnComment) {
            Button editBtn = new Button("Update");
            editBtn.setOnAction(e -> editFeedback(fb));
            Button deleteBtn = new Button("Delete");
            deleteBtn.setOnAction(e -> deleteFeedback(fb));
            actions.getChildren().addAll(editBtn, deleteBtn);
        }

        // Check if it has replies
        boolean hasReplies = false;
        try {
            hasReplies = !DatabaseManager.getReplies(fb.getFeedbackId()).isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Show Reply button logic
        boolean showReply = currentUsername != null && (!isOwnComment || hasReplies);
        if (showReply) {
            Button replyBtn = new Button("Reply");
            replyBtn.setOnAction(e -> showReplyForm(box, fb.getFeedbackId()));
            actions.getChildren().add(replyBtn);
        }

        box.getChildren().addAll(header, commentLbl, actions);
        // Load replies recursively
        try {
            List<FeedbackRatings> replies = DatabaseManager.getReplies(fb.getFeedbackId());
            for (FeedbackRatings reply : replies) {
                VBox replyBox = createFeedbackBox(reply, true, level + 1);
                box.getChildren().add(replyBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return box;
    }



    /**
     * Displays the ability for the user to reply to older feedback
     *
     * @param parentBox the VBox of the parent feedback for the user to reply to
     * @param parentId  the feedbackId of the feedback being replied to
     */
    private void showReplyForm(VBox parentBox, int parentId) {
        if (currentUsername == null) {
            showAlert("Error", "You must be logged in to reply.");
            return;
        }

        VBox replyForm = new VBox(5);
        replyForm.setPadding(new Insets(10, 0, 0, 0));

        HBox replyRatingContainer = new HBox();
        Rating replyRating = new Rating(5);
        replyRating.setPartialRating(true);
        replyRating.setRating(0); // Ensure no stars are filled
        replyRatingContainer.getChildren().add(replyRating);

        TextArea replyComment = new TextArea();
        replyComment.setPrefHeight(60);
        replyComment.setWrapText(true);
        replyComment.setPromptText("Enter your reply...");

        Button submitReply = new Button("Submit Reply");
        submitReply.setOnAction(e -> {
            double rating = replyRating.getRating();
            String comment = replyComment.getText().trim();
            if (rating > 0 && !comment.isEmpty()) {
                try {
                    DatabaseManager.addFeedback(currentUsername, rating, comment, parentId);
                    loadFeedbacks();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showAlert("Error", "Failed to submit reply.");
                }
            } else {
                showAlert("Warning", "Please provide a rating and reply.");
            }
        });

        replyForm.getChildren().addAll(new Label("Your Reply Rating:"), replyRatingContainer, new Label("Your Reply:"),
                replyComment, submitReply);
        parentBox.getChildren().add(replyForm);
    }


    /**
     * Allows the user to be able to edit their own feedback
     * @param fb the FeedbackRatings object can be edited
     */
    private void editFeedback(FeedbackRatings fb) {
        if (currentUsername == null || !fb.getUsername().equals(currentUsername)) {
            showAlert("Error", "You can only edit your own feedback.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Edit Feedback");
        dialog.setHeaderText("Update your rating and comment.");

        VBox content = new VBox(10);
        Rating editRating = new Rating(5);
        editRating.setPartialRating(true);
        editRating.setRating(fb.getRating());
        TextArea editComment = new TextArea(fb.getComment());
        editComment.setWrapText(true);
        content.getChildren().addAll(new Label("Rating:"), editRating, new Label("Comment:"), editComment);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            double newRating = editRating.getRating();
            String newComment = editComment.getText().trim();
            if (newRating > 0 && !newComment.isEmpty()) {
                try {
                    DatabaseManager.updateFeedback(fb.getFeedbackId(), newRating, newComment, currentUsername);
                    loadFeedbacks();
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert("Error", "Failed to update feedback.");
                }
            } else {
                showAlert("Warning", "Please provide a rating and comment.");
            }
        }
    }


    /**
     * allows user to delete their own feedback after confirmation
     *
     * @param fb the FeedbackRatings object to delete
     */
    private void deleteFeedback(FeedbackRatings fb) {
        if (currentUsername == null || !fb.getUsername().equals(currentUsername)) {
            showAlert("Error", "You can only delete your own feedback.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Feedback");
        confirm.setHeaderText("Are you sure you want to delete this feedback?");
        confirm.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                DatabaseManager.deleteFeedback(fb.getFeedbackId(), currentUsername);
                loadFeedbacks();
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to delete feedback.");
            }
        }
    }

    /**
     * Displays an alert dialog with coressponding title and message if feedback is not valid
     * @param title   the title of the alert dialog
     * @param message the content message of the alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}