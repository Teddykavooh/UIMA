package com.teddykavooh.uima.model;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.text.DateFormat;

@Entity(tableName = "patients")
public class Patient {
    // TODO: Remove quick fix for uniqueId
    @PrimaryKey
    @NonNull
    @Expose(serialize = false, deserialize = false)
    private String uniqueId;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String dob;
    @Expose
    private String gender;
    @Expose
    private String reg_date;

    // Sync tracker: restrict from sync
    @Expose(serialize = false, deserialize = false)
    private boolean synced;


    // Constructor
    public Patient(@NonNull String uniqueId, String firstName, String lastName, String dob,
                   String gender, String reg_date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.uniqueId = uniqueId;
        this.dob = dob;
        this.gender = gender;
        this.reg_date = reg_date;
        this.synced = false;
    }

    // Default Constructor
    public Patient() {}

    // Getter methods
    @NonNull
    public String getUniqueId() {
        return uniqueId;
    }
    public void setUniqueId(@NonNull String uniqueId) {
        this.uniqueId = uniqueId;
    }
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
    public String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReg_date() {
        return reg_date;
    }
    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
    public boolean isSynced() {
        return synced;
    }
    public void setSynced(boolean synced) {
        this.synced = synced;
    }
}
