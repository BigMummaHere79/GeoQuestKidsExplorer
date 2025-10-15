package com.example.geoquestkidsexplorer.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// NOTE: added import for animation. -- GLENDA --
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Controller for the quiz results dialog box.
 */
public class TestResultsController {

    @FXML private Label scoreLabel;
    @FXML private Label messageLabel;
    @FXML private Button mainButton;
    @FXML private Button secondaryButton;

    // NOTE: added for animation effect. -- GLENDA --
    @FXML private Label trophyLabel; // Changed from ImageView to Label
    @FXML private Canvas confettiCanvas;
    @FXML private Pane animationPane;
    @FXML private Label failLabel;

    private Stage dialogStage;
    private String currentContinent;
    private Runnable onRetry;
    private Runnable onContinue;
    private Runnable onPractice;

    // NOTE: added for additional animation effect. -- GLENDA --
    private Timeline confettiTimeline;
    private List<ConfettiParticle> confettiParticles;

    /**
     * Sets the stage for this dialog, allowing it to be closed.
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the results and configures the buttons based on the score.
     */
    public void setResults(int score, int totalQuestions, String continent, boolean passed) {
        this.currentContinent = continent;
        double percentage = (double) score / totalQuestions * 100;
        scoreLabel.setText(String.format("You scored %d/%d (%.0f%%)!", score, totalQuestions, percentage));

        if (passed) {
            messageLabel.setText("Congratulations! You've unlocked the next continent!");
            mainButton.setText("Continue to next adventure!");
            secondaryButton.setVisible(false); // Hide the second button for a passing score

            // Show and animate trophy emoji
            // Show animationPane with trophy and confetti
            animationPane.setVisible(true);
            animationPane.setManaged(true);
            trophyLabel.setVisible(true);
            trophyLabel.setManaged(true);
            centerTrophyInCanvas();
            startTrophyAnimation();

            // Show and start confetti
            confettiCanvas.setVisible(true);
            confettiCanvas.setManaged(true);
            startConfettiAnimation();

            // Hide failLabel
            failLabel.setVisible(false);
            failLabel.setManaged(false);
        } else {
            messageLabel.setText("You can do it! Keep exploring to improve.");
            mainButton.setText("Repeat test");
            secondaryButton.setText("Go back to practice mode");
            secondaryButton.setVisible(true);

            // Show failLabel
            failLabel.setVisible(true);
            failLabel.setManaged(true);
            startFailAnimation();

            // Hide animationPane, trophy, and confetti
            animationPane.setVisible(false);
            animationPane.setManaged(false);
            trophyLabel.setVisible(false);
            trophyLabel.setManaged(false);
            confettiCanvas.setVisible(false);
            confettiCanvas.setManaged(false);
        }
    }

    /**
     * Sets the actions to be performed when the buttons are clicked.
     */
    public void setActions(Runnable onRetry, Runnable onContinue, Runnable onPractice) {
        this.onRetry = onRetry;
        this.onContinue = onContinue;
        this.onPractice = onPractice;
    }

    @FXML
    private void initialize() {
        // Initialize confetti particles list
        confettiParticles = new ArrayList<>();
        // Reference animationPane to suppress unused warning
        animationPane.getChildren(); // Access children to mark animationPane as used
    }

    /**
     * Centers the trophy label in the confetti canvas.
     */
    private void centerTrophyInCanvas() {
        double canvasWidth = confettiCanvas.getWidth();
        double canvasHeight = confettiCanvas.getHeight();
        // Approximate trophy size (font size 135.0, assuming emoji width ~ height)
        double trophyWidth = 135.0; // Rough estimate for emoji width
        double trophyHeight = 135.0; // Rough estimate for emoji height
        double layoutX = (canvasWidth - trophyWidth) / 2;
        double layoutY = (canvasHeight - trophyHeight) / 2;
        trophyLabel.setLayoutX(layoutX);
        trophyLabel.setLayoutY(layoutY);
    }

