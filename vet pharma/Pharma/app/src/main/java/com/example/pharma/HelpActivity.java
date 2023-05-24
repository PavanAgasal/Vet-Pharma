package com.example.pharma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class HelpActivity extends AppCompatActivity {

    EditText hissue,hcontact,hemail,hname;
    Button submit;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        hissue = findViewById(R.id.issueDetailseditTextHelp);
        hcontact = findViewById(R.id.contacteditTextHelp);
        hemail = findViewById(R.id.emaileditTextHelp);
        submit = findViewById(R.id.submitbuttonHelp);
        hname = findViewById(R.id.nameeditTextHelp);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Feedback();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationHelp);

        bottomNavigationView.setSelectedItemId(R.id.help);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.help:
                        return true;
                }

                return false;
            }
        });


    }

    private void Feedback()
    {
        mAuth = FirebaseAuth.getInstance();
        String userID = mAuth.getCurrentUser().getUid();

        String saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        String name = hname.getText().toString();
        String problem = hissue.getText().toString();
        String contact = hcontact.getText().toString();
        String email = hemail.getText().toString();

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(HelpActivity.this,"Enter a valid email",Toast.LENGTH_LONG).show();
        }
        if(name.isEmpty() || problem.isEmpty() || contact.isEmpty() || email.isEmpty())
        {
            Toast.makeText(HelpActivity.this,"None of the fields should be empty",Toast.LENGTH_LONG).show();
        }
        else
        {
            DatabaseReference helpRef = FirebaseDatabase.getInstance().getReference().child("Feedbacks").child(userID);

            HashMap<String, Object> helpMap = new HashMap<>();
            helpMap.put("name", name);
            helpMap.put("email", email);
            helpMap.put("phone", contact);
            helpMap.put("issue", problem);
            helpMap.put("date",saveCurrentDate);

            helpRef.updateChildren(helpMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(HelpActivity.this,"Your feedback has been sent successfully",Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(HelpActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(HelpActivity.this,"Error: " + task.getException().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
