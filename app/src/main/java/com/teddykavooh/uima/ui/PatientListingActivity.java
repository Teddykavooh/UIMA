package com.teddykavooh.uima.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.teddykavooh.uima.R;

public class PatientListingActivity extends AppCompatActivity {
    private static final String TAG = "PatientListingActivity";
    private EditText patientIdEditText,
            patientNameEditText,
            patientAgeEditText,
            patientPhoneEditText,
            patientEmailEditText,
            patientAddressEditText;
    private TextInputLayout date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_listing);

        Button addPatientButton = findViewById(R.id.addPatientButton);

        addPatientButton.setOnClickListener(v -> {
                    Intent intent = new Intent(PatientListingActivity.this, RegisterPatientActivity.class);
                    startActivity(intent);
                }
        );
    }
}
