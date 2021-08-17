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

public class RetiroActivity extends AppCompatActivity {

    TextInputEditText timontoRetiro;
    TextInputEditText numberDocument;
    TextInputEditText pinRetiro;
    TextInputEditText pinRetiroConfirm;
    Button retirarDinero;
    DBHelper dbHelper;
    Datos datos;
    UserBankClient userBankClient;
    CorrespondentBankUser correspondentBankUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);
        getSupportActionBar().hide();


        timontoRetiro = findViewById(R.id.timontoRetiro);
        numberDocument = findViewById(R.id.numberDocument);
        pinRetiro = findViewById(R.id.pinRetiro);

        pinRetiroConfirm = findViewById(R.id.pinRetiroConfirm);


        dbHelper = new DBHelper(this);
        datos = new Datos(this);
        userBankClient = new UserBankClient();
        correspondentBankUser = new CorrespondentBankUser();



        retirarDinero = findViewById(R.id.retirarDinero);
        retirarDinero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = numberDocument.getText().toString();
                userBankClient.setId(id);

                String pin = pinRetiro.getText().toString();
                userBankClient.setPassword(pin);


                datos.open();

                String pinConfirm = pinRetiroConfirm.getText().toString();
                String pinR = pinRetiro.getText().toString();
                userBankClient.setPassword(pinR);


                int retiroMonto = Integer.parseInt(timontoRetiro.getText().toString());
                userBankClient.setSaldo(retiroMonto);

                int montoRetiro;
                montoRetiro = (retiroMonto);


                int nuevoSaldoC;

                // validar si el usuario cliente existe
                if(datos.validateUserClient(userBankClient)){
                    Toast.makeText(getApplicationContext(), "Usuario encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RetiroActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }


                // validar si el confimrar pin coincide con el pin de la cuenta
                if (pin.equals(pinConfirm)){
                    Toast.makeText(RetiroActivity.this, "El pin Coincide", Toast.LENGTH_SHORT).show();
                } else {
                    pinRetiroConfirm.setError("pin no coincide");
                }

                // validar que el usuario tiene saldo suficiente
                if (datos.validarMontoRetiro(userBankClient)){
                    datos.open();

                    //actualizar usuario cliente
                    datos.updateUserBank(userBankClient);
                    SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
                    //int saldo = prefe.getInt("saldo",0);
                    //onBackPressed();
                    //datos.recuperarDato(correspondentBankUser);

                    //Traer del shared
                    String correo = prefe.getString("email", "");
                    correspondentBankUser = datos.getUserCorresponsal(correo);


                    nuevoSaldoC = correspondentBankUser.getSaldo() + 2000 + montoRetiro;
                    correspondentBankUser.setSaldo(nuevoSaldoC);
                    datos.open();

                    //actualizar usuario corresponsal
                    datos.updateUserCorresponsal(correspondentBankUser);
                    datos.close();

                    Toast.makeText(RetiroActivity.this, "Se realizo el retiro", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                }
            }


        });


    }


}
