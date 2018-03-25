package com.unisinos.patienthelper;

import android.app.Activity;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jever on 22/03/2018.
 */

public class Util {

    public static String ConverterDateString(Date data) {
        String datetime="";
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            datetime = dateformat.format(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datetime;
    }

    public static Date ConverterStringDate(String dataString) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date data = null;

        try {
            data = new Date(format.parse(dataString).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void Mensagem(final Activity context, final String msg, final int tempo) {
        if (context != null)
            context.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context, msg, tempo).show();
                }
            });
    }
}
