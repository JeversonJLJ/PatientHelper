package com.unisinos.patienthelper.BroadcastReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.unisinos.patienthelper.R;
import com.unisinos.patienthelper.Service.ServiceAlarm;

public class BroadcastReceiverOnBootComplete extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context,"1")
                .setSmallIcon(R.drawable.ic_action_add_alarm)
                .setContentTitle("BroadcastReceiverOnBootComplete")
                .setContentText("BroadcastReceiverOnBootComplete")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, mBuilder.build());

    // if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
    Intent serviceIntent = new Intent(context, ServiceAlarm.class);
            context.startService(serviceIntent);
    //}
}
}