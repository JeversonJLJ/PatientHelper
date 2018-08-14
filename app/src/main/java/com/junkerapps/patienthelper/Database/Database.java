package com.junkerapps.patienthelper.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jever on 21/03/2018.
 */

public class Database extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Acompanhamentodepacientes.db";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(FeedReaderContract.FeedPaciente.SQL_CREATE_ENTRIES);
            db.execSQL(FeedReaderContract.FeedAlarm.SQL_CREATE_ENTRIES);

        } catch (Exception e) {
            Log.e("Criando Banco", e.getMessage());
        }

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        try {
            db.execSQL(FeedReaderContract.FeedPaciente.SQL_DELETE_ENTRIES);
            db.execSQL(FeedReaderContract.FeedAlarm.SQL_DELETE_ENTRIES);

            onCreate(db);
        } catch (Exception e) {
            Log.e("Criando Banco", e.getMessage());
        }
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void recriarBanco(SQLiteDatabase db){
        try {
            //Drop
            db.execSQL(FeedReaderContract.FeedPaciente.SQL_DELETE_ENTRIES);
            db.execSQL(FeedReaderContract.FeedAlarm.SQL_DELETE_ENTRIES);

            //Create
            db.execSQL(FeedReaderContract.FeedPaciente.SQL_CREATE_ENTRIES);
            db.execSQL(FeedReaderContract.FeedAlarm.SQL_CREATE_ENTRIES);
        } catch (Exception e) {
            Log.e("Recriando Banco", e.getMessage());
        }
    }
}
