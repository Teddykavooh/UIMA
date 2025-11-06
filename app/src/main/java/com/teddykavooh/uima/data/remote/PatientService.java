package com.teddykavooh.uima.data.remote;

import com.teddykavooh.uima.model.Patient;
import com.teddykavooh.uima.model.PatientRegisterResponse;
import com.teddykavooh.uima.model.PatientViewResponse;
import com.teddykavooh.uima.model.PatientsListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PatientService {
    @POST("patients/register")
    Call<PatientRegisterResponse> registerPatient(@Body Patient patient);

    @GET("patients/view")
    Call<PatientsListResponse> getAllPatients();

    @GET("patients/show/{id}")
    Call<PatientViewResponse> getPatient(@Path("id") String id);
}
