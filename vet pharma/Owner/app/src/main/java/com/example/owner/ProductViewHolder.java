package com.example.owner;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductName, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.productimageViewCustomProduct);
        txtProductName = itemView.findViewById(R.id.nametextViewCustomProduct);
        txtProductPrice = itemView.findViewById(R.id.pricetextViewCustomProduct);

    }

    public  void setItemClickListener(ItemClickListener Listener)
    {
        this.listener = Listener;
    }

    @Override
    public void onClick(View v)
    {
        listener.onClick(v, getAdapterPosition(), false);
    }
}
