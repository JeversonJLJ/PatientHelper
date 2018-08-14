package com.junkerapps.patienthelper.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.junkerapps.patienthelper.Activity.MainActivity;
import com.junkerapps.patienthelper.Activity.PatientActivity;
import com.junkerapps.patienthelper.Database.Alarm;
import com.junkerapps.patienthelper.R;

import java.util.List;

/**
 * Created by jever on 26/03/2017.
 */

public class RecyclerAdapterSchedule extends RecyclerView.Adapter<RecyclerAdapterSchedule.ViewHolder> {

    private List<Alarm> list;
    private Activity activity;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewPatientName;
        public TextView mTextViewTime;
        public TextView mTextViewDescription;
        public ViewGroup mRoot;
        public FrameLayout mFrameLayout;

        public ViewHolder(View v) {
            super(v);
            mRoot = v.findViewById(R.id.cardViewSchedule);
            mTextViewPatientName = v.findViewById(R.id.textViewPatientName);
            mTextViewTime = v.findViewById(R.id.textViewTime);
            mTextViewDescription = v.findViewById(R.id.textViewDescription);
            mFrameLayout = v.findViewById(R.id.frameLayoutColor);
        }
    }


    public RecyclerAdapterSchedule(Activity activity, List<Alarm> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public RecyclerAdapterSchedule.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_schedule, parent, false);

        return new RecyclerAdapterSchedule.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterSchedule.ViewHolder holder, int position) {
        try {
            final Alarm alarm = list.get(position);
            //TransitionManager.beginDelayedTransition(holder.mRoot, new AutoTransition());
            holder.mTextViewPatientName.setText(alarm.getPaciente().getNome());
            holder.mTextViewTime.setText(alarm.getHorario());
            holder.mTextViewDescription.setText(alarm.getDescricao());

            holder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, PatientActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putLong(PatientActivity.COD_PATIENT, alarm.getCodPaciente());
                    bundle.putInt(PatientActivity.TAB_LOAD, PatientActivity.TAB_LOAD_1);
                    intent.putExtras(bundle);
                    activity.startActivityForResult(intent, MainActivity.REQ_LOAD_PATIENT);
                }
            });

            if (alarm.getPaciente().getCor() == "")
                holder.mFrameLayout.setBackground(new ColorDrawable(Color.parseColor("#26a69a")));
            else
                holder.mFrameLayout.setBackground(new ColorDrawable(Color.parseColor("#" + alarm.getPaciente().getCor())));
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}