package com.beng22coe.biblestudy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    private TextInputEditText confirmPasswordInput;
    private MaterialButton signupButton;
    private TextView loginLink;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        usernameInput = findViewById(R.id.username_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        confirmPasswordInput = findViewById(R.id.confirm_password_input);
        signupButton = findViewById(R.id.signup_button);
        loginLink = findViewById(R.id.login_link);

        // Set click listeners
        signupButton.setOnClickListener(v -> attemptSignup());
        
        loginLink.setOnClickListener(v -> {
            finish(); // Go back to login activity
        });
    }

    private void attemptSignup() {
        String username = usernameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        // Simple validation
        if (username.isEmpty()) {
            usernameInput.setError("Username cannot be empty");
            return;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email address");
            return;
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError("Passwords do not match");
            return;
        }

        // Check if username already exists
        if (databaseHelper.checkUsername(username)) {
            usernameInput.setError("Username already exists");
            return;
        }

        // Check if email already exists
        if (databaseHelper.checkEmail(email)) {
            emailInput.setError("Email already exists");
            return;
        }

        // Add user to database
        long result = databaseHelper.addUser(username, email, password);
        
        if (result > 0) {
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
            // Return to login screen
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}