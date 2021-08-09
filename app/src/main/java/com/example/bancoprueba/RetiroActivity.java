package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Corresponsal.UserBank;
import com.example.Datos;
import com.example.Helpers.DBHelper;
import com.example.SQLConstants;
import com.google.android.material.textfield.TextInputEditText;

public class RetiroActivity extends AppCompatActivity {

    TextInputEditText timontoRetiro;
    TextInputEditText numberDocument;
    TextInputEditText pinRetiro;
    TextInputEditText pinRetiroConfirm;
    Button retirarDinero;
    DBHelper dbHelper;
    UserBank userBank;
    Datos datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);
        getSupportActionBar().hide();


        timontoRetiro = findViewById(R.id.timontoRetiro);
        numberDocument = findViewById(R.id.numberDocument);
        dbHelper = new DBHelper(this);
        userBank = new UserBank();
        datos = new Datos(this);



        retirarDinero = findViewById(R.id.retirarDinero);
        retirarDinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = numberDocument.getText().toString();
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(SQLConstants.COLUMN_BANK_SALDO,
                        timontoRetiro.getText().toString());

                pinRetiro = findViewById(R.id.pinRetiro);
                datos.open();

                pinRetiro = findViewById(R.id.pinRetiro);
                String pin = pinRetiro.getText().toString();
                pinRetiroConfirm = findViewById(R.id.pinRetiroConfirm);

                String pinConfirm = pinRetiroConfirm.getText().toString();
                String document = numberDocument.getText().toString();
                String retiro = timontoRetiro.getText().toString();

                int montoRetiro;
                montoRetiro = Integer.parseInt(retiro);
                int nuevoSaldo;
                int saldo = userBank.getSaldo();

                if(dbHelper.validateUserBank(document,pin)){
                    Toast.makeText(getApplicationContext(), "Usuario encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RetiroActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }


                if (pin.equals(pinConfirm)){
                    Toast.makeText(RetiroActivity.this, "El pin Coincide", Toast.LENGTH_SHORT).show();
                } else {
                    pinRetiroConfirm.setError("pin no coincide");
                }

                if ( saldo > montoRetiro){
                    nuevoSaldo = saldo-montoRetiro;
                    userBank.setSaldo(nuevoSaldo);

                    datos.open();
                    datos.updateUserBank(userBank);
                    Toast.makeText(RetiroActivity.this, "Se realizo el retiro", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }








}
