package controller;

import Dao.ownerDao;
import model.Owner;

import java.util.List;

/**
 * Controller for handling Owner-related operations.
 * Author: Manzi 9z
 */
public class ownercontroller {

    private final ownerDao ownerDAO;

    public ownercontroller() {
        this.ownerDAO = new ownerDao();
    }

    /**
     * Adds a new owner after validating the input.
     *
     * @param owner Owner object to be added
     * @return true if added successfully, false otherwise
     */
    public boolean addOwner(Owner owner) {

        if (owner == null) {
            System.err.println("Owner object cannot be null.");
            return false;
        }

        if (owner.getFullName() == null || owner.getFullName().trim().isEmpty()) {
            System.err.println("Full name is required.");
            return false;
        }

        if (owner.getNationalId() == null || owner.getNationalId().trim().isEmpty()) {
            System.err.println("National ID is required.");
            return false;
        }

        return ownerDAO.addOwner(owner);
    }

    /**
     * Retrieves a list of all owners.
     *
     * @return List of Owner objects
     */
    public List<Owner> getAllOwners() {
        return ownerDAO.getOwners();
    }

    /**
     * Updates an existing owner's information.
     *
     * @param owner Owner object with updated details
     * @return true if update was successful, false otherwise
     */
    public boolean updateOwner(Owner owner) {

        if (owner == null) {
            System.err.println("Owner object cannot be null.");
            return false;
        }

        if (owner.getOwnerId() <= 0) {
            System.err.println("Invalid owner ID.");
            return false;
        }

        return ownerDAO.updateOwner(owner);
    }

    /**
     * Deletes an owner using their ID.
     *
     * @param ownerId ID of the owner to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteOwner(int ownerId) {

        if (ownerId <= 0) {
            System.err.println("Invalid owner ID.");
            return false;
        }

        return ownerDAO.deleteOwner(ownerId);
    }
}
