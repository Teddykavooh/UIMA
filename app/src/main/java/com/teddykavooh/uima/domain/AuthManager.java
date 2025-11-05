package com.teddykavooh.uima.domain;

import com.teddykavooh.uima.data.repository.AuthRepository;
import com.teddykavooh.uima.model.LoginResponse;
import com.teddykavooh.uima.model.SignupResponse;
import com.teddykavooh.uima.model.User;

import retrofit2.Callback;

// Auth Manager
public class AuthManager {
    private final AuthRepository repository;

    public AuthManager(AuthRepository repository) {
        this.repository = repository;
    }

    public void signup(String firstname, String lastname, String email, String password, Callback<SignupResponse> callback) {
        User user = new User(firstname, lastname, email, password);
        repository.signup(user, callback);
    }

    public void login(String email, String password, Callback<LoginResponse> callback) {
        User user = new User(null, null, email, password);
        repository.login(user, callback);
    }

}
