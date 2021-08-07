package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Corresponsal.UserBank;
import com.example.Corresponsal.UserDataBase;
import com.example.Datos;
import com.google.android.material.textfield.TextInputEditText;

public class MenuActivity extends AppCompatActivity {

    TextView tvSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        String newemail;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                newemail = null;

            } else {
                newemail = extras.getString("email");
            }
        } else {
            newemail = (String) savedInstanceState.getSerializable("email");
        }


        getSupportActionBar().hide();
        tvSaldo = findViewById(R.id.tvSaldo);

       Datos datos = new Datos(this);
       UserDataBase userDataBase = datos.mostrarDatos(newemail);
       tvSaldo.setText(String.valueOf("saldo: " + userDataBase.getSaldo()));

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






}
