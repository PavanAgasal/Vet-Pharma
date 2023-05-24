package com.example.pharma.ViewHolder;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharma.AppointmentActivity;
import com.example.pharma.Model.Doctor;
import com.example.pharma.R;

import java.util.List;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    List<Doctor> data;

    Context context;


    public DoctorAdapter(List<Doctor> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycle_doctor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Doctor doctor = data.get(position);
        holder.textName.setText(doctor.getName());
        holder.textQualification.setText(doctor.getQualification());
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AppointmentActivity.class);
                intent.putExtra("Name",doctor.getName());
                intent.putExtra("Contact",doctor.getContact());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textName;
        TextView textQualification;
        Button book;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                textName = itemView.findViewById(R.id.docNameTextViewCustomDoctor);
                textQualification = itemView.findViewById(R.id.qualificationtextViewCustomDoctor);
                book = itemView.findViewById(R.id.bookbuttonCustomDoctor);


            }
    }




}
