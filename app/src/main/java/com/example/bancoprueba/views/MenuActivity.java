package com.example.bancoprueba.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.utils.Datos;
import com.example.bancoprueba.R;
import com.example.bancoprueba.historial.HistorialTransaccionesActivity;

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
        tvSaldo.setText("Saldo Corresponsal: " + correspondentBankUser.getSaldo());
        datos.close();
    }

    //iniciar actividad de registrar cuenta de cliente
    public void registerBank(View v) {
        Intent intent = new Intent(this, RegisterNewBankActivity.class);
        startActivity(intent);
    }
    //iniciar actividad de retirar dinero usuario cliente
    public void retirarMonto(View v) {
        Intent intent = new Intent(this, RetiroActivity.class);
        startActivity(intent);
    }
    //iniciar actividad de consultar saldo de usuario cliente
    public void consultaDeSaldo(View v) {
        Intent intent = new Intent(this, ConsultarSaldoActivity.class);
        startActivity(intent);
    }
    //iniciar actividad de depositar dinero de usuario cliente
    public void depositarDinero(View v) {
        Intent intent = new Intent(this, DepositoActivity.class);
        startActivity(intent);
    }

    //iniciar actividad de historial de transacciones
    public void historialTransacciones(View view) {
        startActivity(new Intent(this, HistorialTransaccionesActivity.class));
    }

}
