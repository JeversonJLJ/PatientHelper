package com.unisinos.patienthelper.Fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.unisinos.patienthelper.Adapters.RecyclerAdapterSchedule;
import com.unisinos.patienthelper.Database.Alarm;
import com.unisinos.patienthelper.Database.Database;
import com.unisinos.patienthelper.Database.Paciente;
import com.unisinos.patienthelper.Dialog.DialogDate;
import com.unisinos.patienthelper.R;
import com.unisinos.patienthelper.Class.Util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by jever on 25/03/2018.
 */


public class DataFragment extends Fragment {

    private View mRootView;
    public long codPatient;
    private Paciente mPatient;
    private TextInputEditText mEditTextName;
    private EditText mEditTextBirthDate;
    private EditText mEditTextAge;
    private EditText mEditTextComments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_patient_data, container, false);
        mEditTextName = mRootView.findViewById(R.id.textInputEditTextName);
        mEditTextBirthDate = mRootView.findViewById(R.id.editTextBirthDate);
        mEditTextAge = mRootView.findViewById(R.id.editTextAge);
        mEditTextComments = mRootView.findViewById(R.id.editTextComments);

        mEditTextBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDate.ShowData(getActivity(), new DialogDate.OnSelectedDate() {
                    @Override
                    public void onSelectedDate(Date date, String dateText) {
                        mEditTextBirthDate.setText(dateText);
                    }
                }, Util.ConverterCalendario(mEditTextBirthDate.getText().toString()));
            }
        });

        loadData();
        return mRootView;
    }

    private void loadData(){
        Database mDbHelper = new Database(mRootView.getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        mPatient = Paciente.ConsultarChave(db,codPatient);
        if(mPatient!=null) {
            mEditTextName.setText(mPatient.getNome());
            mEditTextBirthDate.setText(Util.ConverterData(mPatient.getDataNacimento()));
            mEditTextComments.setText(mPatient.getObservacao());
        }

    }
    public void save() {
        Database mDbHelper = new Database(mRootView.getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Paciente patient = new Paciente();
        patient.setCodigo(Paciente.ObterProximoCodigo(db));
        patient.setNome(mEditTextName.getText().toString());
        patient.setDataNacimento(Util.ConverterStringDate(mEditTextBirthDate.getText().toString()));
        patient.setObservacao(mEditTextComments.getText().toString());
        Paciente.InserirSQL(db, patient);

        //TODO esta gerando alarm para teste remover quando fizer tela de cadastro de alarms
        Alarm alarm = new Alarm();
        alarm.setCodigo(Alarm.ObterProximoCodigo(db));
        alarm.setCodPaciente(patient.getCodigo());
        alarm.setAtivo(true);
        alarm.setDomingo(true);
        alarm.setSegunda(true);
        alarm.setTerca(true);
        alarm.setQuarta(true);
        alarm.setQuinta(true);
        alarm.setSexta(true);
        alarm.setSabado(true);
        Random r = new Random();
        int hora = r.nextInt(23 - 0) + 0;
        int min = r.nextInt(59 - 0) + 0;
        alarm.setHorario(hora + ":" + min);
        alarm.setDescricao("Alarm de teste");
        alarm.setDataUltimoAviso(Util.ConverterStringDate("24/03/2018"));
        alarm.setDataIncio(Calendar.getInstance().getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);
        alarm.setDataFim(calendar.getTime());
        Alarm.InserirSQL(db, alarm);

    }
}
