package com.example.owner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DoctorsActivity extends AppCompatActivity {

    RecyclerView recyclerview;
    Adapter adapter;
    List<Doctor> data;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        recyclerview = findViewById(R.id.recyclerViewDoctor);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        data = new ArrayList<>();
        adapter = new Adapter(data,this);

        recyclerview.setAdapter(adapter);

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
                                a.setId(d.getId());
                                data.add(a);
                            }

                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }
}
