package com.example.pharma;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class AppointmentActivity extends AppCompatActivity {

    private static final String TAG = "AppointmentActivity";
    TextView displayDate;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Button confirmAppointment;
    TextView name;
    TextView ownerName, animalAge, animalType,currentDate;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView session;
    String doctorName, doctorContact;
    String userID;

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.nametextViewAppointment);
        doctorName = getIntent().getStringExtra("Name");
        name.setText(doctorName);

        doctorContact = getIntent().getStringExtra("Contact");

        ownerName = findViewById(R.id.ownerNameeditTextAppointment);
        animalAge = findViewById(R.id.animalAgeeditTextAppointment);
        animalType = findViewById(R.id.animalTypeeditTextAppointment);
        radioGroup = findViewById(R.id.sessionRadioGroupAppointment);
        session = findViewById(R.id.sessiontextViewAppointment);
        currentDate = findViewById(R.id.currentDatetextViewCustomAppointment);

        confirmAppointment = findViewById(R.id.confirmAppointmentbuttonAppointment);
        displayDate = findViewById(R.id.datetextViewAppointment);

        displayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AppointmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyy" + dayOfMonth + "/" + month + "/" + year);
                String date = dayOfMonth + "/" + month + "/" + year;
                displayDate.setText(date);
            }
        };

        confirmAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String docName = name.getText().toString();
                final String owner = ownerName.getText().toString();
                final String type = animalType.getText().toString();
                final String age = animalAge.getText().toString();
                final String date = displayDate.getText().toString();
                final String time = session.getText().toString();

                if (owner.isEmpty() || type.isEmpty() || age.isEmpty() || date.isEmpty() || time.isEmpty()) {
                    Toast.makeText(AppointmentActivity.this, "None of the fields should be empty", Toast.LENGTH_LONG).show();
                }
                else
                    {
                        Calendar calendar = Calendar.getInstance();
                       final String current = DateFormat.getDateInstance().format(calendar.getTime());
                        userID = mAuth.getCurrentUser().getUid();

                        CollectionReference dbCart = db.collection("UserData").document(userID).collection("Appointments");

                        Appointment appointment = new Appointment(
                                docName,
                                owner,
                                type,
                                age,
                                date,
                                time,
                                current

                        );

                        dbCart.add(appointment)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {

                                        CollectionReference dbCart = db.collection("DoctorView").document(doctorContact).collection("Appointment");

                                        Appointment appointment = new Appointment(
                                                docName,
                                                owner,
                                                type,
                                                age,
                                                date,
                                                time,
                                                current

                                        );
                                        dbCart.add(appointment)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(AppointmentActivity.this, "Appointment confirmed", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(AppointmentActivity.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AppointmentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        session.setText(radioButton.getText());
    }
}
