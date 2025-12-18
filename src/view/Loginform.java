package view;

import Dao.DatabaseConnection;
import controller.officercontroller;
import model.officer;
import javax.swing.*;
import java.awt.*;

/**
 * Login Form - Authentication interface for officers
 * @author MANZI 9Z
 */
public class Loginform extends JFrame {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnClear;
    private officercontroller officerController;
    
    public Loginform() {
        officerController = new officercontroller();
        initComponents();
    }
    
    private void initComponents() {
        setTitle("Land Management System - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Title Label
        JLabel lblTitle = new JLabel("Land Management System");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBounds(80, 30, 300, 30);
        mainPanel.add(lblTitle);
        
        // Subtitle
        JLabel lblSubtitle = new JLabel("Officer Login");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setBounds(165, 60, 120, 25);
        mainPanel.add(lblSubtitle);
        
        // Username Label
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        lblUsername.setBounds(70, 110, 100, 25);
        mainPanel.add(lblUsername);
        
        // Username TextField
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setBounds(70, 135, 300, 30);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtUsername);
        
        // Password Label
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPassword.setBounds(70, 175, 100, 25);
        mainPanel.add(lblPassword);
        
        // Password Field
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        txtPassword.setBounds(70, 200, 300, 30);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        mainPanel.add(txtPassword);
        
        // Login Button
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBounds(70, 250, 140, 35);
        btnLogin.setBackground(new Color(0, 153, 51));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> handleLogin());
        mainPanel.add(btnLogin);
        
        // Clear Button
        btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Arial", Font.BOLD, 14));
        btnClear.setBounds(230, 250, 140, 35);
        btnClear.setBackground(new Color(204, 51, 0));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> clearFields());
        mainPanel.add(btnClear);
        
        add(mainPanel);
    }
    
    private void handleLogin() {
        // Technical validation: Check if fields are empty
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username cannot be empty!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Password cannot be empty!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return;
        }
        
        // Technical validation: Username length
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, 
                "Username must be at least 3 characters long!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Technical validation: Password length
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 4 characters long!", 
                "Validation Error", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Attempt login through controller
        officer officer = officerController.login(username, password);
        
        if (officer != null) {
            JOptionPane.showMessageDialog(this, 
                "Login successful! Welcome " + officer.getFullName(), 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Open Dashboard
            Dashboard dashboard = new Dashboard(officer);
            dashboard.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid username or password!", 
                "Login Failed", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtUsername.requestFocus();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Loginform().setVisible(true);
        });
    }
}