package com.example.khareedlayvendor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class AutoStart extends BroadcastReceiver
{
    Alarm alarm = new Alarm();
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {
            String vendorId = intent.getStringExtra("vendorId");
//            Alarm alarm = new Alarm(vendorId);
            alarm.setAlarm(context);
        }
    }
}
