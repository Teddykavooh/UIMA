package com.teddykavooh.uima.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.Vitals;

// Add Vitals.class to the entities array and increment the version number
@Database(entities = {Patient.class, Vitals.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private volatile static AppDatabase INSTANCE;

    // Add abstract methods for both DAOs
    public abstract PatientDAO patientDao();
    public abstract VitalsDAO vitalsDao();

    // Migration from version 1 to 2 (already exists)
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE patients ADD COLUMN synced INTEGER NOT NULL DEFAULT 0");
        }
    };

    // Migration to create the new vitals table
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // SQL to create the new vitals table.
            database.execSQL("CREATE TABLE IF NOT EXISTS `vitals` (`id` INTEGER PRIMARY KEY " +
                    "AUTOINCREMENT NOT NULL, `patient_id` TEXT NOT NULL, `height` REAL NOT NULL, " +
                    "`weight` REAL NOT NULL,`visitDate` TEXT, `timestamp` INTEGER NOT NULL, " +
                    "`synced` INTEGER NOT NULL, FOREIGN KEY(`patient_id`) REFERENCES " +
                    "`patients`(`uniqueId`) ON UPDATE NO ACTION ON DELETE CASCADE )" );
            database.execSQL("CREATE INDEX IF NOT EXISTS `index_vitals_patientId` ON `vitals`" +
                    " (`patient_id`)");
        }
    };

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "uima_database")
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
