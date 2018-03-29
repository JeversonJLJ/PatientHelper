package com.unisinos.patienthelper.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.R;

import java.util.List;

/**
 * Created by jever on 26/03/2017.
 */

public class RecyclerAdapterSchedule extends RecyclerView.Adapter<RecyclerAdapterSchedule.ViewHolder> {

    private List<Alarm> list;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewPatientName;
        public TextView mTextViewTime;
        public TextView mTextViewDescription;
        public ViewGroup mRoot;
        public ViewHolder(View v) {
            super(v);
            mRoot = v.findViewById(R.id.cardViewSchedule);
            mTextViewPatientName = v.findViewById(R.id.textViewPatientName);
            mTextViewTime = v.findViewById(R.id.textViewTime);
            mTextViewDescription = v.findViewById(R.id.textViewDescription);
        }
    }
    
    
    public RecyclerAdapterSchedule(List<Alarm> list) {
        this.list = list;
    }

    @Override
    public RecyclerAdapterSchedule.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_view_schedule, parent, false);

        return new RecyclerAdapterSchedule.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterSchedule.ViewHolder holder, int position) {
        Alarm alarm = list.get(position);
       //TransitionManager.beginDelayedTransition(holder.mRoot, new AutoTransition());
        holder.mTextViewPatientName.setText(alarm.getPaciente().getNome());
        holder.mTextViewTime.setText(alarm.getHorario());
        holder.mTextViewDescription.setText(alarm.getDescricao());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

/*
    public void changeScene(RecyclerAdapterSchedule.ViewHolder holder, List<Sound> sounds) {

        View imageView = holder.imageView;
        ViewGroup.LayoutParams paramsImageView = imageView.getLayoutParams();

        holder.adapter = new RecyclerAdapterSoundsItem(sounds, activity);
        holder.recyclerViewSounds.setAdapter(holder.adapter);

        //TransitionManager.beginDelayedTransition(holder.root, new AutoTransition());
        if (!holder.expanded) {
            paramsImageView.width = (int) activity.getResources().getDimension(R.dimen.image_group_size_small);
            paramsImageView.height = (int) activity.getResources().getDimension(R.dimen.image_group_size_small);
            imageView.setLayoutParams(paramsImageView);
            setTextAppearance(activity, holder.name, android.R.style.TextAppearance_Medium);
            holder.btnExpand.animate().rotation(180).start();
            holder.soundList.setVisibility(View.VISIBLE);
            holder.expanded = true;
        } else {
            paramsImageView.width = (int) activity.getResources().getDimension(R.dimen.image_group_size_normal);
            paramsImageView.height = (int) activity.getResources().getDimension(R.dimen.image_group_size_normal);
            imageView.setLayoutParams(paramsImageView);
            setTextAppearance(activity, holder.name, android.R.style.TextAppearance_Large);
            holder.btnExpand.animate().rotation(0).start();
            holder.soundList.setVisibility(View.INVISIBLE);
            holder.expanded = false;
        }
    }

    public void setTextAppearance(Context context, TextView textView, int resId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, resId);
        } else {
            textView.setTextAppearance(resId);
        }
    }
    */
}