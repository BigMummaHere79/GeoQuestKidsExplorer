package com.example.geoquestkidsexplorer.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FlashCardRealRepositoryTest {

    // Test the Region should never be null
    @Test
    void loadByRegionReturnsList(){
        var repo = new FlashCardRealRepository();
        List<FlashCard> cards = repo.loadByRegion("Not yet Loaded");
        assertNotNull(cards);
    }

    // tests that empty region can be found
    @Test
    void loadByRegionReturnEvenEmpty(){
        var repo = new FlashCardRealRepository();
        List<FlashCard> cards = repo.loadByRegion("Not yet Loaded");
        //Expects empty if region not found
        assertTrue(cards.isEmpty());
    }
}