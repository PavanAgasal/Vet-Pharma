package com.example.owner;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {

    Button addDoc,viewDoc,addProd,viewProd,viewOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationHome);

        Paper.init(this);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.logout:
                        Paper.book().destroy();
                        startActivity(new Intent(getApplicationContext(),LoginAdmin.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.help:
                        startActivity(new Intent(getApplicationContext(),Feedbackss.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        addDoc = findViewById(R.id.addDoctorbuttonMain);
        viewDoc = findViewById(R.id.viewDoctorbuttonMain);
        addProd = findViewById(R.id.addProductbuttonMain);
        viewProd = findViewById(R.id.viewProductbuttonMain);
        viewOrder = findViewById(R.id.viewOrdersbuttonMain);

        addDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DoctorAdd.class);
                startActivity(intent);
            }
        });

        viewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,DoctorsActivity.class);
                startActivity(intent);
            }
        });

        addProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,ProductAdd.class);
                startActivity(intent);
            }
        });

        viewProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,PharmacyActivity.class);
                startActivity(intent);
            }
        });

        viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,OrdersActivity.class);
                startActivity(intent);
            }
        });
    }

}
