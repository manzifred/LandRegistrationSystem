package controller;

import Dao.LandDao;
import model.land;
import java.util.List;

public class landcontroller {

    private final LandDao landDAO;

    public landcontroller() {
        this.landDAO = new LandDao();
    }

    public boolean addLand(land land) {

        if (land == null) {
            System.err.println("Land object cannot be null.");
            return false;
        }

        if (land.getPlotNumber() == null || land.getPlotNumber().trim().isEmpty()) {
            System.err.println("Plot number is required.");
            return false;
        }

        if (land.getSize() == null || land.getSize().trim().isEmpty()) {
            System.err.println("Land size is required.");
            return false;
        }

        if (land.getLocation() == null || land.getLocation().trim().isEmpty()) {
            System.err.println("Location is required.");
            return false;
        }

        if (land.getOwnerId() <= 0) {
            System.err.println("Valid owner ID is required.");
            return false;
        }

        if (land.getLandUsage() == null || land.getLandUsage().trim().isEmpty()) {
            System.err.println("Land usage type is required.");
            return false;
        }

        return landDAO.addLand(land);
    }

    public List<land> getAllLands() {
        return landDAO.getAllLands();
    }

    public land getLandById(int landId) {
        if (landId <= 0) {
            System.err.println("Invalid land ID.");
            return null;
        }
        return landDAO.getLandById(landId);
    }

    public boolean updateLand(land land) {

        if (land == null) {
            System.err.println("Land object cannot be null.");
            return false;
        }

        if (land.getLandId() <= 0) {
            System.err.println("Invalid land ID.");
            return false;
        }

        if (land.getPlotNumber() == null || land.getPlotNumber().trim().isEmpty()) {
            System.err.println("Plot number is required.");
            return false;
        }

        if (land.getSize() == null || land.getSize().trim().isEmpty()) {
            System.err.println("Land size is required.");
            return false;
        }

        if (land.getLocation() == null || land.getLocation().trim().isEmpty()) {
            System.err.println("Location is required.");
            return false;
        }

        if (land.getOwnerId() <= 0) {
            System.err.println("Valid owner ID is required.");
            return false;
        }

        if (land.getLandUsage() == null || land.getLandUsage().trim().isEmpty()) {
            System.err.println("Land usage type is required.");
            return false;
        }

        return landDAO.updateLand(land);
    }

    public boolean deleteLand(int landId) {
        if (landId <= 0) {
            System.err.println("Invalid land ID.");
            return false;
        }
        return landDAO.deleteLand(landId);
    }

    public List<land> searchLandsByPlotNumber(String plotNumber) {
        if (plotNumber == null || plotNumber.trim().isEmpty()) {
            System.err.println("Plot number cannot be empty.");
            return null;
        }
        return landDAO.searchLandsByPlotNumber(plotNumber.trim());
    }

    public List<land> getLandsByOwnerId(int ownerId) {
        if (ownerId <= 0) {
            System.err.println("Invalid owner ID.");
            return null;
        }
        return landDAO.getLandsByOwnerId(ownerId);
    }
}
