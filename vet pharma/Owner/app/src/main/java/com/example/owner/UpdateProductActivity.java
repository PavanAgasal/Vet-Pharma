package com.example.owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateProductActivity extends AppCompatActivity {

    EditText upName,upPrice,upQuantity,upDescription;
    Button upUpdate,upDelete;
    ImageView upImage;
    String productID ="";
    DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        upName = findViewById(R.id.productNameeditTextUpdateProduct);
        upPrice = findViewById(R.id.productPriceeditTextUpdateProduct);
        upQuantity = findViewById(R.id.productQtyeditTextUpdateProduct);
        upDescription = findViewById(R.id.productDescriptioneditTextUpdateProduct);
        upUpdate = findViewById(R.id.updateProductbuttonUpdateProduct);
        upDelete = findViewById(R.id.deleteProductbuttonUpdateProduct);
        upImage = findViewById(R.id.imageViewUpdateProduct);

        displayProductInfo();

        upUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                updateProduct();
            }
        });

        upDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               DeleteProduct();
            }
        });
    }

    private void DeleteProduct()
    {
        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
              if (task.isSuccessful())
              {
                  Toast.makeText(UpdateProductActivity.this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(UpdateProductActivity.this,PharmacyActivity.class);
                  startActivity(intent);
                  finish();
              }
            }
        });
    }

    private void updateProduct()
    {
        String prodName = upName.getText().toString();
        String prodPrice = upPrice.getText().toString();
        String prodQty = upQuantity.getText().toString();
        String prodDesc = upDescription.getText().toString();

        if (prodName.equals(""))
        {
            upName.setError("Product name required");
            upName.requestFocus();
        }
        else if (prodPrice.equals(""))
        {
            upPrice.setError("Product price required");
            upPrice.requestFocus();
        }
        else if (prodQty.equals(""))
        {
            upQuantity.setError("Product quantity required");
            upQuantity.requestFocus();
        }
        else if (prodDesc.equals(""))
        {
            upDescription.setError("Product description required");
            upDescription.requestFocus();
        }
        else
        {
            HashMap<String,Object> productMap = new HashMap<>();
            productMap.put("pid", productID);
            productMap.put("description", prodDesc);
            productMap.put("name", prodName);
            productMap.put("price", prodPrice);
            productMap.put("quantity", prodQty);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                   if (task.isSuccessful())
                   {
                       Toast.makeText(UpdateProductActivity.this, "Product Details Updated Successfully", Toast.LENGTH_SHORT).show();
                       Intent intent = new Intent(UpdateProductActivity.this,PharmacyActivity.class);
                       startActivity(intent);
                       finish();
                   }
                }
            });
        }

    }

    private void displayProductInfo()
    {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
              if (dataSnapshot.exists())
              {
                  String pname = dataSnapshot.child("name").getValue().toString();
                  String pprice = dataSnapshot.child("price").getValue().toString();
                  String pquantity = dataSnapshot.child("quantity").getValue().toString();
                  String pdescription = dataSnapshot.child("description").getValue().toString();
                  String pimage = dataSnapshot.child("image").getValue().toString();

                  upName.setText(pname);
                  upPrice.setText(pprice);
                  upQuantity.setText(pquantity);
                  upDescription.setText(pdescription);
                  Picasso.get().load(pimage).into(upImage);

              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
