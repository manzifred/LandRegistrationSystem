package Dao;

import model.land;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LandDao {

    // ADD LAND
    public boolean addLand(land land) {

        String sql = "INSERT INTO lands (plot_number, size, location, owner_id, land_usage) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, land.getPlotNumber());
            pst.setString(2, land.getSize());
            pst.setString(3, land.getLocation());
            pst.setInt(4, land.getOwnerId());
            pst.setString(5, land.getLandUsage());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding land: " + e.getMessage());
            return false;
        }
    }

    // GET ALL LANDS
    public List<land> getAllLands() {

        List<land> list = new ArrayList<>();
        String sql = "SELECT * FROM lands";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultToLand(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching lands: " + e.getMessage());
        }

        return list;
    }

    // GET LAND BY ID
    public land getLandById(int landId) {

        String sql = "SELECT * FROM lands WHERE land_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, landId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return mapResultToLand(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching land: " + e.getMessage());
        }

        return null;
    }

    // UPDATE LAND
    public boolean updateLand(land land) {

        String sql = "UPDATE lands SET plot_number = ?, size = ?, location = ?, owner_id = ?, land_usage = ? "
                   + "WHERE land_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, land.getPlotNumber());
            pst.setString(2, land.getSize());
            pst.setString(3, land.getLocation());
            pst.setInt(4, land.getOwnerId());
            pst.setString(5, land.getLandUsage());
            pst.setInt(6, land.getLandId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating land: " + e.getMessage());
            return false;
        }
    }

    // DELETE LAND
    public boolean deleteLand(int landId) {

        String sql = "DELETE FROM lands WHERE land_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, landId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting land: " + e.getMessage());
            return false;
        }
    }

    // SEARCH BY PLOT NUMBER
    public List<land> searchLandsByPlotNumber(String plotNumber) {

        List<land> list = new ArrayList<>();
        String sql = "SELECT * FROM lands WHERE plot_number LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + plotNumber + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(mapResultToLand(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error searching lands: " + e.getMessage());
        }

        return list;
    }

    // GET LAND BY OWNER ID
    public List<land> getLandsByOwnerId(int ownerId) {

        List<land> list = new ArrayList<>();
        String sql = "SELECT * FROM lands WHERE owner_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, ownerId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                list.add(mapResultToLand(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error fetching lands by owner: " + e.getMessage());
        }

        return list;
    }

    // MAP RESULTSET TO LAND OBJECT
    private land mapResultToLand(ResultSet rs) throws SQLException {
        land land = new land();
        land.setLandId(rs.getInt("land_id"));
        land.setPlotNumber(rs.getString("plot_number"));
        land.setSize(rs.getString("size"));
        land.setLocation(rs.getString("location"));
        land.setOwnerId(rs.getInt("owner_id"));
        land.setLandUsage(rs.getString("land_usage"));
        return land;
    }
}
