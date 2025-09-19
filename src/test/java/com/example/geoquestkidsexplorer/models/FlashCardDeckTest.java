package com.example.geoquestkidsexplorer.models;

import javafx.scene.image.WritableImage;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Tests all fields within the FlashCard deck
 * Simple assertions created to cover all methods
 * **/

class FlashCardDeckTest {
    private static FlashCard card(String name){
        return new FlashCard(name, new WritableImage(1,1));
    }

    // Test true if the deck is empty
    @Test
    void testEmptyDeckTrue(){
        var deck = new FlashCardDeck(List.of());
        assertTrue(deck.isEmpty());
    }

    //Test assertions should be null if empty
    @Test
    void testNonEmptyDeckIsFalse(){
        var deck = new FlashCardDeck(List.of());
        assertNull(deck.current());
    }

    //Test that the deck displays showingFront()
    @Test
    void testNewDeckShowsFront(){
        var deck = new FlashCardDeck(List.of(card("Australia")));
        assertTrue(deck.showingFront());
    }

    //When flipped desk should assert false when showingFront
    @Test
    void testTogglesShowingFront(){
        var deck = new FlashCardDeck(List.of(card("Australia")));
        deck.flip();
        assertFalse(deck.showingFront());
    }

    @Test
    void testAdvancesToNextIndex(){
        var deck = new FlashCardDeck(List.of(card("Australia"),card("New Zealand")));
        deck.next();
        assertEquals("New Zealand",deck.current().countryName());
    }

    // test next deck
    @Test
    void testNextOnLastReturnsFalse(){
        var deck = new FlashCardDeck(List.of(card("Australia")));
        assertFalse(deck.next());
    }

    // Assert true if deck is reset to front
    @Test
    void testNextToFrontEvenIfWasBack(){
        var deck = new FlashCardDeck(List.of(card("Australia"),card("New Zealand")));
        deck.flip(); // flipped to back
        deck.next(); // Resets to front
        assertTrue(deck.showingFront());
    }

    // test how many number of cards are in deck
    @Test
    void testNumberOfCardSize(){
        var deck = new FlashCardDeck(List.of(card("Australia"),card("New Zealand"),card("Vanuatu"),
                card("Solomon Islands"),card("Papua New Guinea"),card("New Caledonia"),card("Fiji")));
        assertEquals(7, deck.size());
    }

    // test previous()
    @Test
    void testAtStartReturnFalse(){
        var deck = new FlashCardDeck(List.of(card("Australia"),card("New Zealand")));
        assertFalse(deck.previous());
    }

    // test that when First card displays, index is at 0
    @Test
    void testIndexStartsAtZero(){
        var deck = new FlashCardDeck(List.of(card("Australia")));
        assertEquals(0, deck.index());
    }
}
