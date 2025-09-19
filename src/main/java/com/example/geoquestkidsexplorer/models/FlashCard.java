package com.example.geoquestkidsexplorer.models;
import javafx.scene.image.Image;

/**
 * Moved the code logic that was in Controller into FlashCard model
 * **/
public class FlashCard {
    private final String countryName;
    private final Image image;

    public FlashCard(String countryName, Image image)
    {
        this.countryName = countryName;
        this.image = image;
    }
    public String countryName() {
        return countryName;
    }
    public Image image(){
        return image;
    }
}
