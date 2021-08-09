package com.example.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.Corresponsal.UserDataBase;
import com.example.SQLConstants;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;

    Context context;
    public DBHelper(Context context){
        super(context, SQLConstants.DB,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLConstants.SQL_CREATE_TABLE_USUARIOS);
        sqLiteDatabase.execSQL(SQLConstants.SQL_CREATE_TABLE_BANK_USUARIOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQLConstants.SQL_DELETE);
        sqLiteDatabase.execSQL(SQLConstants.SQL_BANK_DELETE);
        onCreate(sqLiteDatabase);

    }

    public boolean validateUser(String email , String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        String [] column = { SQLConstants.COLUMN_ID };
        String selection = SQLConstants.COLUMN_EMAIL + "=?" + " and " + SQLConstants.COLUMN_PASSWORD + "=?";
        String [] selectionArgs = {email,password};
        Cursor cursor = myDB.query(SQLConstants.TABLE_USUARIOS,column,selection,selectionArgs,
                null,null,null);
        int count = cursor.getCount();
        myDB.close();
        cursor.close();
        if(count >0)
            return true;
        else
            return false;
    }

    public boolean validateUserBank(String email , String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        String [] column = { SQLConstants.COLUMN_BANK_ID };
        String selection = SQLConstants.COLUMN_BANK_ID + "=?" + " and " + SQLConstants.COLUMN_BANK_PASSWORD + "=?";
        String [] selectionArgs = {email,password};
        Cursor cursor = myDB.query(SQLConstants.USUARIOS_BANK,column,selection,selectionArgs,
                null,null,null);
        int count = cursor.getCount();
        myDB.close();
        cursor.close();
        if(count >0)
            return true;
        else
            return false;
    }






}
