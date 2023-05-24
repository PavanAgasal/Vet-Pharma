package com.example.owner;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Doctor> data;

    Context context;


    public Adapter(List<Doctor> data, Context context) {
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
        //holder.image.setImageDrawable(context.getResources().getDrawable(doctor.getImage()));
       // holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
         //   @Override
           // public void onClick(View v) {
             //   Intent intent = new Intent(context,Appointment.class);
               // intent.putExtra("Name",model.getDoctorName());
                //context.startActivity(intent);
           // }
       // });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textName;
        TextView textQualification;
        //ImageView image;
       // RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                textName = itemView.findViewById(R.id.docnameTextViewCustomDoctor);
                textQualification = itemView.findViewById(R.id.docQualificationtextViewCustomDoctor);
               // image = itemView.findViewById(R.id.doctorimageViewCustom);
              //  relativeLayout = itemView.findViewById(R.id.relativeLayout);

            itemView.setOnClickListener(this);

            }

        @Override
        public void onClick(View v) {
            Doctor doctor = data.get(getAdapterPosition());
            Intent intent = new Intent(context,UpdateDoctorActivity.class);
            intent.putExtra("Doctors",doctor);
            context.startActivity(intent);

        }
    }




}
