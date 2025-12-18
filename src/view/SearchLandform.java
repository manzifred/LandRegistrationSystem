package view;

import controller.landcontroller;
import controller.ownercontroller;
import model.land;
import model.Owner;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Search Land Form - Search and view land records
 * @author MANZI 9Z
 */
public class SearchLandform extends JFrame {
    
    private JTextField txtSearchPlot, txtSearchOwner;
    private JButton btnSearchByPlot, btnSearchByOwner, btnViewAll, btnViewDetails;
    private JTable tblResults;
    private DefaultTableModel tableModel;
    private landcontroller landController;
    private ownercontroller ownerController;
    
    public SearchLandform() {
        landController = new landcontroller();
        ownerController = new ownercontroller();
        initComponents();
        loadAllLands();
    }
    
    private void initComponents() {
        setTitle("Search Land Records");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.WHITE);
        
        // Title
        JLabel lblTitle = new JLabel("Land Search & View");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(400, 10, 250, 30);
        mainPanel.add(lblTitle);
        
        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(null);
        searchPanel.setBounds(20, 50, 950, 150);
        searchPanel.setBackground(new Color(240, 240, 240));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Options"));
        
        // Search by Plot Number
        JLabel lblSearchPlot = new JLabel("Search by Plot Number:");
        lblSearchPlot.setBounds(20, 30, 180, 25);
        searchPanel.add(lblSearchPlot);
        
        txtSearchPlot = new JTextField();
        txtSearchPlot.setBounds(200, 30, 300, 25);
        searchPanel.add(txtSearchPlot);
        
        btnSearchByPlot = new JButton("Search");
        btnSearchByPlot.setBounds(520, 30, 120, 25);
        btnSearchByPlot.setBackground(new Color(0, 153, 51));
        btnSearchByPlot.setForeground(Color.WHITE);
        btnSearchByPlot.setFocusPainted(false);
        btnSearchByPlot.addActionListener(e -> searchByPlotNumber());
        searchPanel.add(btnSearchByPlot);
        
        // Search by Owner Name
        JLabel lblSearchOwner = new JLabel("Search by Owner Name:");
        lblSearchOwner.setBounds(20, 70, 180, 25);
        searchPanel.add(lblSearchOwner);
        
        txtSearchOwner = new JTextField();
        txtSearchOwner.setBounds(200, 70, 300, 25);
        searchPanel.add(txtSearchOwner);
        
        btnSearchByOwner = new JButton("Search");
        btnSearchByOwner.setBounds(520, 70, 120, 25);
        btnSearchByOwner.setBackground(new Color(0, 153, 51));
        btnSearchByOwner.setForeground(Color.WHITE);
        btnSearchByOwner.setFocusPainted(false);
        btnSearchByOwner.addActionListener(e -> searchByOwnerName());
        searchPanel.add(btnSearchByOwner);
        
        // View All Button
        btnViewAll = new JButton("View All Lands");
        btnViewAll.setBounds(660, 30, 150, 25);
        btnViewAll.setBackground(new Color(0, 102, 204));
        btnViewAll.setForeground(Color.WHITE);
        btnViewAll.setFocusPainted(false);
        btnViewAll.addActionListener(e -> loadAllLands());
        searchPanel.add(btnViewAll);
        
        // View Details Button
        btnViewDetails = new JButton("View Selected Details");
        btnViewDetails.setBounds(660, 70, 150, 25);
        btnViewDetails.setBackground(new Color(255, 153, 0));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.addActionListener(e -> viewSelectedLandDetails());
        searchPanel.add(btnViewDetails);
        
        mainPanel.add(searchPanel);
        
        // Results Label
        JLabel lblResults = new JLabel("Search Results");
        lblResults.setFont(new Font("Arial", Font.BOLD, 16));
        lblResults.setBounds(20, 210, 200, 25);
        mainPanel.add(lblResults);
        
        // Results Table
        String[] columns = {"Land ID", "Plot Number", "Size (sq.m)", "Location", "Owner ID", "Owner Name", "Usage"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblResults = new JTable(tableModel);
        tblResults.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblResults.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tblResults);
        scrollPane.setBounds(20, 240, 950, 360);
        mainPanel.add(scrollPane);
        
