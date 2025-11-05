package com.teddykavooh.uima.domain;

import com.teddykavooh.uima.data.repository.PatientRepository;
import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.PatientRegisterResponse;
import com.teddykavooh.uima.model.PatientViewResponse;
import com.teddykavooh.uima.model.PatientsListResponse;

import java.text.DateFormat;

import retrofit2.Callback;

public class PatientManager {
    private final PatientRepository repository;

    public PatientManager(PatientRepository repository) {
        this.repository = repository;
    }

    public void registerPatient(String firstname, String lastname, String uniqueId, DateFormat dob, String gender, DateFormat reg_date, Callback<PatientRegisterResponse> callback) {
        Patient patient = new Patient(firstname, lastname, uniqueId, dob, gender, reg_date);
        repository.registerPatient(patient, callback);
    }

    public void getPatient(String uniqueId, Callback<PatientViewResponse> callback) {
        Patient patient = new Patient(null, null, uniqueId, null, null, null);
        repository.getPatient(patient, callback);
    }

    public void getAllPatients(String firstname, String lastname, String uniqueId, DateFormat dob, String gender, DateFormat reg_date, Callback<PatientsListResponse> callback) {
        Patient patient = new Patient(firstname, lastname, uniqueId, dob, gender, reg_date);
        repository.getAllPatients(patient, callback);
    }

}
