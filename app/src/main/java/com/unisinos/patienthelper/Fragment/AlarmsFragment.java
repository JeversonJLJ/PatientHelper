package com.unisinos.patienthelper.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unisinos.patienthelper.Activity.MainActivity;
import com.unisinos.patienthelper.Adapters.RecyclerAdapterAlarm;
import com.unisinos.patienthelper.Adapters.RecyclerAdapterSchedule;
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jever on 25/03/2018.
 */

public class AlarmsFragment extends Fragment {

    private List<Alarm> mAlarmList;
    public long codPatient;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public AlarmsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_patient_alarms, container, false);
        mAlarmList = new ArrayList<Alarm>();

        mRecyclerView = mRootView.findViewById(R.id.recycler_view_alarm);
        mLayoutManager = new LinearLayoutManager(mRootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapterAlarm(getActivity(), mAlarmList);
        mRecyclerView.setAdapter(mAdapter);

        loadData();
        return mRootView;

    }

    private void loadData() {
        Database mDbHelper = new Database(mRootView.getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mAlarmList = Alarm.ConsultarSQL(db, codPatient);
        mAdapter = new RecyclerAdapterAlarm(getActivity(), mAlarmList);
        mRecyclerView.setAdapter(mAdapter);


    }
}
