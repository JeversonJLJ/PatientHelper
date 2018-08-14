package com.junkerapps.patienthelper.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.junkerapps.patienthelper.Service.ServiceAlarm;

public class BroadcastReceiverOnBootComplete extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Intent serviceIntent = new Intent(context, ServiceAlarm.class);
            context.startService(serviceIntent);
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
}