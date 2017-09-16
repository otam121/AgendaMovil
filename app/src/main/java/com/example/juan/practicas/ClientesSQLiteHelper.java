package com.example.juan.practicas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JuanSalinas on 13/09/2017.
 */

public class ClientesSQLiteHelper extends SQLiteOpenHelper {

    //SENTENCIA PARA CREAR LA TABLA EN LA BASE DE DATOS
    String sqlpin ="CREATE TABLE pinuser (codigo INTEGER, pin INTEGER)";

    public ClientesSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlpin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS pinuser");
        db.execSQL(sqlpin);

    }
}
