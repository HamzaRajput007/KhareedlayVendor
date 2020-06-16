package com.example.khareedlayvendor;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.khareedlayvendor.AppUtils.Constants;
import com.example.khareedlayvendor.Models.Model_Orders.Model_OrderDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.khareedlayvendor.NotificationClass.NOTIFICATION;

public class Alarm extends BroadcastReceiver
{
    private String vendorId;
    private SharedPreferences sharedPreferences;
    private NotificationManagerCompat notificationManagerCompat;
    public static int count = 1;
    private NotificationCompat.Builder builder = null;
    String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        sharedPreferences = context.getSharedPreferences(Constants.KEY_LOGIN_PREFRENCES,Context.MODE_PRIVATE);
        vendorId = sharedPreferences.getString(Constants.KEY_USER_ID,"");

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        notificationManagerCompat = NotificationManagerCompat.from(context);
        Call<ArrayList<Model_OrderDetails>> call = RetrofitClient_Base.getInstance().getApi().getOrders(vendorId,"ck_cec24f9d9e3882b3b6ba0f4db13127b564fec9bb",
                "cs_f18b2f622fa1ad0cbd7bfdcf730ad6ddc36d2bc0");
        call.enqueue(new Callback<ArrayList<Model_OrderDetails>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ArrayList<Model_OrderDetails>> call, Response<ArrayList<Model_OrderDetails>> response) {
                assert response.body() != null;
                int orderId = response.body().get(0).getId();
                int previousId = 0;
                if (sharedPreferences.getInt(Constants.KEY_ORDER_ID,0) != 0){
                    previousId = sharedPreferences.getInt(Constants.KEY_ORDER_ID,0);
                }
                else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Constants.KEY_ORDER_ID,orderId);
                    editor.apply();
                }

                if (orderId > previousId){
                    Toast.makeText(context, "New Order", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(Constants.KEY_ORDER_ID,orderId);
                    editor.apply();
                    generateNotification(context);
                }
                else {
                    Toast.makeText(context, "No order listed", Toast.LENGTH_SHORT).show();
//                    generateNotification(context);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Model_OrderDetails>> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        wl.release();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setAlarm(Context context)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        int time = 60000;
        assert am != null;
        am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 1000, time, pi);
        am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                SystemClock.elapsedRealtime() +
                        60 * 1000,  pi);// Millisec * Second * Minute
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void generateNotification(Context context){
        if (builder == null) {
            builder = new NotificationCompat.Builder(context, NOTIFICATION);
        }
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSmallIcon(R.drawable.ic_orders)
                    .setContentTitle("New Order received")
                    .setContentText("This is New Order")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setSound(alarmSound)
                    .setNumber(count)
                    .setGroup(GROUP_KEY_WORK_EMAIL)
                    .build();

        notificationManagerCompat.notify(1,builder.build());
        count++;
        Toast.makeText(context, ""+count, Toast.LENGTH_SHORT).show();
    }
}
