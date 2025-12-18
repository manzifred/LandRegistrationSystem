/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.sql.Date;
/**
 *
 * @author MANZI 9Z
 */
public class Owner {
   
    
    private int ownerId;
    private String fullName;
    private String nationalId;
    private Date dob;
    private String contact;
    private String address;

    // Empty constructor
    public Owner() {}

    // Constructor without ID (for inserting new records)
    public Owner(String fullName, String nationalId, Date dob, String contact, String address) {
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.dob = dob;
        this.contact = contact;
        this.address = address;
    }

    // Constructor with ID (for retrieving records)
    public Owner(int ownerId, String fullName, String nationalId, Date dob, String contact, String address) {
        this.ownerId = ownerId;
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.dob = dob;
        this.contact = contact;
        this.address = address;
    }

    // Getters and setters
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}


