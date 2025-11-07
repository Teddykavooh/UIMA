package com.teddykavooh.uima.data.remote;

import com.teddykavooh.uima.model.AddVitalsResponse;
import com.teddykavooh.uima.model.Vitals;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface VitalsService {
    @POST("visits/add")
    Call<AddVitalsResponse> addVitals(@Body Vitals vitals);
}
