package com.apaaja.eventapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText txtUsername, txtPassword;
    private Button btnLogin, btnRegister;
    private CheckBox chkRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        chkRememberMe = findViewById(R.id.chkRememberMe);

        if (Preferences.getStatusLogin(this)) {
            txtUsername.setText(Preferences.getUsername(this));
            txtPassword.setText(Preferences.getPassword(this));
            chkRememberMe.setChecked(true);
        }

        btnLogin.setOnClickListener(view -> {
            String username = txtUsername.getText().toString();
            String password = txtPassword.getText().toString();

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                if (TextUtils.isEmpty(username)) {
                    txtUsername.setError("Username is required");
                }
                if (TextUtils.isEmpty(password)) {
                    txtPassword.setError("Password is required");
                }
                return;
            }

            if (username.equals(Preferences.getUsername(Login.this)) &&
                    password.equals(Preferences.getPassword(Login.this))) {

                if (chkRememberMe.isChecked()) {
                    Preferences.setStatusLogin(Login.this, true);
                    Preferences.setUsername(Login.this, username);
                    Preferences.setPassword(Login.this, password);
                } else {
                    Preferences.setStatusLogin(Login.this, false);
                }

                Intent intent = new Intent(Login.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                txtUsername.setError("Invalid credentials");
            }
        });

        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }
}
