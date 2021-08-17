package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Corresponsal.UserBankClient;
import com.example.Corresponsal.CorrespondentBankUser;
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
    UserBankClient userBankClient;
    CorrespondentBankUser correspondentBankUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);
        getSupportActionBar().hide();

        tiDocument = findViewById(R.id.tiDocumentDeposito);
        tiDepotiso = findViewById(R.id.tiDocumentoPersona);
        tiMontoDeposito = findViewById(R.id.tiMontoDeposito);



        correspondentBankUser = new CorrespondentBankUser();
        userBankClient = new UserBankClient();
        dbHelper = new DBHelper(this);


        depositar = findViewById(R.id.depositar);
        depositar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = tiDocument.getText().toString();
                userBankClient.setId(id);

                datos  = new Datos(getApplicationContext());
                datos.open();
                SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
                int saldo = prefe.getInt("saldo",0);
                //datos.recuperarDato(correspondentBankUser);



                String deposito = tiMontoDeposito.getText().toString();
                int valorComision = correspondentBankUser.getSaldo();
                int saldoNuevo;
                int montoDeposito;
                montoDeposito = Integer.parseInt(deposito);
                int nuevoSaldo;


                if(datos.validateUserClientDeposito(userBankClient)){
                    nuevoSaldo = userBankClient.getSaldo() + montoDeposito;
                    saldoNuevo = saldo - montoDeposito + 1000;
                    correspondentBankUser.setSaldo(saldoNuevo);

                    userBankClient.setSaldo(nuevoSaldo);
                    datos.open();
                    datos.updateUserBank(userBankClient);
                    datos.updateUserCorresponsal(correspondentBankUser);
                    Toast.makeText(DepositoActivity.this, "Se realizo el deposito", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                } else {
                    Toast.makeText(DepositoActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}