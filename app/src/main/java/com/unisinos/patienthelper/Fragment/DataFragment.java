package com.unisinos.patienthelper.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Database.Paciente;
import com.unisinos.patienthelper.R;
import com.unisinos.patienthelper.Util;

import java.util.Random;

/**
 * Created by jever on 25/03/2018.
 */


public class DataFragment extends Fragment {

    private View mRootView;
    private EditText mEditTextName;
    private EditText mEditTextBirthDate;
    private EditText mEditTextAge;
    private EditText mEditTextComments;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_patient_data, container, false);
        mEditTextName = mRootView.findViewById(R.id.editTextName);
        mEditTextBirthDate = mRootView.findViewById(R.id.editTextBirthDate);
        mEditTextAge = mRootView.findViewById(R.id.editTextAge);
        mEditTextComments = mRootView.findViewById(R.id.editTextComments);

        return mRootView;
    }
    public void save(){
        Database mDbHelper = new Database(mRootView.getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Paciente patient = new Paciente();
        patient.setCodigo(Paciente.ObterProximoCodigo(db));
        patient.setNome(mEditTextName.getText().toString());
        patient.setDataNacimento(Util.ConverterStringDate(mEditTextBirthDate.getText().toString()));
        patient.setObservacao(mEditTextComments.getText().toString());
        Paciente.InserirSQL(db,patient);

        Alarm alarm = new Alarm();
        alarm.setCodigo(Alarm.ObterProximoCodigo(db));
        alarm.setCodPaciente(patient.getCodigo());
        alarm.setDomingo(true);
        Random r = new Random();
        int hora = r.nextInt(23 - 0) + 0;
        int min = r.nextInt(59 - 0) + 0;
        alarm.setHorario(hora + ":" + min);
        alarm.setDescricao("Alarm de teste");
        alarm.setDataUltimoAviso(Util.ConverterStringDate("24/03/2018"));
        Alarm.InserirSQL(db,alarm);

    }
}
