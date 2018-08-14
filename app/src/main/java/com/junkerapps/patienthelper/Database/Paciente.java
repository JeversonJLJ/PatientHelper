package com.junkerapps.patienthelper.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.junkerapps.patienthelper.Class.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jever on 21/03/2018.
 */

public class Paciente {

    private long codigo;

    private String nome;

    private Date dataNacimento;

    private String observacao;

    private String cor;

    public static boolean TestarCampos(SQLiteDatabase db, Paciente paciente) {
        return true;
    }

    public static boolean InserirSQL(SQLiteDatabase db, Paciente paciente) {
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_CODIGO, paciente.getCodigo());
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_NOME, paciente.getNome());
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_DATA_NACIMENTO,  Util.ConverterDateString(paciente.getDataNacimento()));
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_OBSERVACAO, paciente.getObservacao());
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_COR, paciente.getCor());


        if (db.insert(FeedReaderContract.FeedPaciente.TABLE_NAME, null, values) == -1)
            return false;
        else
            return true;
    }

    public static long ObterProximoCodigo(SQLiteDatabase db) {
        Cursor c = db.rawQuery("Select Max(" + FeedReaderContract.FeedPaciente.COLUMN_NAME_CODIGO + ") + 1 From " + FeedReaderContract.FeedPaciente.TABLE_NAME, null);
        c.moveToFirst();
        return c.getLong(0);
    }

    public static boolean ExcluirSQL(SQLiteDatabase db, Paciente paciente) {
        String selection = FeedReaderContract.FeedPaciente.COLUMN_NAME_CODIGO + " = ?";
        String[] selectionArgs = {paciente.getCodigo() + ""};

        int count = db.delete(FeedReaderContract.FeedPaciente.TABLE_NAME, selection, selectionArgs);

        if (count > 0)
            return true;
        else
            return false;
    }


    public static boolean AlterarSQL(SQLiteDatabase db, Paciente paciente) {
        if (!TestarCampos(db, paciente))
            return false;

        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_NOME, paciente.getNome());
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_DATA_NACIMENTO, Util.ConverterDateString(paciente.getDataNacimento()));
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_OBSERVACAO, paciente.getObservacao());
        values.put(FeedReaderContract.FeedPaciente.COLUMN_NAME_COR, paciente.getCor());

        String selection = FeedReaderContract.FeedPaciente.COLUMN_NAME_CODIGO + " = ?";
        String[] selectionArgs = {paciente.getCodigo() + ""};

        int count = db.update(
                FeedReaderContract.FeedPaciente.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0)
            return true;
        else
            return false;
    }


    public static Paciente ConsultarChave(SQLiteDatabase db, long codPaciente) {
        List<Paciente> lista = ConsultarSQL(db, " Where " + FeedReaderContract.FeedPaciente.COLUMN_NAME_CODIGO + " = " + codPaciente);

        if (lista.isEmpty())
            return null;

        return lista.get(0);
    }



    public static List<Paciente> ConsultarSQL(SQLiteDatabase db, String complementoSelect) {
        List<Paciente> lista = new ArrayList<Paciente>();
        String sql = " Select ";
        sql += FeedReaderContract.FeedPaciente.COLUMN_NAME_CODIGO + ",\n";
        sql += FeedReaderContract.FeedPaciente.COLUMN_NAME_NOME + ",\n";
        sql += FeedReaderContract.FeedPaciente.COLUMN_NAME_DATA_NACIMENTO + ", \n";
        sql += FeedReaderContract.FeedPaciente.COLUMN_NAME_OBSERVACAO + ", \n";
        sql += FeedReaderContract.FeedPaciente.COLUMN_NAME_COR + " \n";
        sql += " From " + FeedReaderContract.FeedPaciente.TABLE_NAME + "\n";
        sql += " " + complementoSelect;

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {
            Paciente paciente = ConverterCursor(c);
            lista.add(paciente);
        }
        c.close();
        return lista;
    }

    private static Paciente ConverterCursor(Cursor c) {
        Paciente paciente = new Paciente();

        paciente.setCodigo(c.getLong(c.getColumnIndexOrThrow(FeedReaderContract.FeedPaciente.COLUMN_NAME_CODIGO)));
        paciente.setNome(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedPaciente.COLUMN_NAME_NOME)));
        paciente.setDataNacimento(Util.ConverterStringDate(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedPaciente.COLUMN_NAME_DATA_NACIMENTO))));
        paciente.setObservacao(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedPaciente.COLUMN_NAME_OBSERVACAO)));
        paciente.setCor(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.FeedPaciente.COLUMN_NAME_COR)));

        return paciente;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNacimento() {
        return dataNacimento;
    }

    public void setDataNacimento(Date dataNacimento) {
        this.dataNacimento = dataNacimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
}
