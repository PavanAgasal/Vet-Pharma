package com.example.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointViewHolder> {

    List<Appointment> appointmentList;
    Context context;


    public AppointmentAdapter(List<Appointment> appointmentList, Context context) {
        this.appointmentList = appointmentList;
        this.context = context;
    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycle_appointments,parent,false);
        return new AppointmentAdapter.AppointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointViewHolder holder, int position) {

        Appointment appointment = appointmentList.get(position);
        holder.docName.setText(appointment.getDocname());
        holder.owner.setText(appointment.getOwnername());
        holder.animalType.setText(appointment.getAnimaltype());
        holder.animalAge.setText(appointment.getAnimalage());
        holder.date.setText(appointment.getDate());
        holder.session.setText(appointment.getTime());
        holder.current.setText(appointment.getCurrent());

    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class AppointViewHolder extends RecyclerView.ViewHolder {

        TextView docName;
        TextView owner;
        TextView animalType;
        TextView animalAge;
        TextView date;
        TextView session;
        TextView current;

        public AppointViewHolder(@NonNull View itemView) {
            super(itemView);
            docName = itemView.findViewById(R.id.doctorNametextViewCustomAppoint);
            owner = itemView.findViewById(R.id.ownerNametextViewCustomAppoint);
            animalType = itemView.findViewById(R.id.animalTypetextViewCustomAppoint);
            animalAge = itemView.findViewById(R.id.animalAgetextViewCustomAppoint);
            date = itemView.findViewById(R.id.datetextViewCustomAppoint);
            session = itemView.findViewById(R.id.sessiontextViewCustomAppoint);
            current = itemView.findViewById(R.id.currentDatetextViewCustomAppointment);
        }
    }
}
