package com.teddykavooh.uima.data.remote;

import com.teddykavooh.uima.model.LoginResponse;
import com.teddykavooh.uima.model.SignupResponse;
import com.teddykavooh.uima.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

//Retrofit API Interface
public interface AuthService {
    @POST("user/signup")
    Call<SignupResponse> signup(@Body User user);

    @POST("user/signin")
    Call<LoginResponse> login(@Body User user);
}
