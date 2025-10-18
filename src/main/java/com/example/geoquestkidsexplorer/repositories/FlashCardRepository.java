package com.example.geoquestkidsexplorer.repositories;
import com.example.geoquestkidsexplorer.models.FlashCard;

import java.util.List;

/** One small interface for the flash card so
 * I can implement it
 * **/
public interface FlashCardRepository
{
    List<FlashCard> loadByRegion(String region);
}
