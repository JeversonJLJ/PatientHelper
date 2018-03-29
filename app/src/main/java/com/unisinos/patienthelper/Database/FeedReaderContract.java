package com.unisinos.patienthelper.Database;

import android.provider.BaseColumns;

/**
 * Created by Programacao on 02/01/2017.
 */

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {
    }

    public static final String TEXT_TYPE = " TEXT";
    public static final String REAL_TYPE = " REAL";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String NUMERIC_TYPE = " NUMERIC";
    public static final String NONE_TYPE = " NONE";
    public static final String COMMA_SEP = ",";

    /* Inner class that defines the table contents */
    public static class FeedPaciente implements BaseColumns {
        public static final String TABLE_NAME = "Paciente";
        public static final String COLUMN_NAME_CODIGO = "Codigo";
        public static final String COLUMN_NAME_NOME = "Nome";
        public static final String COLUMN_NAME_DATA_NACIMENTO = "Idade";
        public static final String COLUMN_NAME_OBSERVACAO = "Observacao";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedPaciente.TABLE_NAME + " (" +
                        FeedPaciente.COLUMN_NAME_CODIGO + INTEGER_TYPE + " PRIMARY KEY," +
                        FeedPaciente.COLUMN_NAME_NOME + TEXT_TYPE + COMMA_SEP +
                        FeedPaciente.COLUMN_NAME_DATA_NACIMENTO + TEXT_TYPE + COMMA_SEP +
                        FeedPaciente.COLUMN_NAME_OBSERVACAO + TEXT_TYPE + " )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedPaciente.TABLE_NAME;


    }

    public static class FeedAlarm implements BaseColumns {
        public static final String TABLE_NAME = "Alarm";
        public static final String COLUMN_NAME_CODIGO = "Codigo";
        public static final String COLUMN_NAME_CODIGO_PACIENTE = "CodPaciente";
        public static final String COLUMN_NAME_DESCRICAO = "Descricao";
        public static final String COLUMN_NAME_HORARIO = "Horario";
        public static final String COLUMN_NAME_SEGUNDA = "Segunda";
        public static final String COLUMN_NAME_TERCA = "Terca";
        public static final String COLUMN_NAME_QUARTA = "Quarta";
        public static final String COLUMN_NAME_QUINTA = "Quinta";
        public static final String COLUMN_NAME_SEXTA = "Sexta";
        public static final String COLUMN_NAME_SABADO = "Sabado";
        public static final String COLUMN_NAME_DOMINGO = "Domingo";
        public static final String COLUMN_NAME_DATA_ULTIMO_AVISO= "DataUltimoAviso";
        public static final String COLUMN_NAME_ATIVO= "Ativo";
        public static final String COLUMN_NAME_DATA_INICIO= "DataInicio";
        public static final String COLUMN_NAME_DATA_FIM= "DataFim";



        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedAlarm.TABLE_NAME + " (" +
                        FeedAlarm.COLUMN_NAME_CODIGO + INTEGER_TYPE + " PRIMARY KEY," +
                        FeedAlarm.COLUMN_NAME_CODIGO_PACIENTE + INTEGER_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_DESCRICAO + TEXT_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_HORARIO + TEXT_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_SEGUNDA + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_TERCA + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_QUARTA + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_QUINTA + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_SEXTA + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_SABADO + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_DOMINGO + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_DATA_ULTIMO_AVISO + TEXT_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_ATIVO + NUMERIC_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_DATA_INICIO + TEXT_TYPE + COMMA_SEP +
                        FeedAlarm.COLUMN_NAME_DATA_FIM + TEXT_TYPE + " )";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedAlarm.TABLE_NAME;


    }



}
