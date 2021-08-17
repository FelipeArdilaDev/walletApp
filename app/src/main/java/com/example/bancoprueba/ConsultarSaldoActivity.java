package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Corresponsal.UserBankClient;
import com.example.Datos;
import com.google.android.material.textfield.TextInputEditText;

public class ConsultarSaldoActivity extends AppCompatActivity {

    TextInputEditText buscar;
    Button consultar;
    Datos datos;
    UserBankClient userBankClient;
    TextView tvMostrarSaldo;
    CorrespondentBankUser bankCorresponsal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_saldo);
        getSupportActionBar().hide();


        bankCorresponsal = new CorrespondentBankUser();
        userBankClient = new UserBankClient();

        tvMostrarSaldo = findViewById(R.id.tvMostrarSaldo);
        buscar = findViewById(R.id.tiConsulta);
        consultar = findViewById(R.id.consultar);



        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = buscar.getText().toString();
                userBankClient.setId(id);

                datos = new Datos(getApplicationContext());
                datos.open();
                SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
                int saldo = prefe.getInt("saldo",0);
                //datos.recuperarDato(bankCorresponsal);

                int saldoCorresponsal = bankCorresponsal.getSaldo();
                int nuevoSaldo;
                if (datos.consultaUserClient(userBankClient)){
                    tvMostrarSaldo.setText(String.valueOf("saldo: " + userBankClient.getSaldo()));
                    datos.updateUserBankCopnsulta(userBankClient);
                    nuevoSaldo = saldo + 1000;
                    bankCorresponsal.setSaldo(nuevoSaldo);
                    datos.updateUserCorresponsal(bankCorresponsal);

                    Toast.makeText(ConsultarSaldoActivity.this, "Se encontro el usuario", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ConsultarSaldoActivity.this, "No se encontro el usuario", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}