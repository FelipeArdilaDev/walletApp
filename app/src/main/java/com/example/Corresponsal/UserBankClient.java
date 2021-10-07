package com.example.Corresponsal;

import android.content.ContentValues;

import com.example.Helpers.SQLConstants;

public class UserBankClient {

    private String id;
    private int saldo;
    private String numberCount;
    private String password;


    public UserBankClient(String id, int saldo, String numberCount, String password){
        this.id = id;
        this.saldo = saldo;
        this.numberCount = numberCount;
        this.password = password;

    }

    public UserBankClient(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(String numberCount) {
        this.numberCount = numberCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues(4);
        contentValues.put(SQLConstants.COLUMN_BANK_ID,id);
        contentValues.put(SQLConstants.COLUMN_BANK_SALDO,saldo);
        contentValues.put(SQLConstants.COLUMN_BANK_NUMBER_COUNT,numberCount);
        contentValues.put(SQLConstants.COLUMN_BANK_PASSWORD,password);

        return contentValues;
    }

    public ContentValues values(){
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(SQLConstants.COLUMN_BANK_SALDO,saldo);

        return contentValues;

    }

    public ContentValues valuesConsulta(){
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(SQLConstants.COLUMN_BANK_SALDO,saldo);

        return contentValues;

    }



}
