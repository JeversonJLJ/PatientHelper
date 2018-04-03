package com.unisinos.patienthelper.Dialog;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TimePicker;

import com.unisinos.patienthelper.R;

import java.util.Calendar;

/**
 * Created by jever on 02/04/2018.
 */

public class Dialog {

/*
    public interface OnClickOkDialogMessage {
        void onClickOkDialogMessage();
    }

    public static void showDialogMessage(Activity activity, String message, final OnClickOkDialogMessage onDialogMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AlertDialogStyle);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(activity.getString(R.string.ok_message_dialog), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onDialogMessage.onClickOkDialogMessage();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }*/

    public interface OnDialogImputText {
        void onClickOkDialogImputText(String imputText);
    }

    public static void showDialogImputText(Activity activity, String message, final OnDialogImputText onDialogImputText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Title");

        final EditText input = new EditText(activity);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        builder.setView(input);

        builder.setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogImputText.onClickOkDialogImputText(input.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.cancel_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public interface OnDialogTimePicker {
        void onClickOkDialogTimePicker(String timeText);
    }

    public static void showDialogTimePicker(Activity activity, final OnDialogTimePicker onDialogTimePicker) {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                onDialogTimePicker.onClickOkDialogTimePicker(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle(activity.getString(R.string.select_hour_minutes_text));
        mTimePicker.show();

    }

}

