package Dao;

import model.Owner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for Owner CRUD operations
 * @author MANZI 9Z
 */
public class ownerDao {

    // Create - Add new owner
    public boolean addOwner(Owner owner) {
        String sql = "INSERT INTO owners(full_name, national_id, dob, contact, address) VALUES(?,?,?,?,?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, owner.getFullName());
            pst.setString(2, owner.getNationalId());
            pst.setDate(3, owner.getDob());
            pst.setString(4, owner.getContact());
            pst.setString(5, owner.getAddress());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error adding owner: " + e.getMessage());
            return false;
        }
    }

    // Read - Get all owners
    public List<Owner> getOwners() {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM owners ORDER BY owner_id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Owner owner = new Owner(
                    rs.getInt("owner_id"),
                    rs.getString("full_name"),
                    rs.getString("national_id"),
                    rs.getDate("dob"),
                    rs.getString("contact"),
                    rs.getString("address")
                );
                owners.add(owner);
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving owners: " + e.getMessage());
        }

        return owners;
    }

    // Read - Get owner by ID
    public Owner getOwnerById(int ownerId) {
        String sql = "SELECT * FROM owners WHERE owner_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, ownerId);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Owner(
                        rs.getInt("owner_id"),
                        rs.getString("full_name"),
                        rs.getString("national_id"),
                        rs.getDate("dob"),
                        rs.getString("contact"),
                        rs.getString("address")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting owner: " + e.getMessage());
        }

        return null;
    }

    // Update - Update owner information
    public boolean updateOwner(Owner owner) {
        String sql = "UPDATE owners SET full_name=?, national_id=?, dob=?, contact=?, address=? WHERE owner_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, owner.getFullName());
            pst.setString(2, owner.getNationalId());
            pst.setDate(3, owner.getDob());
            pst.setString(4, owner.getContact());
            pst.setString(5, owner.getAddress());
            pst.setInt(6, owner.getOwnerId());

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating owner: " + e.getMessage());
            return false;
        }
    }

    // Delete - Remove owner
    public boolean deleteOwner(int ownerId) {
        String sql = "DELETE FROM owners WHERE owner_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, ownerId);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting owner: " + e.getMessage());
            return false;
        }
    }

    // Search owners by name
    public List<Owner> searchOwnersByName(String name) {
        List<Owner> owners = new ArrayList<>();
        String sql = "SELECT * FROM owners WHERE full_name ILIKE ? ORDER BY owner_id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, "%" + name + "%");

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Owner owner = new Owner(
                        rs.getInt("owner_id"),
                        rs.getString("full_name"),
                        rs.getString("national_id"),
                        rs.getDate("dob"),
                        rs.getString("contact"),
                        rs.getString("address")
                    );
                    owners.add(owner);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error searching owners: " + e.getMessage());
        }

        return owners;
    }
}