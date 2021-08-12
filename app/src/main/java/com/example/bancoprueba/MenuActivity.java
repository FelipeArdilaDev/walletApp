package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Datos;

public class MenuActivity extends AppCompatActivity {

    TextView tvSaldo;
    TextView tvName;
    CorrespondentBankUser correspondentBankUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        String email;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                email = null;

            } else {
                email = extras.getString("email");
            }
        } else {
            email = (String) savedInstanceState.getSerializable("email");
        }


        getSupportActionBar().hide();
        tvSaldo = findViewById(R.id.tvSaldo);



       Datos datos = new Datos(this);
       CorrespondentBankUser correspondentBankUser = datos.mostrarDatos(email);
       tvSaldo.setText(String.valueOf("saldo: " + correspondentBankUser.getSaldo()));



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
