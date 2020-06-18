package com.example.khareedlayvendor.Remote;


import android.util.Log;

import com.example.khareedlayvendor.Models.Model_Orders.Model_OrderDetails;
import com.example.khareedlayvendor.Models.Models_Login.Model_Login_Call;
import com.example.khareedlayvendor.Models.Models_Products.Model_Products_Details;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api_Interface {

    @GET("wp-json/aw/login")
    Call<Model_Login_Call> getLoginInfo(@Query("username") String username,
                                        @Query("pass") String pass);


    @GET("wp-json/aw/products")
    Call<ArrayList<Model_Products_Details>> getProductsList(@Query("vendor_id") String vendorId,
                                                            @Query("page") String pageNum);


    @GET("wp-json/aw/orders")
    Call<ArrayList<Model_OrderDetails>> getOrders(@Query("vendor_id") String vendorId,
                                                  @Query("consumer_key") String consumerKey,
                                                  @Query("consumer_secret") String consumerSecrete);

    @Headers({"Content-Type: application/json"})
    @PUT("wp-json/wc/v3/products/{id}")
    Call<ArrayList<Model_Products_Details>> updateProduct(@Path("id") Long id , @Body Model_Products_Details model_products_details);
}
