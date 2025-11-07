package com.teddykavooh.uima.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.teddykavooh.uima.data.local.AppDatabase;
import com.teddykavooh.uima.data.local.PatientDAO;
import com.teddykavooh.uima.data.remote.PatientService;
import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.PatientRegisterResponse;
import com.teddykavooh.uima.model.PatientViewResponse;
import com.teddykavooh.uima.model.PatientWithVitals;
import com.teddykavooh.uima.model.PatientsListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PatientRepository {
    private final PatientService patientService;
    private final PatientDAO patientDao;
    private final Context context;

    public PatientRepository(Context context, PatientService patientService) {
        AppDatabase uimaDatabase = AppDatabase.getInstance(context);
        this.patientDao = uimaDatabase.patientDao();
        this.patientService = patientService;
        this.context = context.getApplicationContext();
    }

    // Local operations
    public void saveLocal(Patient patient) {
        new Thread(() -> {
            patientDao.insert(patient);
        }).start();
    }

    public Patient getPatientLocal(String uniqueId) {
        return patientDao.getPatient(uniqueId);
    }

    public List<Patient> getAllPatientsLocal() {
        return patientDao.getAllPatients();
    }

    public List<PatientWithVitals> getPatientsWithVitals() {
        return patientDao.getPatientsWithVitals();
    }

    public List<Patient> getUnsyncedPatients() {
        return patientDao.getUnsyncedPatients();
    }

    // Sync Patients
    public void syncPatients() {
        new Thread(() -> {
            List<Patient> unsyncedPatients = getUnsyncedPatients();
            if (unsyncedPatients.isEmpty()) {
                new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context, "No data to sync", Toast.LENGTH_SHORT).show());
                return;
            }

            for (Patient patient : unsyncedPatients) {
                patientService.registerPatient(patient).enqueue(new Callback<PatientRegisterResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<PatientRegisterResponse> call, @NonNull Response<PatientRegisterResponse> response) {
                        if (response.isSuccessful()) {
                            patient.setSynced(true);
                            new Thread(() -> patientDao.update(patient)).start();

                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context,
                                    "Patient synced: " + patient.getFirstName(), Toast.LENGTH_SHORT).show());
                        } else {
                            new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context,
                                    "Sync failed for " + patient.getFirstName() + ": " + response.message(), Toast.LENGTH_SHORT).show());
                        }
                    }

                    @Override
                    public void onFailure(Call<PatientRegisterResponse> call, Throwable t) {
                        t.printStackTrace();
                        new Handler(Looper.getMainLooper()).post(() -> Toast.makeText(context,
                                "Patient sync failed: " + patient.getFirstName(), Toast.LENGTH_SHORT).show());
                    }
                });
            }
        }).start();
    }

    // Remote operations
    public void getPatient(String uniqueId, Callback<PatientViewResponse> callback) {
        Call<PatientViewResponse> call = patientService.getPatient(uniqueId);
        call.enqueue(callback);
    }

    public void getAllPatients(Callback<PatientsListResponse> callback) {
        Call<PatientsListResponse> call = patientService.getAllPatients();
        call.enqueue(callback);
    }
}
