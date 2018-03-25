package com.unisinos.patienthelper.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unisinos.patienthelper.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by jever on 21/03/2018.
 */

public class Alarm {

    private long codigo;

    private long codPaciente;

    private String descricao;

    private String horario;

    private boolean diariamente;

    private boolean segunda;

    private boolean terca;

    private boolean quarta;

    private boolean quinta;

    private boolean sexta;

    private boolean sabado;

    private boolean domingo;

    private boolean avisouHoje;

    private Date dataUltimoAviso;

    private Paciente paciente;


    public static boolean TestarCampos(SQLiteDatabase db, Alarm alarm) {
        return true;
    }

    public static boolean InserirSQL(SQLiteDatabase db, Alarm alarm) {
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO, alarm.getCodigo());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO_PACIENTE, alarm.getCodPaciente());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DESCRICAO, alarm.getDescricao());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_HORARIO, alarm.getHorario());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DIARIAMENTE, alarm.isDiariamente());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA, alarm.isSegunda());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA, alarm.isTerca());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA, alarm.isQuarta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA, alarm.isQuinta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA, alarm.isSexta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO, alarm.isSabado());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO, alarm.isDomingo());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_AVISOU_HOJE, alarm.isAvisouHoje());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO, Util.ConverterDateString(alarm.getDataUltimoAviso()));


        if (db.insert(FeedReaderContract.FeedAlarm.TABLE_NAME, null, values) == -1)
            return false;
        else
            return true;
    }

    public static long ObterProximoCodigo(SQLiteDatabase db) {
        Cursor c = db.rawQuery("Select Max(" + FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO + ") + 1 From " + FeedReaderContract.FeedAlarm.TABLE_NAME, null);
        c.moveToFirst();
        return c.getLong(0);
    }

    public static boolean ExcluirSQL(SQLiteDatabase db, Alarm alarm) {
        String selection = FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO + " = ?";
        String[] selectionArgs = {alarm.getCodigo() + ""};

        int count = db.delete(FeedReaderContract.FeedAlarm.TABLE_NAME, selection, selectionArgs);

        if (count > 0)
            return true;
        else
            return false;
    }


    public static boolean AlterarSQL(SQLiteDatabase db, Alarm alarm) {
        if (!TestarCampos(db, alarm))
            return false;

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO_PACIENTE, alarm.getCodPaciente());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DESCRICAO, alarm.getDescricao());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_HORARIO, alarm.getHorario());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DIARIAMENTE, alarm.isDiariamente());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA, alarm.isSegunda());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA, alarm.isTerca());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA, alarm.isQuarta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA, alarm.isQuinta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA, alarm.isSexta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO, alarm.isSabado());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO, alarm.isDomingo());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_AVISOU_HOJE, alarm.isAvisouHoje());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO, Util.ConverterDateString(alarm.getDataUltimoAviso()));

        String selection = FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO + " = ?";
        String[] selectionArgs = {alarm.getCodigo() + ""};

        int count = db.update(
                FeedReaderContract.FeedAlarm.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0)
            return true;
        else
            return false;
    }


    public static Alarm ConsultarChave(SQLiteDatabase db, long codAlarm) {
        List<Alarm> lista = ConsultarSQL(db, " Where " + FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO + " = " + codAlarm);

        if (lista.isEmpty())
            return null;

        return lista.get(0);
    }


    public static List<Alarm> ConsultarSQL(SQLiteDatabase db, String complementoSelect) {
        List<Alarm> lista = new ArrayList<Alarm>();
        String sql = " Select ";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO + ",\n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO_PACIENTE + ",\n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DESCRICAO + ",\n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_HORARIO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DIARIAMENTE + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_AVISOU_HOJE + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO + " \n";
        sql += " From " + FeedReaderContract.FeedAlarm.TABLE_NAME + "\n";
        sql += " " + complementoSelect;

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            Alarm alarm = ConverterCursor(c);
            lista.add(alarm);
        }
        c.close();
        return lista;
    }


    public static List<Alarm> ConsultarSQL(SQLiteDatabase db, Date date) {
        List<Alarm> lista = new ArrayList<Alarm>();
        String where = " Where (" ;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day){
            case Calendar.SUNDAY:
                where += FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO + " = 1";
                break;
            case Calendar.MONDAY:
                where += FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA + " = 1";
                break;
            case Calendar.TUESDAY:
                where += FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA + " = 1";
                break;
            case Calendar.WEDNESDAY:
                where += FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA + " = 1";
                break;
            case Calendar.THURSDAY:
                where += FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA + " = 1";
                break;
            case Calendar.FRIDAY:
                where += FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA + " = 1";
                break;
            case Calendar.SATURDAY:
                where += FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO + " = 1";
                break;
        }
        where += " Or " + FeedReaderContract.FeedAlarm.COLUMN_NAME_DIARIAMENTE + " = 1)";


        lista = ConsultarSQL(db, where);

        for (Alarm item : lista) {
            item.paciente = Paciente.ConsultarChave(db, item.codPaciente);
        }

        return lista;
    }


    private static Alarm ConverterCursor(Cursor c) {
        Alarm alarm = new Alarm();

        alarm.setCodigo(c.getLong(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO)));
        alarm.setCodPaciente(c.getLong(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO_PACIENTE)));
        alarm.setDescricao(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DESCRICAO)));
        alarm.setHorario(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_HORARIO)));
        alarm.setDiariamente(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DIARIAMENTE))));
        alarm.setSegunda(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA))));
        alarm.setTerca(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA))));
        alarm.setQuarta(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA))));
        alarm.setQuinta(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA))));
        alarm.setSexta(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA))));
        alarm.setSabado(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO))));
        alarm.setDomingo(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO))));
        alarm.setAvisouHoje(Boolean.parseBoolean(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_AVISOU_HOJE))));
        alarm.setDataUltimoAviso(Util.ConverterStringDate(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO))));


        return alarm;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public long getCodPaciente() {
        return codPaciente;
    }

    public void setCodPaciente(long codPaciente) {
        this.codPaciente = codPaciente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public boolean isDiariamente() {
        return diariamente;
    }

    public void setDiariamente(boolean diariamente) {
        this.diariamente = diariamente;
    }

    public boolean isSegunda() {
        return segunda;
    }

    public void setSegunda(boolean segunda) {
        this.segunda = segunda;
    }

    public boolean isTerca() {
        return terca;
    }

    public void setTerca(boolean terca) {
        this.terca = terca;
    }

    public boolean isQuarta() {
        return quarta;
    }

    public void setQuarta(boolean quarta) {
        this.quarta = quarta;
    }

    public boolean isQuinta() {
        return quinta;
    }

    public void setQuinta(boolean quinta) {
        this.quinta = quinta;
    }

    public boolean isSexta() {
        return sexta;
    }

    public void setSexta(boolean sexta) {
        this.sexta = sexta;
    }

    public boolean isSabado() {
        return sabado;
    }

    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
    }

    public boolean isAvisouHoje() {
        return avisouHoje;
    }

    public void setAvisouHoje(boolean avisouHoje) {
        this.avisouHoje = avisouHoje;
    }

    public Date getDataUltimoAviso() {
        return dataUltimoAviso;
    }

    public void setDataUltimoAviso(Date dataUltimoAviso) {
        this.dataUltimoAviso = dataUltimoAviso;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
}
