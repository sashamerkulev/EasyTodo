package ru.merkulyevsasha.easytodo.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;


public class AlarmHelper {

    public static final String ALARM_ACTION = "ru.merkulyevsasha.easytodo.START_SERVICE";

    private static final int ALARM_AFTER_MINUTES = 1;

    public static void register(Context context){

        AlarmManager alarmManager= (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, TimeNotification.class);
        intent.setAction(AlarmHelper.ALARM_ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ALARM_AFTER_MINUTES);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }


}
