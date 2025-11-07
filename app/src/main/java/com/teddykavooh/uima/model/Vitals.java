package com.teddykavooh.uima.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "vitals")
public class Vitals {
    @PrimaryKey
    @Expose
    @NonNull
    private String patient_id;

    @Expose
    private String height, weight, bmi, visit_date;
    @Expose(serialize = false, deserialize = false)
    private boolean synced;

    // Constructor
    public Vitals(String visitDate, String height, String weight, String bmi, String patient_id) {
        this.visit_date = visitDate;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.patient_id = patient_id;
        this.synced = false;

    }

    // Default Constructor
    public Vitals() {

    }

    // Getters and Setters
    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }


    @NonNull
    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(@NonNull String patient_id) {
        this.patient_id = patient_id;
    }

//    public String getVital_id() {
//        return vital_id;
//    }
//
//    public void setVital_id(String vital_id) {
//        this.vital_id = vital_id;
//    }

    public boolean isSynced() {
        return synced;
    }

    public void setSynced(boolean synced) {
        this.synced = synced;
    }

}

/*
* {
    "visit_date": "2025-10-31",
    "height": "156",
    "weight": "80",
    "bmi": "24.5",
    "patient_id": "2"
}
* */