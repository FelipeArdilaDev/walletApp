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

import java.util.regex.Pattern;

public class Datos {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{4,20}" +
                    "$");

    public Context context;
    public SQLiteDatabase sqLiteDatabase;
    public SQLiteOpenHelper sqLiteOpenHelper;



    public Datos(Context context){
        this.context =  context;
        sqLiteOpenHelper = new DBHelper(context);
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();

    }

    public void open(){
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close(){
        sqLiteOpenHelper.close();

    }

    public void insertUsuario(UserDataBase usuario){
        ContentValues values = usuario.toValues();
        sqLiteDatabase.insert(SQLConstants.TABLE_USUARIOS,null,values);


    }

    public void inserUsuarioBank(UserBank userBank){
        ContentValues values = userBank.toValues();
        sqLiteDatabase.insert(SQLConstants.USUARIOS_BANK,null,values);
    }


   public boolean validateUser(UserDataBase userDataBase) {
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        String query = " SELECT * FROM " + SQLConstants.TABLE_USUARIOS + " WHERE " + SQLConstants.COLUMN_EMAIL +
                " ='" + SQLConstants.COLUMN_EMAIL + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        try {
            if (cursor.getCount() !=0) {
                while (cursor.moveToFirst()) {
                    String user = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_EMAIL));
                    String pass = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_PASSWORD));
                    if (userDataBase.getEmail().equals(user) && (userDataBase.getPassword().equals(pass))) {
                        return true;
                    }
                }
            }
        }catch (Exception ex){
            ex.toString();
            return false;
        }
        return false;
    }

    public UserDataBase mostrarDatos(String email) {

        UserDataBase userDataBase1 = new UserDataBase();
        SQLiteDatabase sqLiteDatabase = this.sqLiteOpenHelper.getReadableDatabase();
        String query = " SELECT * FROM " + SQLConstants.TABLE_USUARIOS + " WHERE " + SQLConstants.COLUMN_EMAIL + " = '" + SQLConstants.TABLE_USUARIOS + "';";
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

    public void updateUser(String id,ContentValues contentValues){
        String[] whereArgs = new String[] {String.valueOf(id)};
        sqLiteDatabase.update(SQLConstants.USUARIOS_BANK,
                contentValues,SQLConstants.SEARCH_BY_ID,whereArgs);

    }

    public static boolean checkEmail(String email){
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validateUserBank(UserBank userBank) {
        SQLiteDatabase sqLiteDatabase = this.sqLiteOpenHelper.getReadableDatabase();
        String query = " SELECT * FROM " + SQLConstants.USUARIOS_BANK + " WHERE " + SQLConstants.COLUMN_BANK_ID + " ='" + userBank.getId() + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        try {
            if (cursor.getCount() !=0) {
                while (cursor.moveToNext()) {
                    String user = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_ID));
                    String pass = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_PASSWORD));
                    if (userBank.getId().equals(user) && (userBank.getPassword().equals(pass))) {
                        return true;
                    }
                }
            }
        }catch (Exception ex){
            ex.toString();
            return false;
        }
        return false;
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


