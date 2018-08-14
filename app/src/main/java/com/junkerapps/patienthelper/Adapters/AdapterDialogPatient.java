package com.junkerapps.patienthelper.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.junkerapps.patienthelper.Database.Paciente;
import com.junkerapps.patienthelper.R;

import java.util.List;

/**
 * Created by Jever on 08/04/2018.
 */

public class AdapterDialogPatient extends ArrayAdapter<Paciente> {
    private LayoutInflater mInflater;

    public AdapterDialogPatient(Context context, int textViewResourceId, List<Paciente> itens) {
        super(context, textViewResourceId);
        mInflater = LayoutInflater.from(context);

        for (int i = 0; i < itens.size(); i++)
            this.add(itens.get(i));

    }

    public View getView(int position, View view, ViewGroup parent) {

        Paciente item = getItem(position);

        view = mInflater.inflate(R.layout.layout_item_list_text, null);

        TextView lblText = view.findViewById(R.id.lblText);
        lblText.setText(item.getNome());


        return view;
    }
}
