package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

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

        //siempre crear constructores!!!!
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
                int saldoCorresponsal = bankCorresponsal.getSaldo();
                int nuevoSaldo;
                if (datos.consultaUserClient(userBankClient)){
                    tvMostrarSaldo.setText(String.valueOf("saldo: " + userBankClient.getSaldo()));
                    datos.updateUserBankCopnsulta(userBankClient);
                    nuevoSaldo = saldoCorresponsal + 1000;
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