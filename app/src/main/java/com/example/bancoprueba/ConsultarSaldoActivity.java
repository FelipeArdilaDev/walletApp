package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Corresponsal.UserBankClient;
import com.example.Datos;
import com.google.android.material.textfield.TextInputEditText;

public class ConsultarSaldoActivity extends AppCompatActivity {

    TextInputEditText buscar;
    Button consultar;
    Datos datos;
    UserBankClient userBankClient;
    TextView tvMostrarSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_saldo);
        getSupportActionBar().hide();

        userBankClient = new UserBankClient();
        tvMostrarSaldo = findViewById(R.id.tvMostrarSaldo);
        buscar = findViewById(R.id.tiCOnsulta);
        consultar = findViewById(R.id.consultar);
        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idbuscar = buscar.getText().toString();
                datos = new Datos(getApplicationContext());
                datos.open();
                userBankClient = datos.getUser(idbuscar);
                tvMostrarSaldo.setText(String.valueOf("saldo: " + userBankClient.getSaldo()));
                Toast.makeText(ConsultarSaldoActivity.this, "Se encontro el usuario", Toast.LENGTH_SHORT).show();
            }
        });

    }
}