        add(mainPanel);
    }
    
    private void searchByPlotNumber() {
        String plotNumber = txtSearchPlot.getText().trim();
        
        // Technical validation: Search term cannot be empty
        if (plotNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a plot number to search!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtSearchPlot.requestFocus();
            return;
        }
        
        // Technical validation: Minimum search length
        if (plotNumber.length() < 2) {
            JOptionPane.showMessageDialog(this, 
                "Please enter at least 2 characters to search!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        List<land> lands = landController.searchLandsByPlotNumber(plotNumber);
        displaySearchResults(lands);
        
        if (lands.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No lands found with plot number containing: " + plotNumber, 
                "Search Results", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void searchByOwnerName() {
        String ownerName = txtSearchOwner.getText().trim();
        
        // Technical validation: Search term cannot be empty
        if (ownerName.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter an owner name to search!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtSearchOwner.requestFocus();
            return;
        }
        
        // Technical validation: Minimum search length
        if (ownerName.length() < 2) {
            JOptionPane.showMessageDialog(this, 
                "Please enter at least 2 characters to search!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Search owners by name
        List<Owner> owners = ownerController.getAllOwners().stream()
    .filter(o -> o.getFullName().toLowerCase().contains(ownerName.toLowerCase()))
    .collect(Collectors.toList());
        
        if (owners.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No owners found with name containing: " + ownerName, 
                "Search Results", 
                JOptionPane.INFORMATION_MESSAGE);
            tableModel.setRowCount(0);
            return;
        }
        
        // Get all lands for found owners
        tableModel.setRowCount(0);
        for (Owner owner : owners) {
            List<land> lands = landController.getLandsByOwnerId(owner.getOwnerId());
            for (land land : lands) {
                Object[] row = {
                    land.getLandId(),
                    land.getPlotNumber(),
                    land.getSize(),
                    land.getLocation(),
                    land.getOwnerId(),
                    owner.getFullName(),
                    land.getLandUsage()
                };
                tableModel.addRow(row);
            }
        }
        
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, 
                "No lands found for owner: " + ownerName, 
                "Search Results", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void loadAllLands() {
        List<land> lands = landController.getAllLands();
        displaySearchResults(lands);
    }
    
    private void displaySearchResults(List<land> lands) {
        tableModel.setRowCount(0);
        
        for (land land : lands) {
            // Get owner name
            Owner owner = ownerController.getAllOwners().stream()
                .filter(o -> o.getOwnerId() == land.getOwnerId())
                .findFirst()
                .orElse(null);
            
            String ownerName = (owner != null) ? owner.getFullName() : "Unknown";
            
            Object[] row = {
                land.getLandId(),
                land.getPlotNumber(),
                land.getSize(),
                land.getLocation(),
                land.getOwnerId(),
                ownerName,
                land.getLandUsage()
            };
            tableModel.addRow(row);
        }
    }
    
    private void viewSelectedLandDetails() {
        int selectedRow = tblResults.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a land from the table to view details!", 
                "Warning", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int landId = (int) tableModel.getValueAt(selectedRow, 0);
        String plotNumber = (String) tableModel.getValueAt(selectedRow, 1);
        String size = (String) tableModel.getValueAt(selectedRow, 2);
        String location = (String) tableModel.getValueAt(selectedRow, 3);
        int ownerId = (int) tableModel.getValueAt(selectedRow, 4);
        String ownerName = (String) tableModel.getValueAt(selectedRow, 5);
        String usage = (String) tableModel.getValueAt(selectedRow, 6);
        
        // Get additional owner details
        Owner owner = ownerController.getAllOwners().stream()
            .filter(o -> o.getOwnerId() == ownerId)
            .findFirst()
            .orElse(null);
        
        String ownerDetails = "";
        if (owner != null) {
            ownerDetails = String.format(
                "\n\nOwner Information:\n" +
                "Name: %s\n" +
                "National ID: %s\n" +
                "Contact: %s\n" +
                "Address: %s\n" +
                "Date of Birth: %s",
                owner.getFullName(),
                owner.getNationalId(),
                owner.getContact(),
                owner.getAddress(),
                owner.getDob()
            );
        }
        
        String details = String.format(
            "Land Details:\n\n" +
            "Land ID: %d\n" +
            "Plot Number: %s\n" +
            "Size: %s square meters\n" +
            "Location: %s\n" +
            "Usage: %s%s",
            landId,
            plotNumber,
            size,
            location,
            usage,
            ownerDetails
        );
        
        // Create a dialog with larger text area for better readability
        JTextArea textArea = new JTextArea(details);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        textArea.setBackground(new Color(245, 245, 245));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 350));
        
        JOptionPane.showMessageDialog(this, 
            scrollPane, 
            "Complete Land Information", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}