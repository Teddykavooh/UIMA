package com.teddykavooh.uima.data.repository;

import com.teddykavooh.uima.data.remote.PatientService;
import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.PatientRegisterResponse;
import com.teddykavooh.uima.model.PatientViewResponse;
import com.teddykavooh.uima.model.PatientsListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class PatientRepository {
    private final PatientService patientService;

    public PatientRepository(PatientService patientService) {
        this.patientService = patientService;
    }

    public void getPatient(Patient patient, Callback<PatientViewResponse> callback) {
        Call<PatientViewResponse> call = patientService.getPatient(patient.getUniqueId());
        call.enqueue(callback);
    }

    public void registerPatient(Patient patient, Callback<PatientRegisterResponse> callback) {
        Call<PatientRegisterResponse> call = patientService.registerPatient(patient);
        call.enqueue(callback);
    }

    public void getAllPatients(Patient patient, Callback<PatientsListResponse> callback) {
        Call<PatientsListResponse> call = patientService.getAllPatients();
        call.enqueue(callback);
    }

    // TODO: Add other CRUD operations as needed
//    public void updatePatient(Patient patient) {
//        patientService.updatePatient(patient);
//    }
//
//    public void deletePatient(String patientId) {
//        patientService.deletePatient(patientId);
//    }
//
//    public List<Patient> searchPatients(String query) {
//        return patientService.searchPatients(query);
//    }
}
