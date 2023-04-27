package com.example.platforma_ctf;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Button loginButton = findViewById(R.id.login_button);
      //  Button registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }
}
