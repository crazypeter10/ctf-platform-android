package com.example.platforma_ctf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        userManager = new UserManager(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (userManager.authenticateUser(username, password)) {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    // Navigate to the ChallengeListActivity
                    Intent intent = new Intent(LoginActivity.this, ChallengeListActivity.class);
                    intent.putExtra("username", username); // Add this line to pass the username

                    startActivity(intent);
                    finish(); // Close LoginActivity

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (userManager.registerUser(username, password)) {
                    Toast.makeText(LoginActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
