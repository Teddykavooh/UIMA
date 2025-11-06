package com.teddykavooh.uima.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.teddykavooh.uima.model.Patient;

import java.util.List;

@Dao
public interface PatientDAO {
    @Insert
    void insert(Patient patient);

    @Query("SELECT * FROM patients ORDER BY reg_date DESC")
    List<Patient> getAllPatients();

    @Query("SELECT * FROM patients WHERE uniqueId = :id")
    Patient getPatient(String id);

    // Get unsynced patients
    @Query("SELECT * FROM patients WHERE synced = 0")
    List<Patient> getUnsyncedPatients();

    @Update
    void update(Patient patient);


    // TODO
    @Query("SELECT * FROM patients WHERE firstName = :name")
    Patient getByName(String name);

    @Delete
    void delete(Patient patient);
}
