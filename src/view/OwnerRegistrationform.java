package view;

import controller.ownercontroller;
import model.Owner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Owner Registration Form - CRUD operations with JTable display
 * @author MANZI 9Z
 */
public class OwnerRegistrationform extends JFrame {
    
    private JTextField txtFullName, txtNationalId, txtContact, txtAddress, txtDob;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnRefresh;
    private JTable tblOwners;
    private DefaultTableModel tableModel;
    private ownercontroller ownerController;
    private int selectedOwnerId = -1;
    
    public OwnerRegistrationform() {
        ownerController = new ownercontroller();
        initComponents();
        loadOwnersData();
    }
    
    private void initComponents() {
        setTitle("Owner Registration");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel lblTitle = new JLabel("Owner Registration");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(400, 10, 250, 30);
        mainPanel.add(lblTitle);
        
        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(null);
        formPanel.setBounds(20, 50, 450, 300);
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Owner Information"));
        
        // Full Name
        JLabel lblFullName = new JLabel("Full Name*:");
        lblFullName.setBounds(20, 30, 100, 25);
        formPanel.add(lblFullName);
        
        txtFullName = new JTextField();
        txtFullName.setBounds(130, 30, 280, 25);
        formPanel.add(txtFullName);
        
        // National ID
        JLabel lblNationalId = new JLabel("National ID*:");
        lblNationalId.setBounds(20, 70, 100, 25);
        formPanel.add(lblNationalId);
        
        txtNationalId = new JTextField();
        txtNationalId.setBounds(130, 70, 280, 25);
        formPanel.add(txtNationalId);
        
        // Date of Birth
        JLabel lblDob = new JLabel("Date of Birth*:");
        lblDob.setBounds(20, 110, 100, 25);
        formPanel.add(lblDob);
        
        txtDob = new JTextField();
        txtDob.setBounds(130, 110, 280, 25);
        txtDob.setToolTipText("Format: YYYY-MM-DD");
        formPanel.add(txtDob);
        
        JLabel lblFormat = new JLabel("(YYYY-MM-DD)");
        lblFormat.setBounds(130, 135, 150, 15);
        lblFormat.setFont(new Font("Arial", Font.ITALIC, 10));
        lblFormat.setForeground(Color.GRAY);
        formPanel.add(lblFormat);
        
        // Contact
        JLabel lblContact = new JLabel("Contact*:");
        lblContact.setBounds(20, 155, 100, 25);
        formPanel.add(lblContact);
        
        txtContact = new JTextField();
        txtContact.setBounds(130, 155, 280, 25);
        formPanel.add(txtContact);
        
        // Address
        JLabel lblAddress = new JLabel("Address*:");
        lblAddress.setBounds(20, 195, 100, 25);
        formPanel.add(lblAddress);
        
        txtAddress = new JTextField();
        txtAddress.setBounds(130, 195, 280, 25);
        formPanel.add(txtAddress);
        
        mainPanel.add(formPanel);
        
        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBounds(20, 360, 450, 50);
        buttonPanel.setBackground(Color.WHITE);
        
        btnAdd = new JButton("Add");
        btnAdd.setBackground(new Color(0, 153, 51));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.addActionListener(e -> addOwner());
        buttonPanel.add(btnAdd);
        
        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(255, 153, 0));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        btnUpdate.addActionListener(e -> updateOwner());
        buttonPanel.add(btnUpdate);
        
        btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(204, 51, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.addActionListener(e -> deleteOwner());
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
        btnRefresh.addActionListener(e -> loadOwnersData());
        buttonPanel.add(btnRefresh);
        
        mainPanel.add(buttonPanel);
        
        // Table Panel
        String[] columns = {"ID", "Full Name", "National ID", "Date of Birth", "Contact", "Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblOwners = new JTable(tableModel);
        tblOwners.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblOwners.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFieldsFromSelectedRow();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tblOwners);
        scrollPane.setBounds(490, 50, 480, 490);
        mainPanel.add(scrollPane);
        
        add(mainPanel);
    }
    
