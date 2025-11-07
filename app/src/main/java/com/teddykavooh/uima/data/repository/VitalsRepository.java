package com.teddykavooh.uima.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.teddykavooh.uima.data.local.AppDatabase;
import com.teddykavooh.uima.data.local.VitalsDAO;
import com.teddykavooh.uima.data.remote.VitalsService;
import com.teddykavooh.uima.model.AddVitalsResponse;
import com.teddykavooh.uima.model.Vitals;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VitalsRepository {
    private final VitalsDAO vitalsDao;
    private final VitalsService vitalsService;
    private final Context context;

    // Constructor
    public VitalsRepository(Context context, VitalsService vitalsService) {
        AppDatabase uimaDatabase = AppDatabase.getInstance(context);
        this.vitalsDao = uimaDatabase.vitalsDao();
        this.vitalsService = vitalsService;
        this.context = context;
    }

    // Local Operations
    public void saveLocal(Vitals vitals) {
        new Thread(() -> {
            vitalsDao.insert(vitals);
        }).start();
    }

    // Remote Operations
    public void syncVitals() {
        new Thread(() -> {
            List<Vitals> unsyncedVitals = vitalsDao.getUnsyncedVitals();
            // Check for availability of data
            if (unsyncedVitals.isEmpty()) {
                // Relay toast
                new Handler(Looper.getMainLooper()).post(() -> {
                    Toast.makeText(context, "No vitals to sync", Toast.LENGTH_SHORT).show();
                });
                return;
            }
            // Sync data
            for (Vitals vitals : unsyncedVitals) {
                vitalsService.addVitals(vitals).enqueue(new Callback<AddVitalsResponse>() {
                    @Override
                    public void onResponse(Call<AddVitalsResponse> call, Response<AddVitalsResponse> response) {
                        // Change sync status
                        if (response.isSuccessful()) {
                            vitals.setSynced(true);
                            new Thread(() -> {
                                vitalsDao.update(vitals);
                            }).start();

                            // Relay toast
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Toast.makeText(context, "Vitals synced successfully", Toast.LENGTH_SHORT).show();
                            });
                        } else {
                            // Handle error
                            new Handler(Looper.getMainLooper()).post(() -> {
                                Toast.makeText(context, "Error syncing vitals", Toast.LENGTH_SHORT).show();
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<AddVitalsResponse> call, Throwable t) {
                        // Handle failure
                        t.printStackTrace();

                        // Relay toast
                        new Handler(Looper.getMainLooper()).post(() -> {
                            Toast.makeText(context, "Error syncing vitals", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        }).start();
    }
}
