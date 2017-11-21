package com.example.juan.practicas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JuanSalinas on 13/09/2017.
 */

public class ClientesSQLiteHelper extends SQLiteOpenHelper {

    //SENTENCIA PARA CREAR LA TABLA EN LA BASE DE DATOS
    String sqlabonos ="CREATE TABLE abonos (nombre VARCHAR, abono VARCHAR, fecha VARCHAR)";
    String sqlcargos ="CREATE TABLE cargos (nombre VARCHAR, cargo VARCHAR, fecha VARCHAR )";
    String sqlhistori="CREATE TABLE midia (nombre VARCHAR, abono VARCHAR)";

    public ClientesSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlabonos);
        db.execSQL(sqlcargos);
        db.execSQL(sqlhistori);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS abonos");
        db.execSQL(sqlabonos);
        db.execSQL("DROP TABLE IF EXISTS cargos");
        db.execSQL(sqlabonos);
        db.execSQL("DROP TABLE IF EXISTS midia");
        db.execSQL(sqlhistori);


    }
}
