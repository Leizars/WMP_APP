package com.apaaja.eventapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    private EditText txtUsername, txtPassword, txtConfirmPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(view -> {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();
            String confirmPassword = txtConfirmPassword.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                if (TextUtils.isEmpty(username)) {
                    txtUsername.setError("Username is required");
                }
                if (TextUtils.isEmpty(password)) {
                    txtPassword.setError("Password is required");
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    txtConfirmPassword.setError("Confirm password is required");
                }
                return;
            }

            if (!password.equals(confirmPassword)) {
                txtConfirmPassword.setError("Passwords do not match");
                return;
            }

            Preferences.setUsername(Register.this, username);
            Preferences.setPassword(Register.this, password);

            Toast.makeText(Register.this, "Registration Successful!", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(Register.this, Login.class));
            finish();
        });
    }
}
