package com.teddykavooh.uima.data.repository;

import com.teddykavooh.uima.data.remote.AuthService;
import com.teddykavooh.uima.model.LoginResponse;
import com.teddykavooh.uima.model.SignupResponse;
import com.teddykavooh.uima.model.User;

import retrofit2.Call;
import retrofit2.Callback;

// Auth Repository
public class AuthRepository {
    private final AuthService authservice;

    public AuthRepository(AuthService authservice) {
        this.authservice = authservice;
    }

    // Signup
    public void signup(User user, Callback<SignupResponse> callback) {
        Call<SignupResponse> call = authservice.signup(user);
        call.enqueue(callback);
    }

    // Login
    public void login(User user, Callback<LoginResponse> callback) {
        Call<LoginResponse> call = authservice.login(user);
        call.enqueue(callback);
    }
}
