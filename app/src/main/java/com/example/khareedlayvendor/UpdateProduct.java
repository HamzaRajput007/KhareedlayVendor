package com.example.khareedlayvendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khareedlayvendor.Models.Models_Products.Model_Products_Details;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.io.DataOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProduct extends AppCompatActivity {

    Long productId ;
    String imagePath;
    ImageView imageViewProduct;
    MaterialButton updateBtn;
    private EditText textView_productName, textView_regularPrice, textView_salePrice, textView_shortDescription, textView_description;
    Model_Products_Details model_products_details = new Model_Products_Details();
    Model_Products_Details mProductUpdated = new Model_Products_Details();
    ArrayList<Model_Products_Details> responseArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        init();
//        bindviews();



        model_products_details = (Model_Products_Details) getIntent().getSerializableExtra("product_details");
        productId = model_products_details.getId();
        imagePath = model_products_details.getArrayList_images().get(0);

        textView_productName.setText(model_products_details.getName());
        textView_regularPrice.setText(model_products_details.getRegular_price());
        textView_salePrice.setText(model_products_details.getSale_price());
        textView_shortDescription.setText(model_products_details.getShort_description());
        textView_description.setText(model_products_details.getDescription());

        if(productId != 0){
            Toast.makeText(this, model_products_details.getName(), Toast.LENGTH_SHORT).show();


        }

        if(imagePath != null){
            Picasso.get().load(imagePath).into(imageViewProduct);
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProductUpdated.setSale_price(textView_salePrice.getText().toString().trim());
                mProductUpdated.setRegular_price(textView_regularPrice.getText().toString().trim());
                mProductUpdated.setName(textView_productName.getText().toString().trim());
                mProductUpdated.setShort_description(textView_shortDescription.getText().toString().trim());
                mProductUpdated.setDescription(textView_description.getText().toString().trim());

                Log.d("getName" , mProductUpdated.getName());
                Log.d("getRegular_price" , mProductUpdated.getRegular_price());
                Log.d("getSale_price" ,mProductUpdated.getSale_price());
                Log.d("getShort_description" ,mProductUpdated.getShort_description());
                Log.d("getDescription" ,mProductUpdated.getDescription());

                Call<ArrayList<Model_Products_Details>> call =  RetrofitClient_Base.getInstance().getApi().updateProduct(productId, mProductUpdated);
                call.enqueue(new Callback<ArrayList<Model_Products_Details>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Model_Products_Details>> call, Response<ArrayList<Model_Products_Details>> response) {
                        if(response != null) {

                            responseArrayList = response.body();
                            Log.d("update_response" , String.valueOf(responseArrayList));
                            Toast.makeText(UpdateProduct.this, "Product Updated Successfully ", Toast.LENGTH_SHORT).show();
                            Intent toMain  = new Intent(getApplicationContext() , MainActivity.class);
                            startActivity(toMain);
                            finish();
                        }
                        else {
                            Toast.makeText(UpdateProduct.this, "Null Response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Model_Products_Details>> call, Throwable t) {
                        Toast.makeText(UpdateProduct.this, "Failed To Upddate : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    //Todo Update the product on the server
    private void updateProduct() {

    }


//    private void bindviews() {
//
//
//    }

    private void init() {
        textView_productName = findViewById(R.id.et_productName_productDetails);
        textView_regularPrice = findViewById(R.id.et_regularPrice_productDetails);
        textView_salePrice = findViewById(R.id.et_salePrice_productDetails);
        textView_shortDescription = findViewById(R.id.et_shortDescription_productDetails);
        textView_description = findViewById(R.id.et_Description_productDetails);
        imageViewProduct = findViewById(R.id.product_update_image);
        updateBtn = findViewById(R.id.button_update_ProductId);
    }
}
