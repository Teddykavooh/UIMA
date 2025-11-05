package com.teddykavooh.uima.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.teddykavooh.uima.R;
import com.teddykavooh.uima.data.remote.ApiClient;
import com.teddykavooh.uima.data.remote.AuthService;
import com.teddykavooh.uima.data.repository.AuthRepository;
import com.teddykavooh.uima.domain.AuthManager;
import com.teddykavooh.uima.model.LoginResponse;
import com.teddykavooh.uima.model.SignupResponse;
import com.teddykavooh.uima.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Auth Activity
public class AuthActivity extends AppCompatActivity {
    private static final String TAG = "AuthActivity";
    private boolean isLogin = true;
    private EditText firstNameInput, lastNameInput, inputEmail, inputPassword;
    private Button btnAuth;
    private TextView toggleText;
    private AuthManager authManager;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        firstNameInput = findViewById(R.id.inputFirstName);
        lastNameInput = findViewById(R.id.inputLastName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnAuth = findViewById(R.id.btnAction);
        toggleText = findViewById(R.id.toggleText);

        authManager = new AuthManager(
                new AuthRepository(ApiClient.getRetrofitInstance().create(AuthService.class))
        );

        updateUI();

        toggleText.setOnClickListener(v -> {
            isLogin = !isLogin;
            updateUI();
        });

        btnAuth.setOnClickListener(v -> {
            String firstName = firstNameInput.getText().toString().trim();
            String lastName = lastNameInput.getText().toString().trim();
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            // Ensure fields are not empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AuthActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Handle signup or login based on isLogin flag
            if (isLogin) {
                login(email, password);
            } else {
                signup(firstName, lastName, email, password);
            }
        });
    }

    // Helper method to update UI based on isLogin flag
    private void updateUI() {
        if (isLogin) {
            firstNameInput.setVisibility(View.GONE);
            lastNameInput.setVisibility(View.GONE);
            btnAuth.setText("Login");
            toggleText.setText("Don't have an account? Sign Up");
        } else {
            firstNameInput.setVisibility(View.VISIBLE);
            lastNameInput.setVisibility(View.VISIBLE);
            btnAuth.setText("Sign Up");
            toggleText.setText("Already have an account? Log In");
        }
    }

    // Signup method
    private void signup(String firstName, String lastName, String email, String password) {
        authManager.signup(firstName, lastName, email, password, new Callback<SignupResponse>() {
            @Override
            public void onResponse(@NonNull Call<SignupResponse> call, @NonNull Response<SignupResponse> response) {
                // Handle success
                if (response.isSuccessful() && response.body() != null) {
                    SignupResponse signupResponse = response.body();
                    Toast.makeText(AuthActivity.this, signupResponse.getData().getMessage(), Toast.LENGTH_SHORT).show();
                    isLogin = true;
                    updateUI();
                } else {
                    Toast.makeText(AuthActivity.this, "Signup failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignupResponse> call, @NonNull Throwable t){
                // Handle error
                Toast.makeText(AuthActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Login method
    public void login(String email, String password) {
        authManager.login(email, password, new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                // Handle success
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    Toast.makeText(AuthActivity.this, "Welcome " + loginResponse.getData().getName(), Toast.LENGTH_SHORT).show();

                    // TODO: Navigate to main patient list activity

                    // Save token after success
                    token = loginResponse.getData().getAccess_token();
                    saveToken(token);
                } else {
                    Toast.makeText(AuthActivity.this, "Login failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t){
                // Handle error
                Toast.makeText(AuthActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Save token to shared preferences
    private void saveToken(String token) {
        getSharedPreferences("UIMA_PREFS", MODE_PRIVATE)
                .edit()
                .putString("access_token", token)
                .apply();
    }
}