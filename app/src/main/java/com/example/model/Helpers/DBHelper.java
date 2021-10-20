package com.example.model.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 2;

    Context context;
    public DBHelper(Context context){
        super(context, SQLConstants.DB,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLConstants.SQL_CREATE_TABLE_USUARIOS);
        sqLiteDatabase.execSQL(SQLConstants.SQL_CREATE_TABLE_BANK_USUARIOS);
        sqLiteDatabase.execSQL(SQLConstants.SQL_CREATE_TABLE_RESULTADO_TRANSACCION);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQLConstants.SQL_DELETE);
        sqLiteDatabase.execSQL(SQLConstants.SQL_BANK_DELETE);
        sqLiteDatabase.execSQL(SQLConstants.SQL_TRANSACCION_DELETE);
        onCreate(sqLiteDatabase);
    }
}
