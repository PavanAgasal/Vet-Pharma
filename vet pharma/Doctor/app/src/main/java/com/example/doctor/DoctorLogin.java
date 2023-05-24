package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctor.Prevalant.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class DoctorLogin extends AppCompatActivity {

    EditText adminPhone, password;
    Button login;
    CheckBox rememberMe;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        adminPhone = findViewById(R.id.emailEditTextLoginAdmin);
        password = findViewById(R.id.passwordeditTextLoginAdmin);
        login = findViewById(R.id.LoginbuttonLoginAdmin);
        rememberMe = findViewById(R.id.checkBox);
        Paper.init(this);
        loadingBar = new ProgressDialog(this);

        String userPhonekey = Paper.book().read(Prevalent.UserPhoneKey);
        String userPasswordkey = Paper.book().read(Prevalent.UserPasswordKey);

        if (userPhonekey != "" && userPasswordkey != "")
        {
            if (!TextUtils.isEmpty(userPhonekey) && !TextUtils.isEmpty(userPasswordkey))
            {
                Allow(userPhonekey,userPasswordkey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginAdmin();
            }
        });
    }

    private void Allow(final String phone, final String pwd)
    {
        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child("Doctors").child(phone).exists())
                {
                    Users userData = dataSnapshot.child("Doctors").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(pwd))
                        {
                            Toast.makeText(DoctorLogin.this,"Welcome Doctor..!",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                            Intent intent = new Intent(DoctorLogin.this,YourAppointments.class);
                            intent.putExtra("Contact",phone);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(DoctorLogin.this,"You are not an authorized doctor",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loginAdmin()
    {
        String phone = adminPhone.getText().toString();
        String pwd = password.getText().toString();

        if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(pwd))
        {
            AllowAccess(phone,pwd);
        }
        else if(TextUtils.isEmpty(phone)){
            adminPhone.setError("Phone number required");
            adminPhone.requestFocus();
        }


        else if(TextUtils.isEmpty(pwd))
        {
            password.setError("Password required");
            password.requestFocus();
        }

        else
        {
            Toast.makeText(DoctorLogin.this,"None of the fields should be empty",Toast.LENGTH_LONG).show();
        }
    }

    private void AllowAccess(final String phone, final String pwd)
    {
        if (rememberMe.isChecked())
        {
           Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, pwd);
        }

        loadingBar.setTitle("Login");
        loadingBar.setMessage("Please wait...We are logging you in");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.child("Doctors").child(phone).exists())
                {
                    Users userData = dataSnapshot.child("Doctors").child(phone).getValue(Users.class);

                    if (userData.getPhone().equals(phone))
                    {
                        if (userData.getPassword().equals(pwd))
                        {
                            Toast.makeText(DoctorLogin.this,"Welcome Doctor..!",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DoctorLogin.this,YourAppointments.class);
                            intent.putExtra("Contact",phone);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            password.setError("Invalid Password");
                            password.requestFocus();
                        }
                    }
                    else
                    {
                        adminPhone.setError("Invalid Phone number");
                        adminPhone.requestFocus();
                    }
                }
                else
                {
                    Toast.makeText(DoctorLogin.this,"You are not an authorized doctor",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
