package controller;

import Dao.OwnershipHistoryDao;
import model.ownershipHistory;

import java.util.List;

/**
 * Controller for handling ownership transfer-related operations.
 * Author: Manzi 9z
 */
public class ownershipHistorycontroller {

    private final OwnershipHistoryDao historyDAO;

    public ownershipHistorycontroller() {
        this.historyDAO = new OwnershipHistoryDao();
    }

    /**
     * Records a new ownership transfer.
     *
     * @param history OwnershipHistory object with transfer details
     * @return true if saved successfully, false otherwise
     */
    public boolean recordTransfer(ownershipHistory history) {

        if (history == null) {
            System.err.println("History object cannot be null.");
            return false;
        }

        if (history.getLandId() <= 0) {
            System.err.println("A valid land ID is required.");
            return false;
        }

        if (history.getNewOwnerId() <= 0) {
            System.err.println("New owner ID must be valid.");
            return false;
        }

        return historyDAO.recordTransfer(history);
    }

    /**
     * Fetches all ownership history records for a given land.
     *
     * @param landId ID of the land
     * @return a list of ownership history entries
     */
    public List<ownershipHistory> getHistoryByLand(int landId) {

        if (landId <= 0) {
            System.err.println("Invalid land ID.");
            return null;
        }

        return historyDAO.getHistory(landId);
    }
}
