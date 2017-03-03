package ru.merkulyevsasha.easytodo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class TimeNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(Intent.ACTION_BOOT_COMPLETED)
                || action .equals("android.intent.action.QUICKBOOT_POWERON")
                || action .equals("com.htc.intent.action.QUICKBOOT_POWERON") ) {

            AlarmHelper.register(context);

        } else if (action.equals(AlarmHelper.ALARM_ACTION)){

            context.startService(new Intent(context, TasksService.class));

        }
    }
}
