package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.repositories.QuizDataSource;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * For Mocking
 * In-memory fake datasource for quiz questions
 * Designed for unit tests where the real database is undesriable
 * @see QuizDataSource
 * @see RealQuizDataSource
 * */

// Created a mock for unit testing in order to match the FlashController Logic
// Week 6.1 - 6.3 - Modules

public class MockFlashcardDB {
    private final List<String> names = new ArrayList<>();
    private final List<Image>  images = new ArrayList<>();

    public MockFlashcardDB add(String name, Image image) {
        names.add(name);
        images.add(image);
        return this;
    }
}