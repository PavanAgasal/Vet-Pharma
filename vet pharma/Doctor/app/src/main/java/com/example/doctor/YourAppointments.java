package com.example.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class YourAppointments extends AppCompatActivity {
    RecyclerView recyclerView;
    AppointmentAdapter appointmentAdapter;
    List<Appointment> appointmentList;
    FirebaseFirestore fstore;
    String doctorContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_appointments);

        Toolbar toolbar = findViewById(R.id.toolbarYourAppointments);
        setSupportActionBar(toolbar);

        Paper.init(this);

        doctorContact = getIntent().getStringExtra("Contact");

        recyclerView = findViewById(R.id.recyclerViewAppoint);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentList = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(appointmentList, this);

        recyclerView.setAdapter(appointmentAdapter);

        fstore = FirebaseFirestore.getInstance();

        fstore.collection("DoctorView").document(doctorContact).collection("Appointment").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for (DocumentSnapshot d : list) {

                                Appointment a = d.toObject(Appointment.class);
                                a.setId(d.getId());
                                appointmentList.add(a);
                            }

                            appointmentAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            Paper.book().destroy();
            Intent intent = new Intent(YourAppointments.this, DoctorLogin.class);
            startActivity(intent);
            finish();
        }

        return true;
    }
}

