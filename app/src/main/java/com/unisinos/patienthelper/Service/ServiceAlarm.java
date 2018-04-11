package com.unisinos.patienthelper.Service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.unisinos.patienthelper.Activity.MainActivity;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServiceAlarm extends Service {

    public static boolean isServiceRunning = false;

    @Override
    public void onCreate() {
        super.onCreate();
        startServiceWithNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startServiceWithNotification();

        return START_STICKY;
    }

    // In case the service is deleted or crashes some how
    @Override
    public void onDestroy() {
        isServiceRunning = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }


    void startServiceWithNotification() {
        final Context context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Database mDbHelper = new Database(context);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                int lastID = 0;
                while (true) {
                    try {
                        List<Alarm> alarms = Alarm.ConsultarSQL(db, Calendar.getInstance().getTime());

                        for (Alarm alarm : alarms) {
                            Calendar calendarNow = Calendar.getInstance();
                            Calendar calendarAlarm = Calendar.getInstance();
                            boolean notifiedToday = false;

                            if (alarm.getDataUltimoAviso() == null) {

                                calendarAlarm.setTime(alarm.getDataIncio());
                                calendarAlarm.set(calendarAlarm.get(Calendar.YEAR),
                                        calendarAlarm.get(Calendar.MONTH),
                                        calendarAlarm.get(Calendar.DATE),
                                        Integer.parseInt(alarm.getHorario().split(":")[0]),
                                        Integer.parseInt(alarm.getHorario().split(":")[1]));


                            } else {
                                calendarAlarm.setTime(alarm.getDataUltimoAviso());
                                calendarAlarm.set(calendarAlarm.get(Calendar.YEAR),
                                        calendarAlarm.get(Calendar.MONTH),
                                        calendarAlarm.get(Calendar.DATE),
                                        Integer.parseInt(alarm.getHorario().split(":")[0]),
                                        Integer.parseInt(alarm.getHorario().split(":")[1]));
                                if (calendarAlarm.get(Calendar.YEAR) == calendarNow.get(Calendar.YEAR) &&
                                        calendarAlarm.get(Calendar.MONTH) == calendarNow.get(Calendar.MONTH) &&
                                        calendarAlarm.get(Calendar.DATE) == calendarNow.get(Calendar.DATE))
                                    notifiedToday = true;
                            }

                            if (calendarAlarm.get(Calendar.HOUR_OF_DAY) == calendarNow.get(Calendar.HOUR_OF_DAY) &&
                                    Math.abs(calendarAlarm.get(Calendar.MINUTE) - calendarNow.get(Calendar.MINUTE))<=5) {
                                if (!notifiedToday) {
                                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, String.valueOf(lastID))
                                            .setSmallIcon(R.drawable.ic_medication_time)
                                            .setColor(getResources().getColor(R.color.colorAccent))
                                            .setContentTitle(alarm.getPaciente().getNome())
                                            .setContentText(alarm.getDescricao())
                                            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                                    Intent intent = new Intent(context, MainActivity.class);//CUSTOM ACTIVITY HERE
                                    PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    mBuilder.setContentIntent(contentIntent);
                                    notificationManager.notify(lastID, mBuilder.build());
                                    lastID++;
                                    alarm.setDataUltimoAviso(Calendar.getInstance().getTime());
                                    Alarm.AlterarSQL(db, alarm);
                                }
                            }

                        }


                        Thread.sleep(5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    void stopMyService() {
        stopForeground(true);
        stopSelf();
        isServiceRunning = false;
    }
}