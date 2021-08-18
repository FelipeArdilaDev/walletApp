package com.example.bancoprueba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Datos;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String user = prefe.getString("email", "");
        correspondentBankUser = datos.getUserCorresponsal(user);

        Log.d("user", correspondentBankUser.getEmail() + correspondentBankUser.getSaldo());

        datos.open();
        tvName = findViewById(R.id.tvName);
        tvName.setText("Bienvenido: " + correspondentBankUser.getName());
        tvSaldo = findViewById(R.id.tvSaldo);
        tvSaldo.setText(String.valueOf("Saldo Corresponsal: " + correspondentBankUser.getSaldo()));


    }

    public void registerBank(View v) {
        Intent intento = new Intent(this, RegisterNewBankActivity.class);
        startActivity(intento);
    }

    public void retirarMonto(View v) {
        Intent intent = new Intent(this, RetiroActivity.class);
        startActivity(intent);

    }

    public void consultaDeSaldo(View v) {
        Intent intent = new Intent(this, ConsultarSaldoActivity.class);
        startActivity(intent);
    }

    public void depositarDinero(View v) {
        Intent intent = new Intent(this, DepositoActivity.class);
        startActivity(intent);

    }


}
