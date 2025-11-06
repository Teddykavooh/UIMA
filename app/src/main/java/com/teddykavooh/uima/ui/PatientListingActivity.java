package com.teddykavooh.uima.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.teddykavooh.uima.R;
import com.teddykavooh.uima.data.remote.ApiClient;
import com.teddykavooh.uima.data.remote.PatientService;
import com.teddykavooh.uima.domain.PatientManager;
import com.teddykavooh.uima.model.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PatientListingActivity extends AppCompatActivity {
    private static final String TAG = "PatientListingActivity";
    private RecyclerView rvLocalPatients, rvRemotePatients;
    private PatientAdapter localPatientAdapter, remotePatientAdapter;
    private PatientManager patientManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_listing);

        Button addPatientButton = findViewById(R.id.addPatientButton);
        rvLocalPatients = findViewById(R.id.rvLocalPatients);
        rvRemotePatients = findViewById(R.id.rvRemotePatients);

        addPatientButton.setOnClickListener(v -> {
                    Intent intent = new Intent(PatientListingActivity.this, RegisterPatientActivity.class);
                    startActivity(intent);
                }
        );

        // Setup Local RecyclerView
        rvLocalPatients.setLayoutManager(new LinearLayoutManager(this));
        localPatientAdapter = new PatientAdapter(new ArrayList<>());
        rvLocalPatients.setAdapter(localPatientAdapter);

        // Setup Remote RecyclerView
        rvRemotePatients.setLayoutManager(new LinearLayoutManager(this));
        remotePatientAdapter = new PatientAdapter(new ArrayList<>());
        rvRemotePatients.setAdapter(remotePatientAdapter);

        // Use PatientManager
        PatientService service = ApiClient.getRetrofitInstance().create(PatientService.class);
        patientManager = new PatientManager(getApplicationContext(), service);

        // Sync Button
        Button syncButton = findViewById(R.id.syncButton);
        syncButton.setOnClickListener(v -> syncPatients());
    }

    // Sync Patients
    private void syncPatients() {
        patientManager.syncPatients();
    }

    // Load patient from local
    private void loadLocalPatients() {
        new Thread(() -> {
            List<Patient> allPatients = patientManager.getAllPatientsLocal();

            // Separate patients into synced and unsynced lists
            List<Patient> localPatients = allPatients.stream().filter(p -> !p.isSynced()).collect(Collectors.toList());
            List<Patient> remotePatients = allPatients.stream().filter(Patient::isSynced).collect(Collectors.toList());

            runOnUiThread(() -> {
                localPatientAdapter.setPatients(localPatients);
                remotePatientAdapter.setPatients(remotePatients);
            });
            // Logger
            Log.d(TAG, "loadLocalPatients: Local=" + localPatients.size() + ", Remote=" + remotePatients.size());

        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLocalPatients();
    }
}
