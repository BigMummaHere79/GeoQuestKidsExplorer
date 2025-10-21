package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.repositories.*;

/**
 * Factory class for creating database service instances.
 * Implements the Factory pattern to provide a centralized way to instantiate services.
 */
public class DatabaseServiceFactory {
    /**
     * Creates a new instance of {@link UserService}.
     * @return A new UserService instance.
     */
    public static UserService createUserService() {
        return new DatabaseUserService();
    }

    /**
     * Creates a new instance of {@link QuizService}.
     * @return A new QuizService instance.
     */
    public static QuizService createQuizService() {
        return new DatabaseQuizService();
    }

    /**
     * Creates a new instance of {@link FeedbackService}.
     * @return A new FeedbackService instance.
     */
    public static FeedbackService createFeedbackService() {
        return new DatabaseFeedbackService();
    }
}