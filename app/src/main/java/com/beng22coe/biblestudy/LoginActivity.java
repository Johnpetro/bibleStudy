package com.beng22coe.biblestudy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private MaterialButton loginButton;
    private TextView signupLink;
    private TextView forgotPasswordLink;
    private CheckBox rememberCheckbox;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize database helper
        databaseHelper = new DatabaseHelper(this);

        // Initialize views
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.login_button);
        signupLink = findViewById(R.id.signup_link);
        forgotPasswordLink = findViewById(R.id.forgot_password);
        rememberCheckbox = findViewById(R.id.remember_checkbox);

        // Set click listeners
        loginButton.setOnClickListener(v -> attemptLogin());
        
        signupLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
        
        forgotPasswordLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        // Simple validation
        if (username.isEmpty()) {
            usernameInput.setError("Username cannot be empty");
            return;
        }

        if (password.isEmpty()) {
            passwordInput.setError("Password cannot be empty");
            return;
        }

        // Check user credentials in database
        if (databaseHelper.checkUser(username, password)) {
            // Save login state if "Remember me" is checked
            if (rememberCheckbox.isChecked()) {
                // In a real app, you would use SharedPreferences to save login state.
                // For this example, we'll just show a toast
                Toast.makeText(this, "Login state saved", Toast.LENGTH_SHORT).show();
            }

            // Navigate to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
