package com.teddykavooh.uima.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Retrofit instance
public class ApiClient {
    private static final String BASE_URL = "https://patientvisitapis.intellisoftkenya.com/api/";
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Converts JSON <-> Java objects
                    .build();
        }
        return retrofit;
    }
}
