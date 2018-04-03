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
import com.unisinos.patienthelper.Class.Util;
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Dialog.Dialog;
import com.unisinos.patienthelper.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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

    public void newAlarm(){
        Dialog.showDialogTimePicker(getActivity(), new Dialog.OnDialogTimePicker() {
            @Override
            public void onClickOkDialogTimePicker(String timeText) {
                Database mDbHelper = new Database(mRootView.getContext());
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                final Alarm alarm = new Alarm();
                alarm.setCodigo(Alarm.ObterProximoCodigo(db));
                alarm.setCodPaciente(codPatient);
                alarm.setAtivo(true);
                alarm.setDomingo(true);
                alarm.setSegunda(true);
                alarm.setTerca(true);
                alarm.setQuarta(true);
                alarm.setQuinta(true);
                alarm.setSexta(true);
                alarm.setSabado(true);
                alarm.setHorario(timeText);
                alarm.setDescricao("");
                alarm.setDataUltimoAviso(null);
                alarm.setDataIncio(Calendar.getInstance().getTime());
                alarm.setDataFim(null);
                Alarm.InserirSQL(db, alarm);

                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mAlarmList.add(0,alarm);
                        mAdapter.notifyItemChanged(0);
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                });


            }
        });
    }
    private void loadData() {
        Database mDbHelper = new Database(mRootView.getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mAlarmList = Alarm.ConsultarSQL(db, codPatient);
        mAdapter = new RecyclerAdapterAlarm(getActivity(), mAlarmList);
        mRecyclerView.setAdapter(mAdapter);


    }
}
