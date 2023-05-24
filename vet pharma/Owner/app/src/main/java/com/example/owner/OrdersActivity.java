package com.example.owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrdersActivity extends AppCompatActivity {

RecyclerView ordersList;
DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        ordersList = findViewById(R.id.recyclerNewOrders);
        ordersList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Orders> options = new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(ordersRef,Orders.class)
                .build();

        FirebaseRecyclerAdapter<Orders,OrdersVievHolder> adapter = new FirebaseRecyclerAdapter<Orders, OrdersVievHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrdersVievHolder ordersVievHolder, final int i, @NonNull final Orders orders)
            {
                ordersVievHolder.userName.setText("Name: " + orders.getName());
                ordersVievHolder.phoneNumber.setText("Phone: " + orders.getPhone());
                ordersVievHolder.totalPrice.setText("Total Price: " + orders.getTotalAmount());
                ordersVievHolder.dateTime.setText("Date: " + orders.getDate() + "Time: " + orders.getTime());
                ordersVievHolder.shippingAddress.setText("Address: " + orders.getHomeAddress() + " " + orders.getStreetAddress());
                ordersVievHolder.pincode.setText("Pincode: " + orders.getPincode());

                ordersVievHolder.showProducts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        String uID = getRef(i).getKey();

                        Intent intent = new Intent(OrdersActivity.this,AdminUserProducts.class);
                        intent.putExtra("uid",uID);
                        startActivity(intent);
                    }
                });

                ordersVievHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Yes",
                                        "No"
                                };

                        AlertDialog.Builder builder = new AlertDialog.Builder(OrdersActivity.this);
                        builder.setTitle("Have you shipped this order products ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                if (which == 0)
                                {
                                    String uID = getRef(i).getKey();

                                    RemoveOrder(uID);
                                }
                                else
                                {
                                    finish();
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public OrdersVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                return new OrdersVievHolder(view);
            }
        };
        ordersList.setAdapter(adapter);
        adapter.startListening();

    }

    private void RemoveOrder(String uID)
    {
        ordersRef.child(uID).removeValue();
    }

    public static class OrdersVievHolder extends RecyclerView.ViewHolder
    {
        public TextView userName, phoneNumber, totalPrice, dateTime, shippingAddress,pincode;
        public Button showProducts;


        public OrdersVievHolder(@NonNull View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.userNameOrders);
            phoneNumber = itemView.findViewById(R.id.phoneNumberOrders);
            totalPrice = itemView.findViewById(R.id.totalPriceOrders);
            dateTime = itemView.findViewById(R.id.dateTimeOrders);
            shippingAddress = itemView.findViewById(R.id.addressOrders);
            showProducts = itemView.findViewById(R.id.showAllProducts);
            pincode = itemView.findViewById(R.id.pincodeOrders);
        }
    }
}
