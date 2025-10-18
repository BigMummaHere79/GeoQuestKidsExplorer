package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.FeedbackRatings;
import com.example.geoquestkidsexplorer.repositories.FeedbackService;
import com.example.geoquestkidsexplorer.utils.DatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FeedbackService.
 */
public class DatabaseFeedbackService extends DatabaseService implements FeedbackService {

    @Override
    public int addFeedback(String username, double rating, String comment, Integer parentId) throws SQLException {
        String sql = "INSERT INTO feedback (username, rating, comment, parent_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, username);
            pstmt.setDouble(2, Double.parseDouble(String.format("%.2f", rating)));
            pstmt.setString(3, comment);
            if (parentId != null) {
                pstmt.setInt(4, parentId);
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    @Override
    public boolean updateFeedback(int feedbackId, double rating, String comment, String username) throws SQLException {
        String sql = "UPDATE feedback SET rating = ?, comment = ? WHERE feedback_id = ? AND username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, Double.parseDouble(String.format("%.2f", rating)));
            pstmt.setString(2, comment);
            pstmt.setInt(3, feedbackId);
            pstmt.setString(4, username);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public boolean deleteFeedback(int feedbackId, String username) throws SQLException {
        String sql = "DELETE FROM feedback WHERE feedback_id = ? AND username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, feedbackId);
            pstmt.setString(2, username);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        }
    }

    @Override
    public List<FeedbackRatings> getTopLevelFeedbacks() throws SQLException {
        List<FeedbackRatings> feedbacks = new ArrayList<>();
        String sql = "SELECT f.*, u.username, u.avatar " +
                "FROM feedback f " +
                "JOIN users u ON f.username = u.username " +
                "WHERE f.parent_id IS NULL " +
                "ORDER BY f.timestamp DESC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                feedbacks.add(extractFeedback(rs));
            }
        }
        return feedbacks;
    }

    @Override
    public List<FeedbackRatings> getReplies(int parentId) throws SQLException {
        List<FeedbackRatings> replies = new ArrayList<>();
        String sql = "SELECT f.*, u.username, u.avatar " +
                "FROM feedback f " +
                "JOIN users u ON f.username = u.username " +
                "WHERE f.parent_id = ? " +
                "ORDER BY f.timestamp ASC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, parentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    replies.add(extractFeedback(rs));
                }
            }
        }
        return replies;
    }

    private FeedbackRatings extractFeedback(ResultSet rs) throws SQLException {
        FeedbackRatings fb = new FeedbackRatings();
        fb.setFeedbackId(rs.getInt("feedback_id"));
        fb.setUsername(rs.getString("username"));
        fb.setRating(rs.getDouble("rating"));
        fb.setComment(rs.getString("comment"));
        fb.setTimestamp(rs.getString("timestamp"));
        fb.setParentId(rs.wasNull() ? null : rs.getInt("parent_id"));
        fb.setAvatar(rs.getString("avatar"));
        return fb;
    }
}