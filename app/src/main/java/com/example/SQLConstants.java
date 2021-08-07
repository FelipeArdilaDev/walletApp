package com.example;

import android.content.Context;

public class SQLConstants {

    //Usuarios Corresponsal
    public static final String DB = "usuarios.db";

    public static final String TABLE_USUARIOS = "usuarios";
    public static final String COLUMN_ID ="id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SALDO = "saldo";

    public static final String SQL_CREATE_TABLE_USUARIOS =
            "CREATE TABLE " + TABLE_USUARIOS + " " +
                   "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_SALDO + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " + ");";

    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_USUARIOS;


    //Usuarios Clientes
    public static final String USUARIOS_BANK = "usuariosBank";
    public static final String COLUMN_BANK_ID = "id";
    public static final String COLUMN_BANK_SALDO = "Saldo";
    public static final String COLUMN_BANK_NUMBER_COUNT = "numberCountBank";
    public static final String COLUMN_BANK_PASSWORD = "passwordBank";

    public static final String SQL_CREATE_TABLE_BANK_USUARIOS =
            "CREATE TABLE " + USUARIOS_BANK + " " +
                    "(" + COLUMN_BANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_BANK_SALDO + " TEXT, " +
                    COLUMN_BANK_NUMBER_COUNT + " TEXT, " +
                    COLUMN_BANK_PASSWORD + " TEXT " + ");";

    public static final String SQL_BANK_DELETE =
            "DROP TABLE " + USUARIOS_BANK;

    public static final String SEARCH_BY_ID = "id=?";


    public SQLConstants(Context context) {
    }

    public static final String[] BANK_COLUMN = {COLUMN_BANK_ID,COLUMN_BANK_SALDO,COLUMN_BANK_NUMBER_COUNT,COLUMN_BANK_PASSWORD};
}


