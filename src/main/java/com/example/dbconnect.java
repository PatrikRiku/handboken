package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class dbconnect implements dbconnectrep {
    @Autowired
    private DataSource dataSource;

    public void addHand(HandMessage handmessage) throws Exception{
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("insert into hands(name, text, room, date) values(?, ?, ?, GETDATE());")) {
            ps.setString(1, handmessage.getName());
            ps.setString(2, handmessage.getMessage());
            ps.setString(3, handmessage.getRoom());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public List<HandMessage> getHandMessages() {

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT name, text, room FROM dbo.hands")) {
            List<HandMessage> cards = new ArrayList<>();
            while (rs.next()) cards.add(rsHands(rs));
            return cards;
        } catch (SQLException e) {
            List<HandMessage> cards = new ArrayList<>();
            return cards;
        }
    }

    private HandMessage rsHands(ResultSet rs) throws  SQLException {
        return new HandMessage (
        rs.getString("name"),
        rs.getString("text"),
        rs.getString("room")
        );
    }

    public void changeHand(int hand_id) throws Exception{
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE hands SET fixed = 1 WHERE hand_id=?;")) {
            ps.setInt(1, hand_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
    public int getId() throws Exception{
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT top 1 * FROM hands ORDER BY hand_id desc")) {
            rs.next();
            int returnval = rs.getInt(1);
            return returnval;
        } catch (SQLException e) {
            return 0;
        }

    }
}