package com.example.geoquestkidsexplorer.models;

import com.example.geoquestkidsexplorer.fakes.MockFlashCardRepo;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** Mocks hardoced seeds in replica of data entry into database**/
public class MockFlashCardRepoTest {
    @Test
    void testLoadByRegion(){
        var repo = new MockFlashCardRepo()
                .add("Australia",new WritableImage(1,1))
                .add("New Zealand",null);

        List<FlashCard> cards = repo.loadByRegion("Oceania");
        assertEquals(2,cards.size());
    }

    @Test
    void testLoadCountryName(){
        var repo = new MockFlashCardRepo()
                .add("Australia",new WritableImage(1,1))
                .add("New Zealand",null);

        List<FlashCard> cards = repo.loadByRegion("Oceania");
        assertEquals("Australia",cards.get(0).countryName());
    }

    @Test
    void testHandlesEmptyRegions(){
        var repo = new MockFlashCardRepo();
        List<FlashCard> cards = cards = repo.loadByRegion("Europe");
        assertTrue(cards.isEmpty());
    }
}
