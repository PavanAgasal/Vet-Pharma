package com.example.owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedbackss extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference helpListRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        recyclerView = findViewById(R.id.recyclerFeedbacks);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        helpListRef = FirebaseDatabase.getInstance().getReference().child("Feedbacks");

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Help> options = new FirebaseRecyclerOptions.Builder<Help>()
                .setQuery(helpListRef,Help.class)
                .build();

        FirebaseRecyclerAdapter<Help,HelpViewHolder> adapter = new FirebaseRecyclerAdapter<Help, HelpViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HelpViewHolder helpViewHolder, int i, @NonNull Help help)
            {
                helpViewHolder.custName.setText(help.getName());
                helpViewHolder.custEmail.setText(help.getEmail());
                helpViewHolder.custContact.setText(help.getPhone());
                helpViewHolder.custFeedback.setText(help.getIssue());
                helpViewHolder.date.setText(help.getDate());

            }

            @NonNull
            @Override
            public HelpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycler_help,parent,false);
                return new HelpViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}
