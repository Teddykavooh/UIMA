package com.teddykavooh.uima.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.teddykavooh.uima.model.Patient;

@Database(entities = {Patient.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private volatile static AppDatabase INSTANCE;
    public abstract PatientDAO patientDao();

    // Better accommodate the sync column
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE patients ADD COLUMN synced INTEGER NOT NULL DEFAULT 0");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "uima_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