    /**
     * Starts the spinning trophy animation with a shining effect.
     */
    private void startTrophyAnimation() {
        // Spinning animation
        RotateTransition rotate = new RotateTransition(Duration.seconds(4), trophyLabel);
        rotate.setAxis(Rotate.Y_AXIS); // Spin around Y-axis for sideways rotation
        rotate.setByAngle(360);
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setAutoReverse(false);
        rotate.play();

        // Shining effect using DropShadow
        DropShadow glow = new DropShadow();
        glow.setColor(Color.GOLD);
        glow.setRadius(20);
        glow.setSpread(0.4);
        trophyLabel.setEffect(glow);
    }

    /**
     * Starts a slight rotation animation for the fail emoji.
     */
    private void startFailAnimation() {
        // Gentle rotation for the fail emoji
        RotateTransition rotate = new RotateTransition(Duration.seconds(5), failLabel);
        rotate.setAxis(Rotate.Y_AXIS); // Rotate around Z-axis for a subtle effect
        rotate.setByAngle(360); // Small tilt
        rotate.setCycleCount(Animation.INDEFINITE);
        rotate.setAutoReverse(true); // Rock back and forth
        rotate.play();
    }

    /**
     * Starts the confetti animation on the canvas.
     */
    private void startConfettiAnimation() {
        Random random = new Random();
        // Create confetti particles
        confettiParticles.clear();
        double canvasWidth = confettiCanvas.getWidth();
        double canvasHeight = confettiCanvas.getHeight();
        for (int i = 0; i < 100; i++) {
            double x = random.nextDouble() * confettiCanvas.getWidth();
            double y = -10; // Start above canvas
            double vx = random.nextDouble() * 4 - 2; // Random horizontal velocity
            double vy = random.nextDouble() * 5 + 2; // Downward velocity
            Color color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            confettiParticles.add(new ConfettiParticle(x, y, vx, vy, color, canvasWidth, canvasHeight));
        }

        // Animation loop for confetti
        confettiTimeline = new Timeline(new KeyFrame(Duration.millis(16), e -> updateConfetti()));
        confettiTimeline.setCycleCount(Animation.INDEFINITE);
        confettiTimeline.play();
    }

    /**
     * Updates confetti particles' positions and redraws them.
     */
    private void updateConfetti() {
        GraphicsContext gc = confettiCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, confettiCanvas.getWidth(), confettiCanvas.getHeight());

        for (ConfettiParticle particle : confettiParticles) {
            particle.update();
            particle.draw(gc);
        }
    }

    /**
     * Represents a single confetti particle.
     */
    private static class ConfettiParticle {
        private double x, y;
        private double vx, vy;
        private final Color color;
        private final double canvasWidth;
        private final double canvasHeight;

        public ConfettiParticle(double x, double y, double vx, double vy, Color color, double canvasWidth, double canvasHeight) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
            this.canvasWidth = canvasWidth;
            this.canvasHeight = canvasHeight;
        }

        public void update() {
            x += vx;
            y += vy;
            vy += 0.05; // Gravity effect
            // Reset particle to top if it falls off
            if (y > canvasHeight) {
                y = -10;
                x = new Random().nextDouble() * canvasWidth;
                vy = new Random().nextDouble() * 5 + 2;
                vx = new Random().nextDouble() * 4 - 2;
            }
        }

        public void draw(GraphicsContext gc) {
            gc.setFill(color);
            gc.fillRect(x, y, 5, 5); // Small rectangles for confetti
        }
    }

    @FXML
    private void handleMainButton(ActionEvent event) {
        if (confettiTimeline != null) {
            confettiTimeline.stop();
        }
        if (mainButton.getText().startsWith("Repeat test")) {
            if (onRetry != null) {
                onRetry.run();
            }
        } else {
            if (onContinue != null) {
                onContinue.run();
            }
        }
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    @FXML
    private void handleSecondaryButton(ActionEvent event) {
        if (confettiTimeline != null) {
            confettiTimeline.stop();
        }
        if (onPractice != null) {
            onPractice.run();
        }
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    /**
     * Handles the close button action to close the dialog window.
     */
    @FXML
    private void handleCloseButton(ActionEvent event) {
        if (confettiTimeline != null) {
            confettiTimeline.stop();
        }
        if (dialogStage != null) {
            dialogStage.close();
        }
    }
}