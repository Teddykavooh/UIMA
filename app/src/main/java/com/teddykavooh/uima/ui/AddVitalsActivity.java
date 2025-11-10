package com.teddykavooh.uima.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.teddykavooh.uima.R;
import com.teddykavooh.uima.data.remote.ApiClient;
import com.teddykavooh.uima.data.remote.VitalsService;
import com.teddykavooh.uima.data.repository.VitalsRepository;
import com.teddykavooh.uima.domain.BmiCalculator;
import com.teddykavooh.uima.domain.VitalsManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddVitalsActivity extends AppCompatActivity {
    private final Calendar calender = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private VitalsRepository vitalsRepository;
    private TextInputEditText visitDate, height, weight, bmi, patientID, vitalsID;
    private MaterialButton close, save, sync;
    private VitalsManager vitalsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_vitals);

        // Init Vitals Repository
        vitalsRepository = new VitalsRepository(this,
                ApiClient.getRetrofitInstance().create(VitalsService.class));

        // View Binds
        visitDate = findViewById(R.id.etVisitDate);
        height = findViewById(R.id.etHeight);
        weight = findViewById(R.id.etWeight);
        bmi = findViewById(R.id.etBMI);
        patientID = findViewById(R.id.etPatientID);
        vitalsID = findViewById(R.id.etVitalsID);
        save = findViewById(R.id.btnSave);
        close = findViewById(R.id.btnClose);
        sync = findViewById(R.id.btnSync);

        // Make BMI field non-editable as it is a calculated value
        bmi.setFocusable(false);
        bmi.setClickable(false);

        // Init Vitals Manager
        vitalsManager = new VitalsManager(vitalsRepository);

        // Set visitDate today
        visitDate.setText(sdf.format(calender.getTime()));

        // The vitals ID is handled by the database, so we hide this field.
        vitalsID.setVisibility(View.GONE);

        // Add TextWatchers for real-time BMI calculation
        TextWatcher bmiCalculatorWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* No-op */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { /* No-op */ }

            @Override
            public void afterTextChanged(Editable s) {
                calculateAndSetBmi();
            }
        };
        height.addTextChangedListener(bmiCalculatorWatcher);
        weight.addTextChangedListener(bmiCalculatorWatcher);

        // Save Vitals
        save.setOnClickListener(v -> {
            saveVitals();
        });

        // Close Activity
        close.setOnClickListener(v -> {
            finish();
        });

        // Sync Vitals
        sync.setOnClickListener(v -> {
            syncVitals();
        });
    }

    private void calculateAndSetBmi() {
        String heightStr = height.getText().toString();
        String weightStr = weight.getText().toString();

        if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
            try {
                double heightValue = Double.parseDouble(heightStr);
                double weightValue = Double.parseDouble(weightStr);
                double bmiValue = BmiCalculator.calculate(weightValue, heightValue);
                bmi.setText(String.format("%.1f", bmiValue));
            } catch (NumberFormatException e) {
                // If parsing fails, clear the BMI field
                bmi.setText("");
            }
        }
    }

    private void saveVitals() {
        String visitDateStr = this.visitDate.getText().toString();
        String heightStr = this.height.getText().toString();
        String weightStr = this.weight.getText().toString();
        String patientIDStr = this.patientID.getText().toString();
        // Set default value
        String patient_id_remote_str = "NONE YET";


        // Validate data
        if (visitDateStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty() || patientIDStr.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            vitalsManager.addVitals(visitDateStr, heightStr, weightStr, patientIDStr,
                    patient_id_remote_str);
            runOnUiThread(() -> {
                Toast.makeText(this, "Vitals saved locally", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }

    private void syncVitals() {
        new Thread(() -> {
            vitalsManager.syncVitals();
        }).start();
    }
}
