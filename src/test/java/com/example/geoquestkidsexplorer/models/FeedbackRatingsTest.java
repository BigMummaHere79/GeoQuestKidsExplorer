package com.example.geoquestkidsexplorer.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackRatingsTest {

    private FeedbackRatings ratings(){
        FeedbackRatings feedback = new FeedbackRatings();
        feedback.setAvatar("👦");
        feedback.setRating(5);
        feedback.setFeedbackId(12);
        feedback.setComment("Love it");
        feedback.setTimestamp("2025-10-18 07:28:16");
        feedback.setUsername("shani1");
        feedback.setExplorerName("Tori");
        feedback.setParentId(1);
        return feedback;
    }

    @Test
    void getFeedbackId() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals(12,feedbackRatings.getFeedbackId());
    }

    @Test
    void setFeedbackId() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setFeedbackId(1);
        assertEquals(1,feedbackRatings.getFeedbackId());
    }

    @Test
    void getUsername() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals("shani1", feedbackRatings.getUsername());
    }

    @Test
    void setUsername() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setUsername("shani2");
        assertEquals("shani2", feedbackRatings.getUsername());
    }

    @Test
    void getRating() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals(5, feedbackRatings.getRating());
    }

    @Test
    void setRating() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setRating(4);
        assertEquals(4, feedbackRatings.getRating());
    }

    @Test
    void getComment() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals("Love it", feedbackRatings.getComment());
    }

    @Test
    void setComment() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setComment("Easy to navigate");
        assertEquals("Easy to navigate", feedbackRatings.getComment());
    }

    @Test
    void getTimestamp() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals("2025-10-18 07:28:16", feedbackRatings.getTimestamp());
    }

    @Test
    void setTimestamp() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setTimestamp("2025-10-19 17:28:16");
        assertEquals("2025-10-19 17:28:16", feedbackRatings.getTimestamp());
    }

    @Test
    void getParentId() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals(1 , feedbackRatings.getParentId());
    }

    @Test
    void setParentId() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setParentId(2);
        assertEquals(2 , feedbackRatings.getParentId());
    }

    @Test
    void getExplorerName() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals("Tori" , feedbackRatings.getExplorerName());
    }

    @Test
    void setExplorerName() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setExplorerName("Lola");
        assertEquals("Lola" , feedbackRatings.getExplorerName());
    }

    @Test
    void getAvatar() {
        FeedbackRatings feedbackRatings = ratings();
        assertEquals("👦" , feedbackRatings.getAvatar());
    }

    @Test
    void setAvatar() {
        FeedbackRatings feedbackRatings = ratings();
        feedbackRatings.setAvatar("👧");
        assertEquals("👧" , feedbackRatings.getAvatar());
    }
}