    private void addOwner() {
        // Validate inputs
        if (!validateInputs()) {
            return;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(txtDob.getText().trim());
            Date sqlDate = new Date(utilDate.getTime());
            
            Owner owner = new Owner(
                txtFullName.getText().trim(),
                txtNationalId.getText().trim(),
                sqlDate,
                txtContact.getText().trim(),
                txtAddress.getText().trim()
            );
            
            boolean success = ownerController.addOwner(owner);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Owner registered successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadOwnersData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to register owner!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid date format! Use YYYY-MM-DD", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updateOwner() {
        if (selectedOwnerId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an owner from the table to update!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInputs()) {
            return;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(txtDob.getText().trim());
            Date sqlDate = new Date(utilDate.getTime());
            
            Owner owner = new Owner(
                selectedOwnerId,
                txtFullName.getText().trim(),
                txtNationalId.getText().trim(),
                sqlDate,
                txtContact.getText().trim(),
                txtAddress.getText().trim()
            );
            
            boolean success = ownerController.updateOwner(owner);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Owner updated successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadOwnersData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to update owner!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, 
                "Invalid date format! Use YYYY-MM-DD", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void deleteOwner() {
        if (selectedOwnerId == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select an owner from the table to delete!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this owner?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = ownerController.deleteOwner(selectedOwnerId);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Owner deleted successfully!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadOwnersData();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Failed to delete owner!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validateInputs() {
        // Business validation: Full name required
        if (txtFullName.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Full name is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtFullName.requestFocus();
            return false;
        }
        
        // Technical validation: Full name length
        if (txtFullName.getText().trim().length() < 3) {
            JOptionPane.showMessageDialog(this, 
                "Full name must be at least 3 characters!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Business validation: National ID required
        if (txtNationalId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "National ID is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtNationalId.requestFocus();
            return false;
        }
        
        // Technical validation: National ID format (16 digits for Rwanda)
        if (!txtNationalId.getText().trim().matches("\\d{16}")) {
            JOptionPane.showMessageDialog(this, 
                "National ID must be exactly 16 digits!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Business validation: DOB required
        if (txtDob.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Date of birth is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtDob.requestFocus();
            return false;
        }
        
        // Business validation: Contact required
        if (txtContact.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Contact is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtContact.requestFocus();
            return false;
        }
        
        // Technical validation: Contact format (10 digits for Rwanda)
        if (!txtContact.getText().trim().matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, 
                "Contact must be exactly 10 digits!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Business validation: Address required
        if (txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Address is required!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtAddress.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void loadOwnersData() {
        tableModel.setRowCount(0);
        List<Owner> owners = ownerController.getAllOwners();
        
        for (Owner owner : owners) {
            Object[] row = {
                owner.getOwnerId(),
                owner.getFullName(),
                owner.getNationalId(),
                owner.getDob(),
                owner.getContact(),
                owner.getAddress()
            };
            tableModel.addRow(row);
        }
    }
    
    private void fillFieldsFromSelectedRow() {
        int selectedRow = tblOwners.getSelectedRow();
        if (selectedRow >= 0) {
            selectedOwnerId = (int) tableModel.getValueAt(selectedRow, 0);
            txtFullName.setText((String) tableModel.getValueAt(selectedRow, 1));
            txtNationalId.setText((String) tableModel.getValueAt(selectedRow, 2));
            txtDob.setText(tableModel.getValueAt(selectedRow, 3).toString());
            txtContact.setText((String) tableModel.getValueAt(selectedRow, 4));
            txtAddress.setText((String) tableModel.getValueAt(selectedRow, 5));
        }
    }
    
    private void clearFields() {
        txtFullName.setText("");
        txtNationalId.setText("");
        txtDob.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        selectedOwnerId = -1;
        tblOwners.clearSelection();
    }
}