package view;

import controller.landcontroller;
import controller.ownercontroller;
import model.land;
import model.Owner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Land Registration Form - CRUD operations with JTable display
 * @author MANZI 9Z
 */
public class LandRegistrationform extends JFrame {
    
    private JTextField txtPlotNumber, txtSize, txtLocation;
    private JComboBox<String> cmbUsage, cmbOwner;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;
    private JTable tblLands;
    private DefaultTableModel tableModel;
    private landcontroller landController;
    private ownercontroller ownerController;
    private int selectedLandId = -1;
    private List<Owner> owners;
    
    public LandRegistrationform() {
        landController = new landcontroller();
        ownerController = new ownercontroller();
        initComponents();
        loadOwners();
        loadLandsData();
    }
    
    private void initComponents() {
        setTitle("Land Registration");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel lblTitle = new JLabel("Land Registration");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(450, 10, 250, 30);
        mainPanel.add(lblTitle);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 50, 500, 300);
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Land Information"));
        
        // Plot Number
        JLabel lblPlotNumber = new JLabel("Plot Number*:");
        lblPlotNumber.setBounds(20, 30, 120, 25);
        formPanel.add(lblPlotNumber);
        
        txtPlotNumber = new JTextField();
        txtPlotNumber.setBounds(140, 30, 320, 25);
        formPanel.add(txtPlotNumber);
        
        // Size
        JLabel lblSize = new JLabel("Size (sq.m)*:");
        lblSize.setBounds(20, 70, 120, 25);
        formPanel.add(lblSize);
        
        txtSize = new JTextField();
        txtSize.setBounds(140, 70, 320, 25);
        formPanel.add(txtSize);
        
        // Location
        JLabel lblLocation = new JLabel("Location*:");
        lblLocation.setBounds(20, 110, 120, 25);
        formPanel.add(lblLocation);
        
        txtLocation = new JTextField();
        txtLocation.setBounds(140, 110, 320, 25);
        formPanel.add(txtLocation);
        
        // Owner
        JLabel lblOwner = new JLabel("Owner*:");
        lblOwner.setBounds(20, 150, 120, 25);
        formPanel.add(lblOwner);
        
        cmbOwner = new JComboBox<>();
        cmbOwner.setBounds(140, 150, 320, 25);
        formPanel.add(cmbOwner);
        
        // Usage
        JLabel lblUsage = new JLabel("Land Usage*:");
        lblUsage.setBounds(20, 190, 120, 25);
        formPanel.add(lblUsage);
        
        String[] usageTypes = {"Residential", "Commercial", "Agricultural", "Industrial", "Mixed Use"};
        cmbUsage = new JComboBox<>(usageTypes);
        cmbUsage.setBounds(140, 190, 320, 25);
        formPanel.add(cmbUsage);
        
        mainPanel.add(formPanel);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBounds(20, 360, 500, 50);
        buttonPanel.setBackground(Color.WHITE);
        
