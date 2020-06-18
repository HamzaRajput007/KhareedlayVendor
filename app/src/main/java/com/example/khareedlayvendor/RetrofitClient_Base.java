package com.example.khareedlayvendor;

import com.example.khareedlayvendor.Remote.Api_Interface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient_Base {

    private static RetrofitClient_Base mInstance;
    private static Retrofit retrofit;

    public RetrofitClient_Base(){

        retrofit = new Retrofit.Builder()
                .baseUrl("https://khareedlaay.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient_Base getInstance(){
        if (mInstance == null){
            mInstance = new RetrofitClient_Base();
        }
        return mInstance;
    }

    public Api_Interface getApi(){
        return retrofit.create(Api_Interface.class);
    }
}
