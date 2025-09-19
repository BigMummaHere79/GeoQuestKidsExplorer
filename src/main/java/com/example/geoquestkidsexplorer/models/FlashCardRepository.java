package com.example.geoquestkidsexplorer.models;
import java.util.List;

/** One small interface for the flash card so
 * I can implement it
 * **/
public interface FlashCardRepository
{
    List<FlashCard> loadByRegion(String region);
}
