package com.teddykavooh.uima.model;

import java.text.DateFormat;

public class Patient {
    private String firstName;
    private String lastName;
    private String uniqueId;
    private DateFormat dob;
    private String gender;
    private DateFormat reg_date;

    // Constructor
    public Patient(String firstName, String lastName, String uniqueId, DateFormat dob, String gender, DateFormat reg_date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniqueId = uniqueId;
        this.dob = dob;
        this.gender = gender;
        this.reg_date = reg_date;
    }

    // Default Constructor
    public Patient() {}

    // Getter methods
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUniqueId() {
        return uniqueId;
    }
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public DateFormat getDob() {
        return dob;
    }
    public void setDob(DateFormat dob) {
        this.dob = dob;
    }

    public String setGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public DateFormat getReg_date() {
        return reg_date;
    }
    public void setReg_date(DateFormat reg_date) {
        this.reg_date = reg_date;
    }
}
