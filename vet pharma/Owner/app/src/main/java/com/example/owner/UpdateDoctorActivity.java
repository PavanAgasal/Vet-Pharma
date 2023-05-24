package com.example.owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateDoctorActivity extends AppCompatActivity {

    private EditText docName;
    private EditText docEmail;
    private EditText docContact;
    private EditText docQualification;
    Button docUpdate;
    Button docDelete;

    private FirebaseFirestore db;

    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_doctor);

        final Doctor doctor = (Doctor) getIntent().getSerializableExtra("Doctors");

        db = FirebaseFirestore.getInstance();

        docName = findViewById(R.id.docNameeditTextUpdateDoctor);
        docEmail = findViewById(R.id.docEmaileditTextUpdateDoctor);
        docContact = findViewById(R.id.docContacteditTextUpdateDoctor);
        docQualification = findViewById(R.id.docQualificationeditTextUpdateDoctor);
        docUpdate = findViewById((R.id.doctorUpdatebuttonUpdateDoctor));
        docDelete = findViewById(R.id.doctorDeletebuttonUpdateDoctor);

        try {
            docName.setText(doctor.getName());
            docEmail.setText(doctor.getEmail());
            docContact.setText(doctor.getContact());
            docQualification.setText(doctor.getQualification());
        }catch (NullPointerException ignored){}

        docUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = docName.getText().toString();
                String email = docEmail.getText().toString();
                String contact = docContact.getText().toString();
                String qualification = docQualification.getText().toString();

                Doctor a = new Doctor(
                        name, email, contact, qualification
                );

                db.collection("Doctors").document(doctor.getId())
                        .set(a)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateDoctorActivity.this, "Doctor updated", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

        docDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateDoctorActivity.this);
                builder.setTitle("Are you sure?");
                builder.setMessage("Data will be deleted permanently...");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        db.collection("Doctors").document(doctor.getId()).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(UpdateDoctorActivity.this, "Doctor details deleted", Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(UpdateDoctorActivity.this, DoctorsActivity.class));
                                        }
                                    }
                                });
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
}


