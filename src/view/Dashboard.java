package view;

import model.officer;
import javax.swing.*;
import java.awt.*;

/**
 * Dashboard - Main menu for the Land Management System
 * @author MANZI 9Z
 */
public class Dashboard extends JFrame {
    
    private officer loggedInOfficer;
    private JButton btnRegisterOwner, btnRegisterLand, btnTransferOwnership, btnSearchLand, btnLogout;
    private JLabel lblWelcome, lblRole;
    
    public Dashboard(officer officer) {
        this.loggedInOfficer = officer;
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Land Management System - Dashboard");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(0, 0, 700, 100);
        headerPanel.setBackground(new Color(0, 102, 204));
        
        // Welcome Label
        lblWelcome = new JLabel("Welcome, " + loggedInOfficer.getFullName());
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 22));
        lblWelcome.setForeground(Color.WHITE);
        lblWelcome.setBounds(30, 20, 500, 30);
        headerPanel.add(lblWelcome);
        
        // Role Label
        lblRole = new JLabel("Role: " + loggedInOfficer.getRole());
        lblRole.setFont(new Font("Arial", Font.PLAIN, 14));
        lblRole.setForeground(new Color(220, 220, 220));
        lblRole.setBounds(30, 55, 300, 25);
        headerPanel.add(lblRole);
        
        mainPanel.add(headerPanel);
        
        // Menu Title
        JLabel lblMenuTitle = new JLabel("Main Menu");
        lblMenuTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblMenuTitle.setForeground(new Color(60, 60, 60));
        lblMenuTitle.setBounds(280, 120, 150, 30);
        mainPanel.add(lblMenuTitle);
        
        // Register Owner Button
        btnRegisterOwner = createMenuButton("Register Owner", 150, 170);
        btnRegisterOwner.addActionListener(e -> openOwnerRegistrationForm());
        mainPanel.add(btnRegisterOwner);
        
        // Register Land Button
        btnRegisterLand = createMenuButton("Register Land", 390, 170);
        btnRegisterLand.addActionListener(e -> openLandRegistrationForm());
        mainPanel.add(btnRegisterLand);
        
        // Transfer Ownership Button
        btnTransferOwnership = createMenuButton("Transfer Ownership", 150, 260);
        btnTransferOwnership.addActionListener(e -> openOwnershipTransferForm());
        mainPanel.add(btnTransferOwnership);
        
        // Search Land Button
        btnSearchLand = createMenuButton("Search Land", 390, 260);
        btnSearchLand.addActionListener(e -> openSearchLandForm());
        mainPanel.add(btnSearchLand);
        
        // Logout Button
        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogout.setBounds(270, 380, 160, 40);
        btnLogout.setBackground(new Color(204, 51, 0));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.addActionListener(e -> logout());
        mainPanel.add(btnLogout);
        
        add(mainPanel);
    }
    
    private JButton createMenuButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBounds(x, y, 200, 60);
        button.setBackground(new Color(0, 153, 51));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 130, 40), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        return button;
    }
    
    private void openOwnerRegistrationForm() {
        OwnerRegistrationform ownerForm = new OwnerRegistrationform();
        ownerForm.setVisible(true);
    }
    
    private void openLandRegistrationForm() {
        LandRegistrationform landForm = new LandRegistrationform();
        landForm.setVisible(true);
    }
    
    private void openOwnershipTransferForm() {
        OwnershipTransferform transferForm = new OwnershipTransferform();
        transferForm.setVisible(true);
    }
    
    private void openSearchLandForm() {
        SearchLandform searchForm = new SearchLandform();
        searchForm.setVisible(true);
    }
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "Logged out successfully!", 
                "Logout", 
                JOptionPane.INFORMATION_MESSAGE);
            
            Loginform loginForm = new Loginform();
            loginForm.setVisible(true);
            this.dispose();
        }
    }
}