package com.example.khareedlayvendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khareedlayvendor.Models.Models_Products.Model_Products_Details;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class ProductsDetailsActivity extends AppCompatActivity {

    private TextView textView_productName, textView_regularPrice, textView_salePrice, textView_shortDescription, textView_description;
    private ImageView imageView;
    private MaterialButton button_update;
    private Model_Products_Details model_products_details;
    private LinearLayout linearLayout_image, linearLayout_productName, linearLayout_regularPrice, linearLayout_salePrice, linearLayout_shortDescription,
            linearLayout_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        Intent intent = getIntent();
        if (intent.getSerializableExtra("model") != null) {
            model_products_details = (Model_Products_Details) intent.getSerializableExtra("model");
            Toast.makeText(this, String.valueOf(model_products_details.getId()), Toast.LENGTH_SHORT).show();
        }

        init();
        bindDataToViews();

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toUpdateProduct = new Intent(getApplicationContext() , UpdateProduct.class);
                Long productId = model_products_details.getId();
                String imagePath = model_products_details.getArrayList_images().get(0);
                toUpdateProduct.putExtra("image_url" , imagePath);
                toUpdateProduct.putExtra("product_details" , model_products_details);
                startActivity(toUpdateProduct);
            }
        });
    }

    private void init() {
        textView_productName = findViewById(R.id.tv_productName_productDetails);
        textView_regularPrice = findViewById(R.id.tv_regularPrice_productDetails);
        textView_salePrice = findViewById(R.id.tv_salePrice_productDetails);
        textView_shortDescription = findViewById(R.id.tv_shortDescription_productDetails);
        textView_description = findViewById(R.id.tv_Description_productDetails);
        imageView = findViewById(R.id.product_image);
        button_update = findViewById(R.id.button_update_ProductsDetails);



        linearLayout_image = findViewById(R.id.linearLayout_image);
        linearLayout_productName = findViewById(R.id.linearLayout_productName);
        linearLayout_regularPrice = findViewById(R.id.linearlayout_regularPrice);
        linearLayout_salePrice = findViewById(R.id.linearLayout_salePrice);
        linearLayout_shortDescription = findViewById(R.id.linearLayout_shortDescription);
        linearLayout_description = findViewById(R.id.linearLayout_description);
    }

    public void bindDataToViews() {
        String imagePath = model_products_details.getArrayList_images().get(0);

        if (!TextUtils.isEmpty(imagePath)) {
            Picasso.get().load(imagePath).into(imageView);
        } else {
            linearLayout_image.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model_products_details.getName())){
            textView_productName.setText(model_products_details.getName());
        }
        else {
            linearLayout_productName.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model_products_details.getRegular_price())){
            textView_regularPrice.setText(model_products_details.getRegular_price());
        }
        else {
            linearLayout_regularPrice.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model_products_details.getSale_price())){
            textView_salePrice.setText(model_products_details.getSale_price());
        }
        else {
            linearLayout_salePrice.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model_products_details.getShort_description())){
            textView_shortDescription.setText(model_products_details.getShort_description());
        }
        else {
            linearLayout_shortDescription.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(model_products_details.getDescription())){
            textView_description.setText(model_products_details.getDescription());
        }
        else {
            linearLayout_description.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(ProductsDetailsActivity.this, MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
