package model;

import java.sql.Timestamp;

public class ownershipHistory {

    private int historyId;
    private int landId;
    private int oldOwnerId;
    private int newOwnerId;
    private String remarks;
    private Timestamp transferDate;
    private int transferredBy;   // <-- Missing field added

    // Empty constructor
    public ownershipHistory() {}

    // Constructor for inserting
    public ownershipHistory(int landId, int oldOwnerId, int newOwnerId, String remarks, int transferredBy) {
        this.landId = landId;
        this.oldOwnerId = oldOwnerId;
        this.newOwnerId = newOwnerId;
        this.remarks = remarks;
        this.transferredBy = transferredBy;   // <-- FIXED
    }

    // Constructor for retrieving
    public ownershipHistory(int historyId, int landId, int oldOwnerId, int newOwnerId,
                            String remarks, Timestamp transferDate, int transferredBy) {
        this.historyId = historyId;
        this.landId = landId;
        this.oldOwnerId = oldOwnerId;
        this.newOwnerId = newOwnerId;
        this.remarks = remarks;
        this.transferDate = transferDate;
        this.transferredBy = transferredBy;   // <-- FIXED
    }

    // Getters and Setters
    public int getHistoryId() { return historyId; }
    public void setHistoryId(int historyId) { this.historyId = historyId; }

    public int getLandId() { return landId; }
    public void setLandId(int landId) { this.landId = landId; }

    public int getOldOwnerId() { return oldOwnerId; }
    public void setOldOwnerId(int oldOwnerId) { this.oldOwnerId = oldOwnerId; }

    public int getNewOwnerId() { return newOwnerId; }
    public void setNewOwnerId(int newOwnerId) { this.newOwnerId = newOwnerId; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Timestamp getTransferDate() { return transferDate; }
    public void setTransferDate(Timestamp transferDate) { this.transferDate = transferDate; }

    public int getTransferredBy() { return transferredBy; }          // <-- Required getter
    public void setTransferredBy(int transferredBy) { this.transferredBy = transferredBy; }  // <-- Required setter
}
