package com.teddykavooh.uima.domain;

import android.content.Context;

import com.teddykavooh.uima.data.remote.PatientService;
import com.teddykavooh.uima.data.repository.PatientRepository;
import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.PatientRegisterResponse;
import com.teddykavooh.uima.model.PatientViewResponse;
import com.teddykavooh.uima.model.PatientsListResponse;

import java.text.DateFormat;
import java.util.List;

import retrofit2.Callback;

public class PatientManager {
    private final PatientRepository repository;

    public PatientManager(Context context, PatientService patientService) {
        this.repository = new PatientRepository(context, patientService);
    }

    // Register patient locally(Room)
    public void registerPatientLocal(String uniqueId, String firstname, String lastname, String dob,
                                String gender, String reg_date) {
        Patient patient = new Patient(uniqueId, firstname, lastname, dob, gender, reg_date);
        repository.saveLocal(patient);
    }

    public Patient getPatientLocal(String uniqueId) {
        return repository.getPatientLocal(uniqueId);
    }

    public List<Patient> getAllPatientsLocal() {
        return repository.getAllPatientsLocal();
    }

    // Sync patients to remote
    public void syncPatients() {
        repository.syncPatients();
    }

    // Get remote patient
    public void getPatient(String uniqueId, Callback<PatientViewResponse> callback) {
        repository.getPatient(uniqueId, callback);
    }

    // Get remote patients
    public void getPatients(Callback<PatientsListResponse> callback) {
        repository.getAllPatients(callback);
    }

}