        btnAdd = new JButton("Add");
        btnAdd.setBackground(new Color(0, 153, 51));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addLand());
        buttonPanel.add(btnAdd);
        
        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(255, 153, 0));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateLand());
        buttonPanel.add(btnUpdate);
        
        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(204, 51, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteLand());
        buttonPanel.add(btnDelete);
        
        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(100, 100, 100));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.addActionListener(e -> clearFields());
        buttonPanel.add(btnClear);
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.setBackground(new Color(0, 102, 204));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(e -> {
            loadOwners();
            loadLandsData();
        });
        buttonPanel.add(btnRefresh);
        
        mainPanel.add(buttonPanel);
        
        // Table Panel
        String[] columns = {"Land ID", "Plot Number", "Size", "Location", "Owner ID", "Usage"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblLands = new JTable(tableModel);
        tblLands.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblLands.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFieldsFromSelectedRow();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tblLands);
        scrollPane.setBounds(540, 50, 540, 490);
        mainPanel.add(scrollPane);
        
        add(mainPanel);
    }
    
    private void loadOwners() {
        cmbOwner.removeAllItems();
        owners = ownerController.getAllOwners();
        
        for (Owner owner : owners) {
            cmbOwner.addItem(owner.getOwnerId() + " - " + owner.getFullName());
        }
    }
    
    private int getSelectedOwnerId() {
        String selected = (String) cmbOwner.getSelectedItem();
        if (selected != null) {
            return Integer.parseInt(selected.split(" - ")[0]);
        }
        return -1;
    }
    
    private void addLand() {
        if (!validateInputs()) {
            return;
        }
        
        land land = new land(
            txtPlotNumber.getText().trim(),
            txtSize.getText().trim(),
            txtLocation.getText().trim(),
            getSelectedOwnerId(),
            (String) cmbUsage.getSelectedItem()
        );
        
        boolean success = landController.addLand(land);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Land registered successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadLandsData();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to register land!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateLand() {
        if (selectedLandId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a land from the table to update!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInputs()) {
            return;
        }
        
        land land = new land(
            selectedLandId,
            txtPlotNumber.getText().trim(),
            txtSize.getText().trim(),
            txtLocation.getText().trim(),
            getSelectedOwnerId(),
            (String) cmbUsage.getSelectedItem()
        );
        
        boolean success = landController.updateLand(land);
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Land updated successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadLandsData();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to update land!", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteLand() {
        if (selectedLandId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a land from the table to delete!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this land?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = landController.deleteLand(selectedLandId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Land deleted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadLandsData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to delete land!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validateInputs() {
        // Business validation: Plot number required
        if (txtPlotNumber.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Plot number is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtPlotNumber.requestFocus();
            return false;
        }
        
        // Technical validation: Plot number format (alphanumeric)
        if (!txtPlotNumber.getText().trim().matches("[A-Z0-9\\-]+")) {
            JOptionPane.showMessageDialog(this, 
                "Plot number must contain only uppercase letters, numbers, and hyphens!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Business validation: Size required
        if (txtSize.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Land size is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtSize.requestFocus();
            return false;
        }
        
        // Technical validation: Size must be numeric
        try {
            double size = Double.parseDouble(txtSize.getText().trim());
            if (size <= 0) {
                JOptionPane.showMessageDialog(this, 
                    "Land size must be greater than zero!", 
                    "Validation Error", 
                    JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Land size must be a valid number!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Business validation: Location required
        if (txtLocation.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Location is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtLocation.requestFocus();
            return false;
        }
        
        // Business validation: Owner must be selected
        if (cmbOwner.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an owner!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void loadLandsData() {
        tableModel.setRowCount(0);
        List<land> lands = landController.getAllLands();
        
        for (land land : lands) {
            Object[] row = {
                land.getLandId(),
                land.getPlotNumber(),
                land.getSize(),
                land.getLocation(),
                land.getOwnerId(),
                land.getLandUsage()
            };
            tableModel.addRow(row);
        }
    }
    
    private void fillFieldsFromSelectedRow() {
        int selectedRow = tblLands.getSelectedRow();
        if (selectedRow >= 0) {
            selectedLandId = (int) tableModel.getValueAt(selectedRow, 0);
            txtPlotNumber.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtSize.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtLocation.setText((String) tableModel.getValueAt(selectedRow, 3));
            
            int ownerId = (int) tableModel.getValueAt(selectedRow, 4);
            for (int i = 0; i < cmbOwner.getItemCount(); i++) {
                if (cmbOwner.getItemAt(i).startsWith(ownerId + " ")) {
                    cmbOwner.setSelectedIndex(i);
                    break;
                }
            }
            
            cmbUsage.setSelectedItem(tableModel.getValueAt(selectedRow, 5));
        }
    }
    
    private void clearFields() {
        txtPlotNumber.setText("");
        txtSize.setText("");
        txtLocation.setText("");
        cmbOwner.setSelectedIndex(-1);
        cmbUsage.setSelectedIndex(0);
        selectedLandId = -1;
        tblLands.clearSelection();
    }
}