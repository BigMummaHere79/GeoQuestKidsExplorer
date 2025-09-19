package com.example.geoquestkidsexplorer.fakes;

import com.example.geoquestkidsexplorer.models.FlashCard;
import com.example.geoquestkidsexplorer.controllers.FlashcardsController;
import com.example.geoquestkidsexplorer.models.FlashCardRepository;
import javafx.scene.image.Image;

import java.util.List;
import java.util.ArrayList;

/**
 * Replicates data that would input into database
 * Testing those objects tthrough this
 * **/

public class MockFlashCardRepo implements FlashCardRepository {
    private final List<FlashCard> cards = new ArrayList<>();

    public MockFlashCardRepo add(String name, Image image){
        cards.add(new FlashCard(name, image));
        return this;
    }

    @Override
    public List<FlashCard> loadByRegion(String region) {
        return new ArrayList<>(cards);
    }
}
