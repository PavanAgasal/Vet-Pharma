package com.example.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DoctorAdd extends AppCompatActivity {

    EditText mName, mEmail, mContact, mQualification;
    Button mUpdatebtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_add);

        db = FirebaseFirestore.getInstance();

        mName = findViewById(R.id.docNameeditTextDoctorAdd);
        mEmail = findViewById(R.id.docEmaileditTextDoctorAdd);
        mContact = findViewById(R.id.docContacteditTextDoctorAdd);
        mQualification = findViewById(R.id.docQualificationeditTextDoctorAdd);
        mUpdatebtn = findViewById(R.id.doctorUpdatebuttonDoctorAdd);


        mUpdatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString().trim();
                String contact = mContact.getText().toString();
                String qualification = mQualification.getText().toString();


                if(name.isEmpty() || email.isEmpty() || contact.isEmpty() || qualification.isEmpty())
                {
                    Toast.makeText(DoctorAdd.this,"None of the fields should be empty",Toast.LENGTH_LONG).show();
                }

                else{

                    CollectionReference dbdoctors = db.collection("Doctors");

                    Doctor doctor = new Doctor(
                            name,
                            email,
                            contact,
                            qualification
                    );

                    dbdoctors.add(doctor)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    Toast.makeText(DoctorAdd.this,"Doctor details added",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(DoctorAdd.this,HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(DoctorAdd.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                }
                            });
                }
            }
        });
    }
}
