package com.example.platforma_ctf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Button loginButton = findViewById(R.id.login_button);
        Button jsonButton = findViewById(R.id.json_button);

        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        jsonButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, JsonActivity.class);
            startActivity(intent);
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker to the academy location
        LatLng academyLocation = new LatLng( 44.4180219, 26.0859072);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(academyLocation);
        markerOptions.title("Military Technical Academy Ferdinand I");
        map.addMarker(markerOptions);

        // Move the camera to the academy location
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(academyLocation, 17));
    }
}
