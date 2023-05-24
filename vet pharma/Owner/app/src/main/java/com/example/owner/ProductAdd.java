package com.example.owner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductAdd extends AppCompatActivity {

        EditText mName, mPrice, mQuantity, mDescription ;
        Button mAdd,chooseImage;
        ImageView prodImage;
        String name,price,quantity,description,saveCurrentDate, saveCurrentTime;
        private static final int GalleryPick = 1;
        private Uri imageuri;
        private String productRandomKey, downloadImgeUrl;
        private StorageReference ProductImageRef;
        private DatabaseReference productRef;
        ProgressDialog loadingBar;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_add);

            mName = findViewById(R.id.productNameeditTextProductAdd);
            mPrice = findViewById(R.id.productPriceeditTextProductAdd);
            mQuantity = findViewById(R.id.productQtyeditTextProductAdd);
            mDescription = findViewById(R.id.productDescriptioneditTextProductAdd);
            mAdd = findViewById(R.id.addProductbuttonProductAdd);
            chooseImage = findViewById(R.id.chooseImagebuttonProductAdd);
            loadingBar = new ProgressDialog(this);


        prodImage = findViewById(R.id.productimageViewCustomProduct);

            ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product_Images");
            productRef = FirebaseDatabase.getInstance().getReference().child("Products");

            chooseImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OpenGallery();
                }
            });

            mAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    AddProductData();
                }
            });

    }


    private void OpenGallery()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageuri = data.getData();
            prodImage.setImageURI(imageuri);
        }
    }

    private void AddProductData()
    {
        name = mName.getText().toString();
        price = mPrice.getText().toString();
        quantity = mQuantity.getText().toString();
        description = mDescription.getText().toString();

        if (imageuri == null)
        {
            Toast.makeText(this,"Product image is mandatory",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(name))
        {
            Toast.makeText(this,"Product name required",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(price))
        {
            Toast.makeText(this,"Product price required",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(quantity))
        {
            Toast.makeText(this,"Product quantity required",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(description))
        {
            Toast.makeText(this,"Product description required",Toast.LENGTH_LONG).show();
        }
        else
        {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation()
    {

        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Please wait...while we add the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, YYYY");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(imageuri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageuri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(ProductAdd.this, e.getMessage(),Toast.LENGTH_LONG).show();
                loadingBar.dismiss();
            }
        })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        Toast.makeText(ProductAdd.this, "Product image uploaded successfully",Toast.LENGTH_LONG).show();

                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                            {
                                if(!task.isSuccessful())
                                {
                                    throw task.getException();
                                }

                                downloadImgeUrl = filePath.getDownloadUrl().toString();
                                return filePath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task)
                            {
                              if(task.isSuccessful())
                              {
                                  downloadImgeUrl = task.getResult().toString();

                                  SaveProductInfoToDatabase();
                              }
                            }
                        });
                    }
                });

    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", description);
        productMap.put("image", downloadImgeUrl);
        productMap.put("name", name);
        productMap.put("price", price);
        productMap.put("quantity", quantity);

        productRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                      if(task.isSuccessful())
                      {
                          loadingBar.dismiss();
                          Toast.makeText(ProductAdd.this, "Product is added successfully",Toast.LENGTH_LONG).show();

                          Intent intent = new Intent(ProductAdd.this,HomeActivity.class);
                          startActivity(intent);
                      }
                      else
                      {
                          loadingBar.dismiss();
                          String message = task.getException().toString();
                          Toast.makeText(ProductAdd.this,"Error : " + message,Toast.LENGTH_LONG).show();

                      }
                    }
                });

    }

}
