package com.teddykavooh.uima.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.Vitals;

@Database(entities = {Patient.class, Vitals.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract PatientDAO patientDao();
    public abstract VitalsDAO vitalsDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "uima_database")
                            .fallbackToDestructiveMigration() // 👈 This line wipes & rebuilds DB
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
