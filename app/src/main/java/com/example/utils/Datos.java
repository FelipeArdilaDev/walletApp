package com.example.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Corresponsal.UserBankClient;
import com.example.Helpers.DBHelper;
import com.example.Helpers.SQLConstants;
import com.example.models.ResultadoTransaccion;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Datos {


    public static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{4,20}" +
                    "$");
    public CorrespondentBankUser correspondentBankUser;
    public Context context;
    public SQLiteDatabase db;
    public SQLiteOpenHelper sqLiteOpenHelper;


    public Datos(Context context) {
        this.context = context;
        sqLiteOpenHelper = new DBHelper(context);
        db = sqLiteOpenHelper.getWritableDatabase();

    }

    public static boolean checkEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void open() {
        db = sqLiteOpenHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteOpenHelper.close();
    }

    public void insertUsuario(CorrespondentBankUser usuario) {
        ContentValues values = usuario.toValues();
        db.insert(SQLConstants.TABLE_USUARIOS, null, values);
    }

    public void insertUsuarioBank(UserBankClient userBankClient) {
        ContentValues values = userBankClient.toValues();
        db.insert(SQLConstants.USUARIOS_BANK, null, values);
    }

    public void updateUserBank(UserBankClient userBankClient) {
        ContentValues values = userBankClient.values();
        SQLiteDatabase sqLiteDatabase1 = this.sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase1.update(SQLConstants.USUARIOS_BANK,
                values, SQLConstants.COLUMN_BANK_ID, null);
    }

    public void updateUserCorresponsal(CorrespondentBankUser correspondentBankUser) {
        ContentValues values = correspondentBankUser.values();
        SQLiteDatabase sqLiteDatabase1 = this.sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase1.update(SQLConstants.TABLE_USUARIOS,
                values, SQLConstants.COLUMN_SALDO, null);
    }

    public void updateUserBankCopnsulta(UserBankClient userBankClient) {
        ContentValues values = userBankClient.valuesConsulta();
        SQLiteDatabase sqLiteDatabase1 = this.sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase1.update(SQLConstants.USUARIOS_BANK,
                values, SQLConstants.COLUMN_BANK_ID, null);
    }

    public boolean validatePassword(TextInputEditText passwordRegister) {
        String passwordInput = passwordRegister.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordRegister.setError("El campo no puede estar vacío");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            passwordRegister.setError("La contraseña es demasiado débil");
            return false;
        } else {
            return true;
        }
    }

    public boolean validarMontoRetiro(UserBankClient userBankClient) {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.USUARIOS_BANK + " WHERE " + SQLConstants.COLUMN_BANK_ID + " = '" + userBankClient.getId() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int valor = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_SALDO)));
                    if (userBankClient.getSaldo() < valor) {
                        int retiro = valor - userBankClient.getSaldo();
                        userBankClient.setSaldo(retiro);
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

    public boolean validarMontoTx(UserBankClient userBankClient) {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.USUARIOS_BANK + " WHERE " + SQLConstants.COLUMN_BANK_ID + " = '" + userBankClient.getId() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    int valor = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_SALDO)));
                    if (userBankClient.getSaldo() < valor) {
                        int transferencia = valor - userBankClient.getSaldo();
                        userBankClient.setSaldo(transferencia);
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


    public boolean validateUserCorrespondent(CorrespondentBankUser correspondentBankUser) {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.TABLE_USUARIOS + " WHERE " + SQLConstants.COLUMN_EMAIL + " = '" + correspondentBankUser.getEmail() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String email = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_EMAIL));
                    String password = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_PASSWORD));
                    if (correspondentBankUser.getEmail().equals(email) && (correspondentBankUser.getPassword().equals(password))) {
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


    public CorrespondentBankUser getUserCorresponsal(String email) {

        CorrespondentBankUser corresponal = new CorrespondentBankUser();

        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.TABLE_USUARIOS + " WHERE " + SQLConstants.COLUMN_EMAIL + " = '" + email + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    corresponal.setId(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_ID)));
                    corresponal.setEmail(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_EMAIL)));
                    corresponal.setSaldo(cursor.getInt(cursor.getColumnIndex(SQLConstants.COLUMN_SALDO)));
                    corresponal.setName(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_NAME)));
                    corresponal.setPassword(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_PASSWORD)));
                    corresponal.setPhone(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_PHONE)));
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return corresponal;
        }
        return corresponal;
    }

    public boolean validateUserClient(UserBankClient userBankClient) {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.USUARIOS_BANK + " WHERE " + SQLConstants.COLUMN_BANK_ID + " = '" + userBankClient.getId() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String document = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_ID));
                    String pin = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_PASSWORD));
                    if (userBankClient.getId().equals(document) && (userBankClient.getPassword().equals(pin))) {
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

    public boolean consultaUserClient(UserBankClient userBankClient) {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.USUARIOS_BANK + " WHERE " + SQLConstants.COLUMN_BANK_ID + " = '" + userBankClient.getId() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String document = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_ID));
                    int valor = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_SALDO)));
                    if (userBankClient.getId().equals(document)) {
                        int consulta = valor - 1000;
                        userBankClient.setSaldo(consulta);
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

    public boolean validateUserClientDeposito(UserBankClient userBankClient) {
        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.USUARIOS_BANK + " WHERE " + SQLConstants.COLUMN_BANK_ID + " = '" + userBankClient.getId() + "';";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    String document = cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_ID));
                    int valor = Integer.parseInt(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_BANK_SALDO)));
                    if (userBankClient.getId().equals(document)) {
                        int deposito = valor;
                        userBankClient.setSaldo(deposito);
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

    public boolean insertResultadoTransaccion(ResultadoTransaccion transaccion) {
        ContentValues values = transaccion.toValues();
        long insert = db.insert(SQLConstants.RESULTADO_TRANSACCION, null, values);
        db.close();
        return insert != -1;
    }

    public ArrayList<ResultadoTransaccion> getAllResultadosTransaccion() {
        ArrayList<ResultadoTransaccion> transacciones = new ArrayList<>();

        SQLiteDatabase db = this.sqLiteOpenHelper.getReadableDatabase();
        String query = "SELECT * FROM " + SQLConstants.RESULTADO_TRANSACCION + ";";
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() != 0) {
                while (cursor.moveToNext()) {
                    ResultadoTransaccion transaccion = new ResultadoTransaccion();
                    transaccion.setId(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_ID)));
                    transaccion.setTipoTransaccion(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_TIPO)));
                    transaccion.setMonto(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_MONTO)));
                    transaccion.setCuentaCorresponsal(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_CUENTACORRESPONSAL)));
                    transaccion.setCuentaPrincipal(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_CUENTAPRINCIPAL)));
                    transaccion.setCuentaSecundaria(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_CUENTASECUNDARIA)));
                    transaccion.setFecha(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_FECHA)));
                    transaccion.setHora(cursor.getString(cursor.getColumnIndex(SQLConstants.COLUMN_TRANSACCION_HORA)));


                    transacciones.add(transaccion);
                }
            }
        } catch (Exception ex) {
            ex.toString();
            return null;
        }
        return transacciones;

    }
}


