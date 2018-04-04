package com.unisinos.patienthelper.Dialog;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
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

    public static void showDialogImputText(Activity activity, String lastText, String title, final OnDialogImputText onDialogImputText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog;
        builder.setTitle(title);

        LinearLayout container = new LinearLayout(activity);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(50, 50, 50, 50);
        final EditText input = new EditText(activity);
        input.setLayoutParams(lp);
        input.setGravity(android.view.Gravity.TOP|android.view.Gravity.LEFT);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
        input.setLines(1);
        input.setMaxLines(1);
        input.setText(lastText);
        container.addView(input,lp);


        builder.setView(container);

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
        dialog = builder.create();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
       /* dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                input.post(new Runnable() {
                    @Override
                    public void run() {
                        final InputMethodManager imm = (InputMethodManager) input.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
                        input.requestFocus(); // needed if you have more then one input
                        input.selectAll();
                    }
                });
            }
        });*/
        input.requestFocus();
        input.selectAll();
        dialog.show();
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

    public static void showDialogYesNo(final Activity context, String title, String message, DialogInterface.OnClickListener clickYes, DialogInterface.OnClickListener clickNo) {

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.yes_text, clickYes);
        builder.setNegativeButton(R.string.no_text, clickNo);
        builder.show();
    }

}

