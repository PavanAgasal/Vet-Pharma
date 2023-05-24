package com.example.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class ProfileActivity extends AppCompatActivity {

    TextView name, orders, appointments, cart, logout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.nameTextViewProfile);
        orders = findViewById(R.id.ordersTextviewProfile);
        appointments = findViewById(R.id.appointmentsTextviewProfile);
        cart = findViewById(R.id.cartTextviewProfile);
        logout = findViewById(R.id.logoutTextviewProfile);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();

                Intent intent = new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,YourAppointments.class);
                startActivity(intent);
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationProfile);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.help:
                        startActivity(new Intent(getApplicationContext(),HelpActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
}
