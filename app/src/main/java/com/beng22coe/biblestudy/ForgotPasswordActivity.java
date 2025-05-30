package com.beng22coe.biblestudy;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ForgotPasswordActivity extends AppCompatActivity {

    private TextInputEditText emailInput;
    private MaterialButton resetButton;
    private TextView backToLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize views
        emailInput = findViewById(R.id.email_input);
        resetButton = findViewById(R.id.reset_button);
        backToLoginLink = findViewById(R.id.back_to_login);
        
        // Set click listeners
        resetButton.setOnClickListener(v -> attemptPasswordReset());
        
        // Back to login click listener
        backToLoginLink.setOnClickListener(v -> {
            finish(); // Go back to login activity
        });
    }

    private void attemptPasswordReset() {
        String email = emailInput.getText().toString().trim();

        // Simple validation
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email address");
            return;
        }

        // For demo purposes, just show success message
        // In a real app, you would send a password reset email
        Toast.makeText(this, "Password reset link sent to " + email, Toast.LENGTH_LONG).show();
        finish(); // Go back to login activity
    }
}