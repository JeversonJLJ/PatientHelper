package com.unisinos.patienthelper.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.unisinos.patienthelper.Service.ServiceAlarm;

public class BroadcastReceiverOnBootComplete extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, ServiceAlarm.class);
        context.startService(serviceIntent);
    }
}