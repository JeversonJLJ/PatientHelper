package com.unisinos.patienthelper.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unisinos.patienthelper.R;

/**
 * Created by jever on 25/03/2018.
 */

public class AlarmsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public AlarmsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_patient_alarms, container, false);

        return rootView;
    }
}
