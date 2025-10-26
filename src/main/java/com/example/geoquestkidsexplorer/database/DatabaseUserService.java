package com.example.geoquestkidsexplorer.database;

import com.example.geoquestkidsexplorer.models.UserProfile;
import com.example.geoquestkidsexplorer.models.UserProfileBuilder;
import com.example.geoquestkidsexplorer.repositories.UserService;
import com.example.geoquestkidsexplorer.utils.DatabaseService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Concrete implementation of {@link UserService} using SQLite database.
 * Extends {@link DatabaseService} for shared connection logic.
 * Encapsulates user-specific queries and uses {@link UserProfileBuilder} for profile creation.
 */
public class DatabaseUserService extends DatabaseService implements UserService {

    /**
     * Inserts a new user into the database.
     * @param username Username.
     * @param email    Email.
     * @param password Password.
     * @param avatar   Avatar.
     * @param role     Role.
     * @throws RuntimeException If username or email exists or insertion fails.
     */
    @Override
    public void insertUser(String username, String email, String password, String avatar, String role) {
        if (userExists(username, email)) {
            throw new RuntimeException("Username or email already exists.");
        }

        String sql = "INSERT INTO users(username, email, password, avatar, level, role) VALUES (?,?,?,?,1,?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, Objects.requireNonNullElse(avatar, "🙂"));
            ps.setString(5, Objects.requireNonNullElse(role, "user"));
            int rows = ps.executeUpdate();
            System.out.println("insertUser: User " + username + " inserted with level 1, rows affected: " + rows);

            // Verify insertion (retained logic)
            verifyUserLevel(conn, username, 1);
        } catch (SQLException e) {
            System.err.println("insertUser error: " + e.getMessage());
            throw new RuntimeException("Failed to insert user: " + e.getMessage());
        }
    }

    /**
     * Verifies the user's level after insertion.
     * @param conn         Database connection.
     * @param username     Username.
     * @param expectedLevel Expected level.
     * @throws SQLException If verification fails.
     */
    private void verifyUserLevel(Connection conn, String username, int expectedLevel) throws SQLException {
        try (PreparedStatement verifyPs = conn.prepareStatement("SELECT level FROM users WHERE username = ?")) {
            verifyPs.setString(1, username);
            try (ResultSet rs = verifyPs.executeQuery()) {
                if (rs.next()) {
                    int level = rs.getInt("level");
                    if (rs.wasNull() || level != expectedLevel) {
                        System.err.println("insertUser: User " + username + " has unexpected level: " + (rs.wasNull() ? "NULL" : level));
                    } else {
                        System.out.println("insertUser: Verified user " + username + " has level: " + expectedLevel);
                    }
                } else {
                    System.err.println("insertUser: User " + username + " not found after insertion");
                }
            }
        }
    }

    /**
     * Checks if a user exists by username or email.
     * @param username Username.
     * @param email    Email.
     * @return True if user exists, false otherwise.
     */
    @Override
    public boolean userExists(String username, String email) {
        String sql = "SELECT 1 FROM users WHERE username = ? OR email = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("userExists error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validates login credentials.
     * @param email    Email.
     * @param password Password.
     * @return True if valid, false otherwise.
     */
    @Override
    public boolean validateLogin(String email, String password) {
        String sql = "SELECT 1 FROM users WHERE email = ? AND password = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password); // Note: Use hashed passwords in production
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Login validation error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a user profile by username.
     * @param username Username.
     * @return UserProfile or null if not found.
     */
    @Override
    public UserProfile getUserProfileByUsername(String username) {
        String sql = "SELECT username, email, avatar, level, role FROM users WHERE username = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UserProfileBuilder()
                            .withUsername(rs.getString("username"))
                            .withEmail(rs.getString("email"))
                            .withAvatar(rs.getString("avatar"))
                            .withLevel(rs.getInt("level"))
                            .withRole(rs.getString("role"))
                            .build();
                }
            }
        } catch (SQLException e) {
            System.err.println("getUserProfileByUsername error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves user details (username, avatar) by username.
     * @param username Username.
     * @return Array of {username, avatar} or null if not found.
     */
    @Override
    public String[] getUserDetails(String username) {
        String sql = "SELECT username, avatar FROM users WHERE username = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[]{rs.getString("username"), rs.getString("avatar")};
                }
            }
        } catch (SQLException e) {
            System.err.println("getUserDetails error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets username by email.
     * @param email Email.
     * @return Username or null if not found.
     */
    @Override
    public String getUsernameByEmail(String email) {
        String sql = "SELECT username FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("username");
            }
        } catch (SQLException e) {
            System.err.println("getUsernameByEmail error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Gets avatar by email.
     * @param email Email.
     * @return Avatar or null if not found.
     */
    @Override
    public String getAvatarByEmail(String email) {
        String sql = "SELECT avatar FROM users WHERE email = ? LIMIT 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("avatar");
            }
        } catch (SQLException e) {
            System.err.println("getAvatarByEmail error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Fixes a user's level if null, setting it to 1.
     * @param username Username.
     */
    @Override
    public void fixUserLevel(String username) {
        String sql = "SELECT level FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int level = rs.getInt("level");
                    if (rs.wasNull() || level < 1) {
                        String updateSql = "UPDATE users SET level = 1 WHERE username = ?";
                        try (PreparedStatement updatePs = conn.prepareStatement(updateSql)) {
                            updatePs.setString(1, username);
                            int rows = updatePs.executeUpdate();
                            System.out.println("fixUserLevel: Updated level to 1 for user " + username + ", rows affected: " + rows);
                        }
                    }
                } else {
                    System.err.println("fixUserLevel: No user found for username: " + username);
                }
            }
        } catch (SQLException e) {
            System.err.println("fixUserLevel error: " + e.getMessage());
        }
    }

    /**
     * Saves a quiz result and updates user level if passed.
     * @param username        Username.
     * @param level           Current level.
     * @param scorePercentage Score percentage.
     * @return True if successful, false on error.
     */
    @Override
    public boolean saveQuizResultAndUpdateLevel(String username, int level, double scorePercentage) {
        boolean isPassing = scorePercentage >= 80.0;
        String status = isPassing ? "Pass" : "Fail";

        String selectSql = "SELECT grades, status FROM results WHERE username = ? AND level = ?";
        Double oldGrade = null;
        String oldStatus = null;

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // Check existing
            try (PreparedStatement selectPs = conn.prepareStatement(selectSql)) {
                selectPs.setString(1, username);
                selectPs.setInt(2, level);
                try (ResultSet rs = selectPs.executeQuery()) {
                    if (rs.next()) {
                        oldGrade = rs.getDouble("grades");
                        oldStatus = rs.getString("status");
                    }
                }
            }

            // Decide if update needed
            boolean shouldUpdate = oldGrade == null || scorePercentage > oldGrade;
            boolean shouldIncrementLevel = isPassing && (oldStatus == null || !oldStatus.equals("Pass"));

            if (shouldUpdate) {
                String upsertSql = """
                INSERT OR REPLACE INTO results (username, level, grades, status)
                VALUES (?, ?, ?, ?)
            """;
                try (PreparedStatement upsertPs = conn.prepareStatement(upsertSql)) {
                    upsertPs.setString(1, username);
                    upsertPs.setInt(2, level);
                    upsertPs.setDouble(3, scorePercentage);
                    upsertPs.setString(4, status);
                    upsertPs.executeUpdate();
                }
            }

            if (shouldIncrementLevel) {
                String updateLevelSql = "UPDATE users SET level = level + 1 WHERE username = ?";
                try (PreparedStatement updatePs = conn.prepareStatement(updateLevelSql)) {
                    updatePs.setString(1, username);
                    updatePs.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("saveQuizResultAndUpdateLevel error: " + e.getMessage());
            return false;
        }
    }
}