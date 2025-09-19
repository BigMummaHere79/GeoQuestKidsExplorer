package com.example.geoquestkidsexplorer.models;
import javafx.scene.image.Image;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Moved what was in controller into here
 * Only what Database uses
 * Seperate database logic into a new class
 * **/

public class FlashCardRealRepository implements FlashCardRepository {
    private static final String DATABASE_URL = "jdbc:sqlite:geoquest.db";

    @Override
    public List<FlashCard> loadByRegion(String region) {
        List<FlashCard> countries = new ArrayList<>();
        String sql = "SELECT country, country_picture FROM countries WHERE continent = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, region);

            try(ResultSet rs = pstmt.executeQuery()){
                while (rs.next()) {
                    String name = rs.getString("country");
                    byte[] imgBytes = rs.getBytes("country_picture");
                    Image img = null;
                    if (imgBytes != null && imgBytes.length > 0) {
                        img = new Image(new ByteArrayInputStream(imgBytes));
                    }
                    countries.add(new FlashCard(name, img));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Collections.shuffle(countries); // random order
            return countries;
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
