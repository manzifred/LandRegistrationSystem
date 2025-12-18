package view;

import controller.ownershipHistorycontroller;
import controller.landcontroller;
import controller.ownercontroller;
import model.ownershipHistory;
import model.land;
import model.Owner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ownership Transfer Form - Records land ownership transfers
 * @author MANZI 9Z
 */
public class OwnershipTransferform extends JFrame {
    
    private JComboBox<String> cmbLand, cmbOldOwner, cmbNewOwner;
    private JTextArea txtRemarks;
    private JButton btnTransfer, btnClear, btnRefresh;
    private JTable tblHistory;
    private DefaultTableModel tableModel;
    private ownershipHistorycontroller historyController;
    private landcontroller landController;
    private ownercontroller ownerController;
    private List<land> lands;
    private List<Owner> owners;
    
    public OwnershipTransferform() {
        historyController = new ownershipHistorycontroller();
        landController = new landcontroller();
        ownerController = new ownercontroller();
        initComponents();
        loadData();
        loadHistoryData();
    }
    
    private void initComponents() {
        setTitle("Ownership Transfer");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel lblTitle = new JLabel("Land Ownership Transfer");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(350, 10, 300, 30);
        mainPanel.add(lblTitle);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 50, 950, 250);
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Transfer Information"));
        
        // Land Selection
        JLabel lblLand = new JLabel("Select Land*:");
        lblLand.setBounds(20, 30, 120, 25);
        formPanel.add(lblLand);
        
        cmbLand = new JComboBox<>();
        cmbLand.setBounds(140, 30, 380, 25);
        cmbLand.addActionListener(e -> updateOldOwner());
        formPanel.add(cmbLand);
        
        JButton btnViewLand = new JButton("View Details");
        btnViewLand.setBounds(530, 30, 120, 25);
        btnViewLand.addActionListener(e -> viewLandDetails());
        formPanel.add(btnViewLand);
        
        // Old Owner
        JLabel lblOldOwner = new JLabel("Current Owner*:");
        lblOldOwner.setBounds(20, 70, 120, 25);
        formPanel.add(lblOldOwner);
        
        cmbOldOwner = new JComboBox<>();
        cmbOldOwner.setBounds(140, 70, 380, 25);
        cmbOldOwner.setEnabled(false);
        formPanel.add(cmbOldOwner);
        
        // New Owner
        JLabel lblNewOwner = new JLabel("New Owner*:");
        lblNewOwner.setBounds(20, 110, 120, 25);
        formPanel.add(lblNewOwner);
        
        cmbNewOwner = new JComboBox<>();
        cmbNewOwner.setBounds(140, 110, 380, 25);
        formPanel.add(cmbNewOwner);
        
        // Remarks
        JLabel lblRemarks = new JLabel("Remarks:");
        lblRemarks.setBounds(20, 150, 120, 25);
        formPanel.add(lblRemarks);
        
        txtRemarks = new JTextArea();
        txtRemarks.setLineWrap(true);
        txtRemarks.setWrapStyleWord(true);
        JScrollPane remarksScroll = new JScrollPane(txtRemarks);
        remarksScroll.setBounds(140, 150, 380, 70);
        formPanel.add(remarksScroll);
        
        mainPanel.add(formPanel);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBounds(20, 310, 950, 50);
        buttonPanel.setBackground(Color.WHITE);
        
