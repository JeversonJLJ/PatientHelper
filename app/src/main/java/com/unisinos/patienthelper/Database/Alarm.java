package com.unisinos.patienthelper.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.unisinos.patienthelper.Class.Util;

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

    private boolean segunda;

    private boolean terca;

    private boolean quarta;

    private boolean quinta;

    private boolean sexta;

    private boolean sabado;

    private boolean domingo;

    private Date dataUltimoAviso;

    private boolean ativo;

    private Date dataIncio;

    private Date dataFim;

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
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA, alarm.getSegunda());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA, alarm.getTerca());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA, alarm.getQuarta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA, alarm.getQuinta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA, alarm.getSexta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO, alarm.getSabado());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO, alarm.getDomingo());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO, Util.ConverterDateString(alarm.getDataUltimoAviso()));
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_ATIVO, alarm.getAtivo());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_INICIO, Util.ConverterDateString(alarm.getDataIncio()));
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_FIM, Util.ConverterDateString(alarm.getDataFim()));


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
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA, alarm.getSegunda());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA, alarm.getTerca());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA, alarm.getQuarta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA, alarm.getQuinta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA, alarm.getSexta());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO, alarm.getSabado());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO, alarm.getDomingo());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_ATIVO, alarm.getAtivo());
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO, Util.ConverterDateString(alarm.getDataUltimoAviso()));
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_INICIO, Util.ConverterDateString(alarm.getDataIncio()));
        values.put(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_FIM, Util.ConverterDateString(alarm.getDataFim()));

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
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_ATIVO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_INICIO + ", \n";
        sql += FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_FIM + " \n";
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

    public static List<Alarm> ConsultarSQL(SQLiteDatabase db, long codPaciente) {
        List<Alarm> lista;
        String where = " Where " + FeedReaderContract.FeedAlarm.COLUMN_NAME_CODIGO_PACIENTE + " = " + codPaciente;
        lista = ConsultarSQL(db, where);
        return lista;
    }

    public static List<Alarm> ConsultarSQL(SQLiteDatabase db, Date date) {
        List<Alarm> lista = new ArrayList<Alarm>();
        String where = " Where ";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        where += " And '" + format.format(date) + "' >= date(" + FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_INICIO + ")";
        where += " And '" + format.format(date) + "' <= date(" + FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_FIM + ")";
        where += " And " + FeedReaderContract.FeedAlarm.COLUMN_NAME_ATIVO + " = 1";
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

        alarm.setSegunda(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEGUNDA))==1?true:false);
        alarm.setTerca(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_TERCA))==1?true:false);
        alarm.setQuarta(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUARTA))==1?true:false);
        alarm.setQuinta(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_QUINTA))==1?true:false);
        alarm.setSexta(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_SEXTA))==1?true:false);
        alarm.setSabado(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_SABADO))==1?true:false);
        alarm.setDomingo(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DOMINGO))==1?true:false);
        alarm.setDataUltimoAviso(Util.ConverterStringDate(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO))));
        alarm.setAtivo(c.getInt(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_ATIVO))==1?true:false);
        alarm.setDataIncio(Util.ConverterStringDate(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_INICIO))));
        alarm.setDataFim(Util.ConverterStringDate(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedAlarm.COLUMN_NAME_DATA_FIM))));


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

    public boolean isSegunda() {
        return segunda;
    }

    public int getSegunda() {
        return segunda ? 1 : 0;
    }

    public void setSegunda(boolean segunda) {
        this.segunda = segunda;
    }

    public boolean isTerca() {
        return terca;
    }

    public int getTerca() {
        return terca ? 1 : 0;
    }

    public void setTerca(boolean terca) {
        this.terca = terca;
    }

    public boolean isQuarta() {
        return quarta;
    }

    public int getQuarta() {
        return quarta ? 1 : 0;
    }


    public void setQuarta(boolean quarta) {
        this.quarta = quarta;
    }

    public boolean isQuinta() {
        return quinta;
    }

    public int getQuinta() {
        return quinta ? 1 : 0;
    }


    public void setQuinta(boolean quinta) {
        this.quinta = quinta;
    }

    public boolean isSexta() {
        return sexta;
    }

    public int getSexta() {
        return sexta?1:0;
    }


    public void setSexta(boolean sexta) {
        this.sexta = sexta;
    }

    public boolean isSabado() {
        return sabado;
    }

    public int getSabado() {
        return sabado?1:0;
    }


    public void setSabado(boolean sabado) {
        this.sabado = sabado;
    }

    public boolean isDomingo() {
        return domingo;
    }

    public int getDomingo() {
        return domingo?1:0;
    }


    public void setDomingo(boolean domingo) {
        this.domingo = domingo;
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

    public Date getDataIncio() {
        return dataIncio;
    }

    public void setDataIncio(Date dataIncio) {
        this.dataIncio = dataIncio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int getAtivo() {
        return ativo?1:0;
    }


    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
