package com.teddykavooh.uima.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.PatientWithVitals;
import com.teddykavooh.uima.model.Vitals;

import java.util.List;

@Dao
public interface PatientDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Patient patient);

    @Query("SELECT * FROM patients WHERE uniqueId = :uniqueId")
    Patient getPatient(String uniqueId);

    @Query("SELECT * FROM patients")
    List<Patient> getAllPatients();

    @Query("SELECT * FROM patients WHERE synced = 0")
    List<Patient> getUnsyncedPatients();

    @Update
    void update(Patient patient);

    /**
     * This query uses the @Relation defined in PatientWithVitals to fetch all patients
     * and their associated vitals records. The @Transaction annotation ensures that this
     * operation is performed atomically, preventing data inconsistency.
     * This replaces the need for a manual JOIN query.
     */
    @Transaction
    @Query("SELECT * FROM patients")
    List<PatientWithVitals> getPatientsWithVitals();
}
