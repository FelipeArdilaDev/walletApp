package com.example;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.Corresponsal.UserBank;
import com.example.Corresponsal.UserDataBase;
import com.example.Helpers.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.regex.Pattern;

public class Datos {
    private ContentValues values;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{4,20}" +
                    "$");

    public Context context;
    public SQLiteDatabase sqLiteDatabase;
    public SQLiteOpenHelper sqLiteOpenHelper;
    private ContentValues contentValues;


    public Datos(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DBHelper(context);
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

    }

    public void open() {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteOpenHelper.close();

    }

    public void insertUsuario(UserDataBase usuario) {
        ContentValues values = usuario.toValues();
        sqLiteDatabase.insert(SQLConstants.TABLE_USUARIOS, null, values);


    }

    public void insertUsuarioBank(UserBank userBank) {
        ContentValues values = userBank.toValues();
        sqLiteDatabase.insert(SQLConstants.USUARIOS_BANK, null, values);
    }

    public UserDataBase mostrarDatos(String email) {

        UserDataBase userDataBase1 = new UserDataBase();
        SQLiteDatabase sqLiteDatabase = this.sqLiteOpenHelper.getReadableDatabase();
        String query = " SELECT * FROM " + SQLConstants.TABLE_USUARIOS + " WHERE " + SQLConstants.COLUMN_EMAIL + "='" + SQLConstants.TABLE_USUARIOS + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);

            if (cursor.getCount() !=0) {
                while (cursor.moveToNext()) {

                    userDataBase1.setSaldo(Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_SALDO))));

                    return  userDataBase1;

                }


            }
        return  userDataBase1;
    }

    public UserBank getUser(String id){
        UserBank userBank = new UserBank();
        String[] whereArgs = new String[]{id};
        Cursor cursor = sqLiteDatabase.query(SQLConstants.USUARIOS_BANK,
                SQLConstants.BANK_COLUMN,
                SQLConstants.SEARCH_BY_ID,
                whereArgs,
                null,null,null);

        if (cursor.getCount() == 1){
            cursor.moveToFirst();
            userBank.setId(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_ID)));
            userBank.setNumberCount(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_NUMBER_COUNT)));
            userBank.setSaldo(cursor.getInt(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_SALDO)));
            userBank.setPassword(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_PASSWORD)));


        }
        return userBank;

    }

    public void updateUserBank(UserBank userBank){
        sqLiteDatabase.update(SQLConstants.USUARIOS_BANK,
                userBank.values(),SQLConstants.SEARCH_BY_ID,null);

    }

    public static boolean checkEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public boolean validatePassword(TextInputEditText passwordRegister) {
        String passwordInput = passwordRegister.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordRegister.setError("El campo no puede estar vacío");
            return false;
        }
        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            passwordRegister.setError("La contraseña es demasiado débil");
            return false;
        } else {
            return true;
        }

    }

}


