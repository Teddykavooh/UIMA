package com.teddykavooh.uima.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.teddykavooh.uima.R;
import com.teddykavooh.uima.data.remote.ApiClient;
import com.teddykavooh.uima.data.remote.PatientService;
import com.teddykavooh.uima.data.repository.PatientRepository;
import com.teddykavooh.uima.domain.PatientManager;
import com.teddykavooh.uima.model.Patient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterPatientActivity extends AppCompatActivity {
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etUniqueID;
    private TextInputEditText etDOB, etGender;
    private TextInputEditText etRegDate;
    private MaterialButton btnSave, btnClose, btnSync;
    private PatientRepository patientRepository;
    private final Calendar calender = Calendar.getInstance();
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
            Locale.getDefault());
    private PatientManager patientManager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);

        // Init Retrofit Repository
        patientRepository = new PatientRepository(getApplicationContext(), ApiClient.getRetrofitInstance().create(PatientService.class));

        // Init Views
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etUniqueID = findViewById(R.id.etUnique);
        etDOB = findViewById(R.id.etDOB);
        etGender = findViewById(R.id.etGender);
        etRegDate = findViewById(R.id.etRegDate);
        btnSave = findViewById(R.id.btnSave);
        btnClose = findViewById(R.id.btnClose);
        btnSync = findViewById(R.id.btnSync);


        // Set regDate today
        etRegDate.setText(sdf.format(calender.getTime()));

        // Generate Unique ID
        etUniqueID.setText(String.valueOf(System.currentTimeMillis()));

        // Date Picker for DOB
        etDOB.setOnClickListener(v -> showDatePicker(etDOB));

        // Select Gender
        etGender.setOnClickListener(v -> showGenderPicker());

        // Close Activity
        btnClose.setOnClickListener(v -> finish());

        // Save Patient
        btnSave.setOnClickListener(v -> {
            savePatient();
        });

        context = this.getApplicationContext();

        // Init PatientManager
        PatientService service = ApiClient.getRetrofitInstance().create(PatientService.class);
        patientManager = new PatientManager(getApplicationContext(), service);

        // TODO: Sync Button
        btnSync.setOnClickListener(v -> {
            Toast.makeText(this, "Syncing patients ...", Toast.LENGTH_LONG).show();
            new Thread(() -> {
                patientRepository.syncPatients();
                runOnUiThread(() -> Toast.makeText(this, "Sync process initiated",
                        Toast.LENGTH_SHORT).show());
            }).start();
        });
    }

    // DatePicker
    private void showDatePicker(TextInputEditText etDOB) {
        new DatePickerDialog(this, (DatePicker view, int year, int monthOfYear, int dayOfMonth) -> {
            calender.set(year,monthOfYear,dayOfMonth);
            etDOB.setText(sdf.format(calender.getTime()));
        }, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH)).show();
    }

    // Gender Picker
    private void showGenderPicker() {
        String[] genders = {"Male", "Female", "Other"};
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Select Gender")
                .setItems(genders, (dialog, which) -> etGender.setText(genders[which]))
                .show();
    }

    // Save Patient
    private void savePatient() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String uniqueID = etUniqueID.getText().toString().trim();
        String dob = etDOB.getText().toString().trim();
        String gender = etGender.getText().toString().trim();
        String regDate = etRegDate.getText().toString().trim();

        // Validate data
        if (firstName.isEmpty() || lastName.isEmpty() || dob.isEmpty() || gender.isEmpty() ||
                regDate.isEmpty() || uniqueID.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Populate Patient Object
        Patient patient = new Patient(uniqueID, firstName, lastName, dob, gender, regDate);

        // Save locally
        new Thread(() -> {
            patientManager.registerPatientLocal(uniqueID, firstName, lastName, dob, gender, regDate);
            runOnUiThread(() -> {
                Toast.makeText(this, "Patient saved locally!", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}
