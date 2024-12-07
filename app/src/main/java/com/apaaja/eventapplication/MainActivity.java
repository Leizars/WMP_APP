    package com.apaaja.eventapplication;

    import android.content.Intent;
    import android.os.Bundle;

    import androidx.appcompat.app.AppCompatActivity;

    public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (Preferences.getStatusLogin(this)) {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, Login.class));
                finish();
            }
        }
    }
