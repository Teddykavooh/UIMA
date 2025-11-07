package com.teddykavooh.uima.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

/**
 * This class defines the one-to-many relationship between a Patient and their Vitals.
 * Room uses this data structure to fetch a patient and all their associated vitals records
 * in an efficient way.
 */
public class PatientWithVitals {
    @Embedded
    public Patient patient;

    @Relation(
            parentColumn = "uniqueId",  // Primary Key of the parent table (Patient)
            entityColumn = "patient_id",  // Foreign Key in the child table (Vitals)
            entity = Vitals.class
    )
    public List<Vitals> vitals; // A patient can have a list of vitals records
}
