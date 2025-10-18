package com.example.geoquestkidsexplorer.controllers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

/**
 * Abstract base class for fun facts controllers, encapsulating common fact display logic.
 * Subclasses override for specific fact loading (polymorphism).
 */
public abstract class FunFactsBaseController extends BaseController {

    /**
     * Creates a styled VBox for a single fun fact (encapsulated to avoid duplication).
     *
     * @param text the fact text.
     * @param icon the icon emoji.
     * @return the styled VBox.
     */
    protected VBox createFactVBox(String text, String icon) {
        VBox factBox = new VBox(0);
        factBox.setPrefWidth(380.0);
        factBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10px; -fx-border-color: #e0e0e0; " +
                "-fx-border-width: 1px; -fx-border-radius: 10px; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.05), 3, 0, 0, 2);");
        factBox.setAlignment(Pos.CENTER);
        factBox.setFillWidth(true);

        Label iconLabel = new Label(icon);
        iconLabel.setStyle("-fx-font-size: 50px; -fx-padding: 10px 10px 0 10px;");
        iconLabel.setAlignment(Pos.CENTER);
        iconLabel.setPrefWidth(380.0);
        VBox.setVgrow(iconLabel, Priority.NEVER);

        Label textLabel = new Label(text);
        textLabel.setWrapText(true);
        textLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555555; -fx-padding: 5px 15px 15px 15px;");
        textLabel.setAlignment(Pos.CENTER);
        textLabel.setPrefWidth(360.0);
        VBox.setVgrow(textLabel, Priority.NEVER);

        factBox.getChildren().addAll(iconLabel, textLabel);
        VBox.setMargin(factBox, new Insets(5, 0, 5, 0));
        return factBox;
    }

    /**
     * Abstract method for subclasses to load facts (polymorphism).
     */
    protected abstract void loadFacts();
}