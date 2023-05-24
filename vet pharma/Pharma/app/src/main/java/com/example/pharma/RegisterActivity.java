package com.example.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pharma.Model.RegisterHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    EditText fname, femail, fcontact, fpassword;
    Button createAccount;
    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*
        fname = findViewById(R.id.fullnameEditTextSignup);
        femail = findViewById(R.id.emailEditTxtSignUp);
        fcontact = findViewById(R.id.contactEditTextSignUp);
        fpassword = findViewById(R.id.pwdEditTxtSignUp);
        createAccount = findViewById(R.id.confirmOrderConfirm);
        loadingBar = new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        final String name = fname.getText().toString();
        final String email = femail.getText().toString();
        final String contact = fcontact.getText().toString();
        final String password = fpassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            femail.setError("Email required");
            femail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)) {
            fpassword.setError("Password required");
            fpassword.requestFocus();
        }
      else  if (TextUtils.isEmpty(contact)) {
            fcontact.setError("Contact number required");
            fcontact.requestFocus();
        }
       else if (TextUtils.isEmpty(name)) {
            fname.setError("Full name required");
            fname.requestFocus();
        }
       else
        {
            loadingBar.setTitle("Register");
            loadingBar.setMessage("Please wait... Your accoungt is being created.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatePhoneNumber(name,contact,password,email);
        }
    }

    private void ValidatePhoneNumber(final String name, final String contact, final String password, final String email)
    {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (!(dataSnapshot.child("Users").child(contact).exists()))
                {
                    ....
                    HashMap<String,Object> userMap = new HashMap<>();
                    userMap.put("name",name);
                    userMap.put("email",email);
                    userMap.put("contact",contact);
                    userMap.put("password",password);

                    RootRef.child("Users").child(contact).updateChildren(userMap)
                    .....


                    RegisterHelper registerHelper = new RegisterHelper(name,email,contact,password);

                    RootRef.child("Users").child(contact).setValue(registerHelper)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginUser.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        loadingBar.dismiss();
                                        String e = task.getException().getMessage();
                                        Toast.makeText(RegisterActivity.this, "Error : " + e, Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this, "This phone number already exists", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         */




        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        fname = findViewById(R.id.fullnameEditTextSignup);
        femail = findViewById(R.id.emailEditTxtSignUp);
        fcontact = findViewById(R.id.contactEditTextSignUp);
        fpassword = findViewById(R.id.pwdEditTxtSignUp);
        createAccount = findViewById(R.id.confirmOrderConfirm);
        loadingBar = new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = fname.getText().toString();
                final String email = femail.getText().toString();
                final String contact = fcontact.getText().toString();
                final String password = fpassword.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !contact.isEmpty() && !password.isEmpty()) {

                    loadingBar.setTitle("Register");
                    loadingBar.setMessage("Please wait... Your accoungt is being created.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();

                                        String userID = mAuth.getCurrentUser().getUid();
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                                        HashMap<String,Object> userMap = new HashMap<>();
                                        userMap.put("name",name);
                                        userMap.put("email",email);
                                        userMap.put("contact",contact);
                                        userMap.put("password",password);

                                        databaseReference.setValue(userMap);

                                        /*
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

                                        RegisterHelper registerHelper = new RegisterHelper(name,email,contact,password);

                                       databaseReference.child(registerHelper.getContact()).setValue(registerHelper);

                                         */



                                        loadingBar.dismiss();

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        finish();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                }
                else {
                    Toast.makeText(RegisterActivity.this, "None of the fields should be empty", Toast.LENGTH_LONG).show();
                }
                if (email.isEmpty() && !contact.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                    femail.setError("Email required");
                    femail.requestFocus();
                }
                if (!email.isEmpty() && !contact.isEmpty() && password.isEmpty() && !name.isEmpty()) {
                    fpassword.setError("Password required");
                    fpassword.requestFocus();
                }
                if (!email.isEmpty() && contact.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                    fcontact.setError("Contact number required");
                    fcontact.requestFocus();
                }
                if (!email.isEmpty() && !contact.isEmpty() && !password.isEmpty() && name.isEmpty()) {
                    fname.setError("Full name required");
                    fname.requestFocus();
                }

            }
        });
    }
}



