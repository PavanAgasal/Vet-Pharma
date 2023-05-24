package com.example.owner;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HelpViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    TextView custName;
    TextView custContact;
    TextView custEmail;
    TextView custFeedback;
    TextView date;
    private ItemClickListener itemClickListener;

    public HelpViewHolder(@NonNull View itemView)
    {
        super(itemView);

        custName = itemView.findViewById(R.id.custNametextViewCustomHelp);
        custContact = itemView.findViewById(R.id.custContacttextViewCustomHelp);
        custEmail = itemView.findViewById(R.id.custEmailtextViewCustomHelp);
        custFeedback = itemView.findViewById(R.id.custFeedbacktextViewCustomHelp);
        date = itemView.findViewById(R.id.currentDatetextViewCustomHelp);
    }

    @Override
    public void onClick(View v)
    {
        this.itemClickListener = itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }
}
