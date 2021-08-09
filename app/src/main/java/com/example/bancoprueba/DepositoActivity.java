package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Corresponsal.UserBank;
import com.example.Datos;
import com.example.Helpers.DBHelper;
import com.example.SQLConstants;
import com.google.android.material.textfield.TextInputEditText;

public class DepositoActivity extends AppCompatActivity {
    TextInputEditText tiDocument;
    TextInputEditText tiDepotiso;
    TextInputEditText tiMontoDeposito;
    Button depositar;
    DBHelper dbHelper;
    Datos datos;
    UserBank userBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        tiDocument = findViewById(R.id.tiDocumentDeposito);
        tiDepotiso = findViewById(R.id.tiDocumentoPersona);
        tiMontoDeposito = findViewById(R.id.tiMontoDeposito);
        dbHelper = new DBHelper(this);
        userBank = new UserBank();
        datos  = new Datos(this);
        depositar = findViewById(R.id.depositar);
        depositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = tiDocument.getText().toString();
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(SQLConstants.COLUMN_BANK_SALDO,
                        tiMontoDeposito.getText().toString());


                String deposito = tiMontoDeposito.getText().toString();

                int montoDeposito;
                montoDeposito = Integer.parseInt(deposito);
                int nuevoSaldo;
                int saldo = userBank.getSaldo();

                String email = tiDocument.getText().toString();

                if(dbHelper.validateUserBankDeposito(email)){
                    nuevoSaldo = saldo+montoDeposito;
                    userBank.setSaldo(nuevoSaldo);
                    datos.open();
                    datos.updateUserBank(userBank);
                    Toast.makeText(DepositoActivity.this, "Se realizo el deposito", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(DepositoActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}