package Dao;

import model.officer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfficerDao {

    /**
     * Add a new officer
     */
    public boolean addOfficer(officer officer) {

        String sql = "INSERT INTO officers(username, password, full_name, role) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, officer.getUsername());
            pst.setString(2, officer.getPassword());
            pst.setString(3, officer.getFullName());
            pst.setString(4, officer.getRole());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding officer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieve all officers
     */
    public List<officer> getAllOfficers() {

        List<officer> list = new ArrayList<>();
        String sql = "SELECT * FROM officers";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                officer o = new officer(
                        rs.getInt("officer_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("full_name"),
                        rs.getString("role")
                );
                list.add(o);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching officers: " + e.getMessage());
        }

        return list;
    }

    /**
     * Retrieve officer by ID
     */
    public officer getOfficerById(int officerId) {

        String sql = "SELECT * FROM officers WHERE officer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, officerId);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    return new officer(
                            rs.getInt("officer_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("full_name"),
                            rs.getString("role")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error fetching officer: " + e.getMessage());
        }

        return null;
    }

    /**
     * Update officer
     */
    public boolean updateOfficer(officer officer) {

        String sql = "UPDATE officers SET username = ?, password = ?, full_name = ?, role = ? WHERE officer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, officer.getUsername());
            pst.setString(2, officer.getPassword());
            pst.setString(3, officer.getFullName());
            pst.setString(4, officer.getRole());
            pst.setInt(5, officer.getOfficerId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating officer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete officer
     */
    public boolean deleteOfficer(int officerId) {

        String sql = "DELETE FROM officers WHERE officer_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, officerId);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting officer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Officer Login (username + password)
     */
    public officer login(String username, String password) {

        String sql = "SELECT * FROM officers WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {
                    return new officer(
                            rs.getInt("officer_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("full_name"),
                            rs.getString("role")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }

        return null;
    }
}
