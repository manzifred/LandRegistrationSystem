package model;

/**
 * Officer Model Class
 * @author MANZI 9Z
 */
public class officer {
    
    private int officerId;
    private String username;
    private String password;
    private String fullName;
    private String role;

    // Empty constructor
    public officer() {}

    // Constructor without ID (for insert)
    public officer(String username, String password, String fullName, String role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Constructor with ID (for fetching from DB)
    public officer(int officerId, String username, String password, String fullName, String role) {
        this.officerId = officerId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    // Getters and Setters
    public int getOfficerId() {
        return officerId;
    }

    public void setOfficerId(int officerId) {
        this.officerId = officerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}