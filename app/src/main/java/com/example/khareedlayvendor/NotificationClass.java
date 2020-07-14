package com.example.khareedlayvendor;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.khareedlayvendor.Adapters.OrdersList_Adapter;
import com.example.khareedlayvendor.AppUtils.Constants;
import com.example.khareedlayvendor.Models.Model_Orders.Model_ItemsDetails;
import com.example.khareedlayvendor.Models.Model_Orders.Model_OrderDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationClass extends Application {
    private  ArrayList<Model_ItemsDetails> arrayList_ItemDetails;

    private String consumer_key = "ck_cec24f9d9e3882b3b6ba0f4db13127b564fec9bb";
    private String consumer_secrete = "cs_f18b2f622fa1ad0cbd7bfdcf730ad6ddc36d2bc0";
    private ArrayList<Model_OrderDetails> arrayList_orders;
    private SharedPreferences sharedPreferences;
    private String vendorId;

    public static final String NOTIFICATION = "notification";
    NotificationChannel notificationChannel = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        arrayList_orders = new ArrayList<>();
        sharedPreferences = getSharedPreferences(Constants.KEY_LOGIN_PREFRENCES,MODE_PRIVATE);
        vendorId = sharedPreferences.getString(Constants.KEY_USER_ID,"");

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void generateNotifications() {
        if (notificationChannel == null) {
            notificationChannel = new NotificationChannel(NOTIFICATION, "order notification",
                    NotificationManager.IMPORTANCE_HIGH);
        }
        notificationChannel.setDescription("New Order placed");

        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;
        manager.createNotificationChannel(notificationChannel);
    }
}
