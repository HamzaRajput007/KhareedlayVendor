package com.example.khareedlayvendor;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.JobIntentService;

public class SimpleJobIntentService extends JobIntentService {

    static final int JOB_ID = 1000;
    private Alarm alarm = new Alarm();

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, SimpleJobIntentService.class, JOB_ID, work);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent.getAction().equals("start")) {
            alarm.setAlarm(this);
        }
    }
}
