package controller;

import Dao.OfficerDao;
import model.officer;

/**
 * Controller for handling officer authentication and related operations.
 * Author: Manzi 9z
 */
public class officercontroller {

    private final OfficerDao officerDAO;

    public officercontroller() {
        this.officerDAO = new OfficerDao();
    }

    /**
     * Validates login input and attempts authentication using OfficerDAO.
     *
     * @param username officer's username
     * @param password officer's password
     * @return Officer object if login is successful, otherwise null
     */
    public officer login(String username, String password) {

        // Validate username
        if (username == null || username.trim().isEmpty()) {
            System.err.println("Username cannot be empty.");
            return null;
        }

        // Validate password
        if (password == null || password.trim().isEmpty()) {
            System.err.println("Password cannot be empty.");
            return null;
        }

        // Clean whitespace
        String cleanedUsername = username.trim();
        String cleanedPassword = password.trim();

        // Call DAO login method
        return officerDAO.login(cleanedUsername, cleanedPassword);
    }
}
