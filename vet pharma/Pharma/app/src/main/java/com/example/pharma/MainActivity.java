package com.example.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ImageButton doctor,pharmacy;
    Button doc,medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       getSupportActionBar().hide();

        //Toolbar toolbar = findViewById(R.id.toolbarHome);
        //setSupportActionBar(toolbar);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationHome);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.help:
                        startActivity(new Intent(getApplicationContext(),HelpActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        doctor =findViewById(R.id.doctorimageButtonHome);
        pharmacy = findViewById(R.id.pharmacyimageButtonHome);
        doc = findViewById(R.id.DoctorButtonHome);
        medicine = findViewById(R.id.PharmacyButtonHome);

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DoctorActivity.class);
                startActivity(intent);
            }
        });
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DoctorActivity.class);
                startActivity(intent);
            }
        });
        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PharmacyActivity.class);
                startActivity(intent);
            }
        });
        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PharmacyActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbarhome,menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.notification) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        }
        return true;

 */
    }
