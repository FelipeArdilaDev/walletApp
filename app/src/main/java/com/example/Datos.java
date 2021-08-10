package com.example;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.Corresponsal.UserBank;
import com.example.Corresponsal.UserDataBase;
import com.example.Helpers.DBHelper;
import com.example.bancoprueba.RetiroActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class Datos {


    public UserDataBase userDataBase;
    public ContentValues values;
    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{4,20}" +
                    "$");

    public Context context;
    public SQLiteDatabase db;
    public SQLiteOpenHelper sqLiteOpenHelper;
    public ContentValues contentValues;
    RetiroActivity retiro;




    public Datos(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DBHelper(context);
        db = sqLiteOpenHelper.getWritableDatabase();

    }

    public void open() {
        db = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteOpenHelper.close();



    }

    public void insertUsuario(UserDataBase usuario) {
        ContentValues values = usuario.toValues();
        db.insert(SQLConstants.TABLE_USUARIOS, null, values);


    }

    public void insertUsuarioBank(UserBank userBank) {
        ContentValues values = userBank.toValues();
        db.insert(SQLConstants.USUARIOS_BANK, null, values);
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
        Cursor cursor = db.query(SQLConstants.USUARIOS_BANK,
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
        ContentValues values = userBank.values();
        SQLiteDatabase sqLiteDatabase1 = this.sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase1.update(SQLConstants.USUARIOS_BANK,
                values,SQLConstants.COLUMN_BANK_ID,null);
    }

    public void updateUserCorresponsal(UserDataBase userDataBase){
        ContentValues values = userDataBase.values();
        SQLiteDatabase sqLiteDatabase1 = this.sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase1.update(SQLConstants.TABLE_USUARIOS,
                values,SQLConstants.COLUMN_SALDO,null);
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


    public void guardarDato(String id){
        SharedPreferences sp = context.getSharedPreferences("documento", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String email = SQLConstants.COLUMN_EMAIL;
        editor.putString(email,id);
        editor.commit();
    }

    public void recuperarDato(String id){
        SharedPreferences sp = context.getSharedPreferences("documento", Context.MODE_PRIVATE);
        String dato = sp.getString("documento",id);

        if(dato.equals("")) {
            userDataBase.setEmail("");
            Toast.makeText(context, "No Existe", Toast.LENGTH_SHORT).show();



        }

    }

    public boolean validarMontoRetiro(UserBank userBank) {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.USUARIOS_BANK + " WHERE " + SQLConstants.COLUMN_BANK_ID+ " = '" + userBank.getId() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int valor = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_SALDO)));
                    if (userBank.getSaldo() < valor) {
                        int retiro = valor - userBank.getSaldo();
                        userBank.setSaldo(retiro);
                        return true;
                    }
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return false;
        }
        return false;
    }

}


