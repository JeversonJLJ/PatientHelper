package com.unisinos.patienthelper.Adapters;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.transition.AutoTransition;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by jever on 26/03/2017.
 */

public class RecyclerAdapterAlarm extends RecyclerView.Adapter<RecyclerAdapterAlarm.ViewHolder> {

    private List<Alarm> list;
    private Activity activity;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public long mCodPatient;
        public long mCodAlarm;
        public TextView mTextViewTime;
        public TextView mTextViewDescription;
        public TextInputEditText mTextInputEditTextDescription;
        public TextInputLayout mTextInputLayoutDescription;
        public Switch mSwitchActive;
        public ConstraintLayout mConstraintLayoutDaysOfTheWeek;
        public ToggleButton mToggleButtonSunday;
        public ToggleButton mToggleButtonMonday;
        public ToggleButton mToggleButtonTuesday;
        public ToggleButton mToggleButtonWednesday;
        public ToggleButton mToggleButtonThursday;
        public ToggleButton mToggleButtonFriday;
        public ToggleButton mToggleButtonSaturday;
        public ImageButton mImageButtonDelete;
        public TextView mTextViewDelete;
        public ImageButton mImageButtonShowHide;
        public ProgressBar mProgressBar;


        public ViewGroup mRoot;

        public ViewHolder(View v) {
            super(v);
            mRoot = v.findViewById(R.id.cardViewAlarm);
            mTextViewTime = v.findViewById(R.id.textViewTime);
            mTextViewDescription = v.findViewById(R.id.textViewDescription);
            mTextInputEditTextDescription = v.findViewById(R.id.textInputEditTextDescription);
            mTextInputLayoutDescription = v.findViewById(R.id.textInputLayoutDescription);
            mSwitchActive = v.findViewById(R.id.switchActive);
            mConstraintLayoutDaysOfTheWeek = v.findViewById(R.id.constraintLayoutDaysOfTheWeek);
            mToggleButtonSunday = v.findViewById(R.id.toggleButtonSunday);
            mToggleButtonMonday = v.findViewById(R.id.toggleButtonMonday);
            mToggleButtonTuesday = v.findViewById(R.id.toggleButtonTuesday);
            mToggleButtonWednesday = v.findViewById(R.id.toggleButtonWednesday);
            mToggleButtonThursday = v.findViewById(R.id.toggleButtonThursday);
            mToggleButtonFriday = v.findViewById(R.id.toggleButtonFriday);
            mToggleButtonSaturday = v.findViewById(R.id.toggleButtonSaturday);
            mImageButtonDelete = v.findViewById(R.id.imageButtonDelete);
            mTextViewDelete = v.findViewById(R.id.textViewDelete);
            mImageButtonShowHide = v.findViewById(R.id.imageButtonShowHide);
            mProgressBar = v.findViewById(R.id.progressBar);

        }
    }


    public RecyclerAdapterAlarm(Activity activity, List<Alarm> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public RecyclerAdapterAlarm.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_alarm, parent, false);

        return new RecyclerAdapterAlarm.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterAlarm.ViewHolder holder, int position) {
        Alarm alarm = list.get(position);
        //TransitionManager.beginDelayedTransition(holder.mRoot, new AutoTransition());
        holder.mProgressBar.setVisibility(View.GONE);

        holder.mCodAlarm = alarm.getCodigo();
        holder.mCodPatient = alarm.getCodPaciente();
        holder.mTextViewTime.setText(alarm.getHorario());
        holder.mTextInputEditTextDescription.setText(alarm.getDescricao());
        holder.mTextViewDescription.setText(alarm.getDescricao());
        holder.mSwitchActive.setChecked(alarm.isAtivo());
        holder.mToggleButtonSunday.setChecked(alarm.isDomingo());
        holder.mToggleButtonMonday.setChecked(alarm.isSegunda());
        holder.mToggleButtonTuesday.setChecked(alarm.isTerca());
        holder.mToggleButtonWednesday.setChecked(alarm.isQuarta());
        holder.mToggleButtonThursday.setChecked(alarm.isQuinta());
        holder.mToggleButtonFriday.setChecked(alarm.isSexta());
        holder.mToggleButtonSaturday.setChecked(alarm.isSabado());

        holder.mImageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.mTextViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        holder.mTextViewTime.setText(String.format("%02d",selectedHour) + ":" + String.format("%02d",selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle(activity.getString(R.string.select_hour_minutes_text));
                mTimePicker.show();

            }
        });

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mTextViewDelete.getVisibility() == View.GONE)
                    changeLayout(holder, true);
                else
                    changeLayout(holder, false);
            }
        });

        holder.mImageButtonShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.mTextViewDelete.getVisibility() == View.GONE)
                    changeLayout(holder, true);
                else
                    changeLayout(holder, false);
            }
        });
        changeLayout(holder, false);
    }

    private void changeLayout(RecyclerAdapterAlarm.ViewHolder holder, boolean expanded) {
        TransitionManager.beginDelayedTransition(holder.mRoot, new AutoTransition());
        if (expanded) {
            holder.mTextViewTime.setVisibility(View.VISIBLE);
            holder.mSwitchActive.setVisibility(View.VISIBLE);
            holder.mTextViewDescription.setVisibility(View.GONE);
            holder.mTextInputLayoutDescription.setVisibility(View.VISIBLE);
            holder.mConstraintLayoutDaysOfTheWeek.setVisibility(View.VISIBLE);
            holder.mImageButtonDelete.setVisibility(View.VISIBLE);
            holder.mTextViewDelete.setVisibility(View.VISIBLE);
            holder.mImageButtonShowHide.animate().rotation(180).start();
            holder.mProgressBar.setVisibility(View.GONE);
        } else {
            holder.mTextViewTime.setVisibility(View.VISIBLE);
            holder.mSwitchActive.setVisibility(View.VISIBLE);
            holder.mTextViewDescription.setVisibility(View.VISIBLE);
            holder.mTextViewDescription.setText(holder.mTextInputEditTextDescription.getText().toString());
            holder.mTextInputLayoutDescription.setVisibility(View.GONE);
            holder.mConstraintLayoutDaysOfTheWeek.setVisibility(View.GONE);
            holder.mImageButtonDelete.setVisibility(View.GONE);
            holder.mTextViewDelete.setVisibility(View.GONE);
            holder.mImageButtonShowHide.animate().rotation(0).start();
            holder.mProgressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}