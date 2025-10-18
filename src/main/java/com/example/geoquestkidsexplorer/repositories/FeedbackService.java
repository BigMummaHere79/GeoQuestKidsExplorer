package com.example.geoquestkidsexplorer.repositories;

import com.example.geoquestkidsexplorer.models.FeedbackRatings;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for feedback CRUD operations.
 */
public interface FeedbackService {
    /**
     * Adds feedback or reply.
     * @param username User.
     * @param rating Rating.
     * @param comment Comment.
     * @param parentId Parent ID or null.
     * @return Generated ID or -1.
     * @throws SQLException On error.
     */
    int addFeedback(String username, double rating, String comment, Integer parentId) throws SQLException;

    /**
     * Updates feedback if owned.
     * @param feedbackId ID.
     * @param rating New rating.
     * @param comment New comment.
     * @param username Owner.
     * @return true if updated.
     * @throws SQLException On error.
     */
    boolean updateFeedback(int feedbackId, double rating, String comment, String username) throws SQLException;

    /**
     * Deletes feedback if owned.
     * @param feedbackId ID.
     * @param username Owner.
     * @return true if deleted.
     * @throws SQLException On error.
     */
    boolean deleteFeedback(int feedbackId, String username) throws SQLException;

    /**
     * Gets top-level feedbacks.
     * @return List.
     * @throws SQLException On error.
     */
    List<FeedbackRatings> getTopLevelFeedbacks() throws SQLException;

    /**
     * Gets replies for parent.
     * @param parentId Parent ID.
     * @return List.
     * @throws SQLException On error.
     */
    List<FeedbackRatings> getReplies(int parentId) throws SQLException;
}