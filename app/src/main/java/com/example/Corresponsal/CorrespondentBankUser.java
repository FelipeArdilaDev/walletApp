package com.example.Corresponsal;

import android.content.ContentValues;

import com.example.SQLConstants;

import java.io.Serializable;

public class CorrespondentBankUser implements Serializable {

    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private int saldo;


    public CorrespondentBankUser(String id, String name, String email, String password, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;

    }

    public CorrespondentBankUser() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }



    public ContentValues toValues(){
        ContentValues contentValues = new ContentValues(6);
        contentValues.put(SQLConstants.COLUMN_ID,id);
        contentValues.put(SQLConstants.COLUMN_EMAIL,email);
        contentValues.put(SQLConstants.COLUMN_NAME,name);
        contentValues.put(SQLConstants.COLUMN_PASSWORD,password);
        contentValues.put(SQLConstants.COLUMN_PHONE,phone);
        contentValues.put(SQLConstants.COLUMN_SALDO,saldo);

        return contentValues;
    }

    public ContentValues values(){
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(SQLConstants.COLUMN_SALDO,saldo);

        return contentValues;
    }





}