        btnTransfer = new JButton("Transfer Ownership");
        btnTransfer.setBackground(new Color(0, 153, 51));
        btnTransfer.setForeground(Color.WHITE);
        btnTransfer.setFocusPainted(false);
        btnTransfer.setPreferredSize(new Dimension(180, 35));
        btnTransfer.addActionListener(e -> transferOwnership());
        buttonPanel.add(btnTransfer);
        
        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(100, 100, 100));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setPreferredSize(new Dimension(120, 35));
        btnClear.addActionListener(e -> clearFields());
        buttonPanel.add(btnClear);
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(0, 102, 204));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setPreferredSize(new Dimension(120, 35));
        btnRefresh.addActionListener(e -> {
            loadData();
            loadHistoryData();
        });
        buttonPanel.add(btnRefresh);
        
        mainPanel.add(buttonPanel);
        
        // History Table
        JLabel lblHistory = new JLabel("Transfer History");
        lblHistory.setFont(new Font("Arial", Font.BOLD, 16));
        lblHistory.setBounds(20, 370, 200, 25);
        mainPanel.add(lblHistory);
        
        String[] columns = {"History ID", "Land ID", "Old Owner ID", "New Owner ID", "Remarks", "Transfer Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblHistory = new JTable(tableModel);
        tblHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(tblHistory);
        scrollPane.setBounds(20, 400, 950, 200);
        mainPanel.add(scrollPane);
        
        add(mainPanel);
    }
    
    private void loadData() {
        // Load Lands
        cmbLand.removeAllItems();
        lands = landController.getAllLands();
        for (land land : lands) {
            cmbLand.addItem(land.getLandId() + " - " + land.getPlotNumber() + " (" + land.getLocation() + ")");
        }
        
        // Load Owners
        cmbNewOwner.removeAllItems();
        owners = ownerController.getAllOwners();
        for (Owner owner : owners) {
            cmbNewOwner.addItem(owner.getOwnerId() + " - " + owner.getFullName());
        }
    }
    
    private void updateOldOwner() {
        if (cmbLand.getSelectedIndex() != -1) {
            String selected = (String) cmbLand.getSelectedItem();
            int landId = Integer.parseInt(selected.split(" - ")[0]);
            
            land land = landController.getLandById(landId);
            if (land != null) {
                Owner owner = ownerController.getAllOwners().stream()
                    .filter(o -> o.getOwnerId() == land.getOwnerId())
                    .findFirst()
                    .orElse(null);
                
                cmbOldOwner.removeAllItems();
                if (owner != null) {
                    cmbOldOwner.addItem(owner.getOwnerId() + " - " + owner.getFullName());
                }
            }
        }
    }
    
    private void viewLandDetails() {
        if (cmbLand.getSelectedIndex() != -1) {
            String selected = (String) cmbLand.getSelectedItem();
            int landId = Integer.parseInt(selected.split(" - ")[0]);
            
            land land = landController.getLandById(landId);
            if (land != null) {
                String details = String.format(
                    "Land Details:\n\n" +
                    "Plot Number: %s\n" +
                    "Size: %s sq.m\n" +
                    "Location: %s\n" +
                    "Current Owner ID: %d\n" +
                    "Usage: %s",
                    land.getPlotNumber(),
                    land.getSize(),
                    land.getLocation(),
                    land.getOwnerId(),
                    land.getLandUsage()
                );
                
                JOptionPane.showMessageDialog(this, details, "Land Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void transferOwnership() {
        if (!validateInputs()) {
            return;
        }
        
        String landSelected = (String) cmbLand.getSelectedItem();
        int landId = Integer.parseInt(landSelected.split(" - ")[0]);
        
        String oldOwnerSelected = (String) cmbOldOwner.getSelectedItem();
        int oldOwnerId = Integer.parseInt(oldOwnerSelected.split(" - ")[0]);
        
        String newOwnerSelected = (String) cmbNewOwner.getSelectedItem();
        int newOwnerId = Integer.parseInt(newOwnerSelected.split(" - ")[0]);
        
        // Business validation: Cannot transfer to same owner
        if (oldOwnerId == newOwnerId) {
            JOptionPane.showMessageDialog(this, 
                "Cannot transfer to the same owner!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to transfer this land ownership?", 
            "Confirm Transfer", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // Record transfer history
            ownershipHistory history = new ownershipHistory(
                landId,
                oldOwnerId,
                newOwnerId,
                txtRemarks.getText().trim(),
                0  // transferredBy can be set to officer ID if needed
            );
            
            boolean historySuccess = historyController.recordTransfer(history);
            
            if (historySuccess) {
                // Update land owner
                land land = landController.getLandById(landId);
                land.setOwnerId(newOwnerId);
                boolean updateSuccess = landController.updateLand(land);
                
                if (updateSuccess) {
                    JOptionPane.showMessageDialog(this, 
                        "Ownership transferred successfully!", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadHistoryData();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Failed to update land ownership!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to record transfer history!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validateInputs() {
        // Business validation: Land must be selected
        if (cmbLand.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a land!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Business validation: Old owner must exist
        if (cmbOldOwner.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Current owner information not found!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Business validation: New owner must be selected
        if (cmbNewOwner.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a new owner!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void loadHistoryData() {
        tableModel.setRowCount(0);
        List<ownershipHistory> historyList = historyController.getHistoryByLand(-1);  // Get all
        
        // Since getHistoryByLand requires valid ID, use getAllHistory if available
        // For now, let's assume we're loading based on current selection
        if (cmbLand.getSelectedIndex() != -1) {
            String selected = (String) cmbLand.getSelectedItem();
            int landId = Integer.parseInt(selected.split(" - ")[0]);
            historyList = historyController.getHistoryByLand(landId);
        }
        
        for (ownershipHistory history : historyList) {
            Object[] row = {
                history.getHistoryId(),
                history.getLandId(),
                history.getOldOwnerId(),
                history.getNewOwnerId(),
                history.getRemarks(),
                history.getTransferDate()
            };
            tableModel.addRow(row);
        }
    }
    
    private void clearFields() {
        cmbLand.setSelectedIndex(-1);
        cmbOldOwner.removeAllItems();
        cmbNewOwner.setSelectedIndex(-1);
        txtRemarks.setText("");
        tblHistory.clearSelection();
    }
}