package com.example.geoquestkidsexplorer.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import javafx.scene.image.WritableImage;

class FlashCardTest {

    // Tests that country is Set
    @Test
    void testSetsCountryName(){
        var img = new WritableImage(1,1);
        var card = new FlashCard("Australia",img);
        assertEquals("Australia",card.countryName());
    }

    // test that image is also set
    @Test
    void testSetsImage(){
        var img = new WritableImage(1,1);
        var card = new FlashCard("New Zealand",img);
        assertSame(img, card.image());
    }

    // test that image can be null, if no controller has been made for that continent
    @Test
    void testImageCanBeNull(){
        var card = new FlashCard("Fiji", null);
        assertNull(card.image());
    }
}