package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Datos;
import com.example.SQLConstants;

public class MenuActivity extends AppCompatActivity {

    TextView tvSaldo;
    TextView tvName;
    CorrespondentBankUser correspondentBankUser;
    Datos datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().hide();

        correspondentBankUser = new CorrespondentBankUser();
        datos = new Datos(this);


        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        int saldo = prefe.getInt("saldo",0);
        String name = prefe.getString("name","");

        //datos.recuperarDato(correspondentBankUser);
        tvSaldo = findViewById(R.id.tvSaldo);
        tvName = findViewById(R.id.tvName);
        tvName.setText("Bienvenido: " + name);
        tvSaldo.setText(String.valueOf("Saldo Corresponsal: " + saldo));

    }

    public void registerBank(View v) {
        Intent intento = new Intent(this, RegisterNewBankActivity.class);
        startActivity(intento);
    }

    public void retirarMonto(View v) {
        Intent intent = new Intent(this, RetiroActivity.class);
        startActivity(intent);

    }

    public void consultaDeSaldo(View v){
        Intent intent = new Intent(this,ConsultarSaldoActivity.class);
        startActivity(intent);
    }

    public void depositarDinero(View v){
        Intent intent = new Intent(this, DepositoActivity.class);
        startActivity(intent);

    }






}
