package com.example.bancoprueba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Corresponsal.UserBankClient;
import com.example.Datos;
import com.example.Helpers.DBHelper;
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

                datos = new Datos(getApplicationContext());
                datos.open();
                SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
                int saldo = prefe.getInt("saldo", 0);
                correspondentBankUser.setSaldo(saldo);


                String deposito = tiMontoDeposito.getText().toString();

                int saldoNuevo;
                int montoDeposito;
                montoDeposito = Integer.parseInt(deposito);
                int nuevoSaldo;


                // validar si el usuario cliente existe
                if (datos.validateUserClientDeposito(userBankClient)) {
                    SharedPreferences prefes = getSharedPreferences("datos", Context.MODE_PRIVATE);
                    String correo = prefes.getString("email", "");
                    correspondentBankUser = datos.getUserCorresponsal(correo);


                    nuevoSaldo = userBankClient.getSaldo() + montoDeposito;
                    saldoNuevo = correspondentBankUser.getSaldo() - montoDeposito + 1000;
                    userBankClient.setSaldo(nuevoSaldo);
                    correspondentBankUser.setSaldo(saldoNuevo);

                    datos.open();
                    // actualizar el usuario cliente
                    datos.updateUserBank(userBankClient);

                    // actualizar el usuario corresponsal
                    datos.updateUserCorresponsal(correspondentBankUser);

                    Intent intent = new Intent(getApplicationContext(), vouhcerDepositoActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(DepositoActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}