package com.teddykavooh.uima.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.teddykavooh.uima.model.Vitals;

import java.util.List;

@Dao
public interface VitalsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Vitals vitals);

    @Query("SELECT * FROM vitals WHERE patient_id = :patientId")
    List<Vitals> getVitalsByPatientId(String patientId);

    // Get unsynced vitals
    @Query("SELECT * FROM vitals WHERE synced = 0")
    List<Vitals> getUnsyncedVitals();

    @Update
    void update(Vitals vitals);

}
