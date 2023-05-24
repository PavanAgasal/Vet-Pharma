package com.example.pharma;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YourAppointments extends AppCompatActivity {
    RecyclerView recyclerView;
    AppointmentAdapter appointmentAdapter;
    List<Appointment> appointmentList;
    FirebaseFirestore fstore;
    FirebaseAuth mAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_appointments);


        recyclerView = findViewById(R.id.recyclerViewAppoint);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appointmentList = new ArrayList<>();
        appointmentAdapter = new AppointmentAdapter(appointmentList, this);

        recyclerView.setAdapter(appointmentAdapter);

        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        fstore.collection("UserData").document(userID).collection("Appointments").get()
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
}

