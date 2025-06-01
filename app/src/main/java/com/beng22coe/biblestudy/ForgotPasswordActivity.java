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
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        emailInput = findViewById(R.id.email_input);
        resetButton = findViewById(R.id.reset_button);
        backToLoginLink = findViewById(R.id.back_to_login);
        
        // Set click listeners
        resetButton.setOnClickListener(v -> attemptPasswordReset());
        
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

        // Check if email exists in database
        if (!databaseHelper.checkEmail(email)) {
            emailInput.setError("Email not found");
            return;
        }

        // In a real app, you would send a password reset email
        // For this example, we'll just show a success message
        
        // Get username associated with email
        String username = databaseHelper.getUsernameByEmail(email);
        
        // For demo purposes, we'll reset the password to a default value
        String newPassword = "reset123";
        int result = databaseHelper.updatePassword(email, newPassword);
        
        if (result > 0) {
            Toast.makeText(this, "Password reset for " + username + ". New password: " + newPassword, Toast.LENGTH_LONG).show();
            finish(); // Go back to login activity
        } else {
            Toast.makeText(this, "Password reset failed", Toast.LENGTH_SHORT).show();
        }
    }
}
