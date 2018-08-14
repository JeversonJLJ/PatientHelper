package com.junkerapps.patienthelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.junkerapps.patienthelper.Database.Alarm;
import com.junkerapps.patienthelper.R;

import java.util.List;

/**
 * Created by Programacao on 19/01/2017.
 */

public class AdapterSchedule extends ArrayAdapter<Alarm> {
    private LayoutInflater mInflater;

    public AdapterSchedule(Context context, int textViewResourceId, List<Alarm> itens) {
        super(context, textViewResourceId);
        mInflater = LayoutInflater.from(context);

        for (int i = 0; i < itens.size(); i++)
            this.add(itens.get(i));

    }

    public View getView(int position, View view, ViewGroup parent) {
        //if(view != null)
        //    return view;

        Alarm item = getItem(position);

        view = mInflater.inflate(R.layout.layout_view_schedule, null);

        TextView textViewPatientName = (TextView) view.findViewById(R.id.textViewPatientName);
        TextView textViewTime = (TextView) view.findViewById(R.id.textViewTime);
        TextView textViewDescription = (TextView) view.findViewById(R.id.textViewDescription);

        textViewPatientName.setText(item.getPaciente().getNome());
        textViewTime.setText(item.getHorario());
        textViewDescription.setText(item.getDescricao());



        return view;
    }
}
