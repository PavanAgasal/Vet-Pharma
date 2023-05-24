package com.example.pharma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.pharma.Model.Doctor;
import com.example.pharma.ViewHolder.DoctorAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    DoctorAdapter doctorAdapter;
    List<Doctor> data;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);


        Toolbar toolbar = findViewById(R.id.toolbarDoctor);
        setSupportActionBar(toolbar);


        recyclerview = findViewById(R.id.recyclerViewDoctor);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        doctorAdapter = new DoctorAdapter(data,this);

        recyclerview.setAdapter(doctorAdapter);

        db = FirebaseFirestore.getInstance();

        db.collection("Doctors").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(!queryDocumentSnapshots.isEmpty())
                        {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                            for(DocumentSnapshot d : list){

                                Doctor a = d.toObject(Doctor.class);
                                data.add(a);
                            }

                            doctorAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}
