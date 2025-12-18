package Dao;

import model.ownershipHistory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List; // Use List interface for better practice

public class OwnershipHistoryDao {

    /**
     * Records a new land ownership transfer history entry.
     * @param h The OwnershipHistory object to save.
     * @return true if the record was inserted successfully, false otherwise.
     */
    public boolean recordTransfer(ownershipHistory h) {
        String sql = "INSERT INTO ownership_history(land_id, old_owner_id, new_owner_id, remarks, transferred_by) VALUES(?,?,?,?,?)";

        // Use try-with-resources to ensure connection and statement are closed
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            // Set parameters
            pst.setInt(1, h.getLandId());
            pst.setInt(2, h.getOldOwnerId());
            pst.setInt(3, h.getNewOwnerId());
            pst.setString(4, h.getRemarks());
            pst.setInt(5, h.getTransferredBy()); // TransferredBy parameter was missing from constructor in original snippet

            return pst.executeUpdate() > 0;

        } catch (SQLException e) { // Catch the specific SQLException
            System.err.println("Error recording transfer: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves the ownership history for a specific land ID.
     * @param landId The ID of the land to fetch history for.
     * @return An ArrayList of OwnershipHistory objects.
     */
    public List<ownershipHistory> getHistory(int landId) {
        // Switched to PreparedStatement to prevent SQL Injection
        List<ownershipHistory> list = new ArrayList<>(); 
        String sql = "SELECT * FROM ownership_history WHERE land_id = ?"; 

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) { // Use PreparedStatement

            pst.setInt(1, landId); // Safely bind the landId parameter
            
            try(ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Note: Ensure your OwnershipHistory constructor matches these fields
                    ownershipHistory oh = new ownershipHistory(
                        rs.getInt("history_id"),
                        rs.getInt("land_id"),
                        rs.getInt("old_owner_id"),
                        rs.getInt("new_owner_id"),
                        rs.getString("remarks"),
                        rs.getTimestamp("transfer_date"),
                        rs.getInt("transferred_by") // Assuming transferred_by is needed for completeness
                    );
                    list.add(oh);
                }
            }

        } catch (SQLException e) { // Catch the specific SQLException
            System.err.println("Error fetching history: " + e.getMessage());
        }

        return list;
    }
}