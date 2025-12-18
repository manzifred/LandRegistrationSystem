package model;

public class land {

    private int landId;
    private String plotNumber;
    private String size;
    private String location;
    private int ownerId;
    private String landUsage;

    public land() {}

    public land(String plotNumber, String size, String location, int ownerId, String landUsage) {
        this.plotNumber = plotNumber;
        this.size = size;
        this.location = location;
        this.ownerId = ownerId;
        this.landUsage = landUsage;
    }

    public land(int landId, String plotNumber, String size, String location, int ownerId, String landUsage) {
        this.landId = landId;
        this.plotNumber = plotNumber;
        this.size = size;
        this.location = location;
        this.ownerId = ownerId;
        this.landUsage = landUsage;
    }

    public int getLandId() { return landId; }
    public void setLandId(int landId) { this.landId = landId; }

    public String getPlotNumber() { return plotNumber; }
    public void setPlotNumber(String plotNumber) { this.plotNumber = plotNumber; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public String getLandUsage() { return landUsage; }
    public void setLandUsage(String landUsage) { this.landUsage = landUsage; }
}
