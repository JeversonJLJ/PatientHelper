package com.unisinos.patienthelper.Dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.unisinos.patienthelper.Class.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jever on 26/03/2017.
 */
public  class DialogDate {

    private OnSelectedDate onSelectedDate = null;
    private Context context = null;

    public interface OnSelectedDate {

        void  onSelectedDate(Date date, String dateText);
    }

    public DialogDate(Context context, OnSelectedDate selectedDate)
    {
        this.context = context;
        this.onSelectedDate = selectedDate;
    }

    public void show(Calendar data) {
        Calendar c = data;
        if(data == null )
            c = Calendar.getInstance();
        final int  year = c.get(Calendar.YEAR);
        final int  month = c.get(Calendar.MONTH);
        final int  day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                        onSelectedDate.onSelectedDate(Util.ConverterData(date),date);
                    }
                }, year, month, day);
        dpd.show();
    }
    public static void ShowData(Context context, DialogDate.OnSelectedDate onSelectedDate ,Calendar date) {
        DialogDate dialogDate = new DialogDate(context, onSelectedDate);
        dialogDate.show(date);
    }


}
