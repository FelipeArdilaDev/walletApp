package com.example.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bancoprueba.R;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.models.UserBankClient;
import com.example.model.utils.Datos;
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


        bankCorresponsal = new CorrespondentBankUser();
        userBankClient = new UserBankClient();

        tvMostrarSaldo = findViewById(R.id.tvMostrarSaldo);
        buscar = findViewById(R.id.tiConsulta);
        consultar = findViewById(R.id.consultar);


        consultar.setOnClickListener(v -> {

            String id = buscar.getText().toString();
            userBankClient.setId(id);

            datos = new Datos(getApplicationContext());
            datos.open();
            int nuevoSaldo;

            // validar si el usuario cliente existe
            if (datos.consultaUserClient(userBankClient)) {

                // traer el shared
                SharedPreferences prefes = getSharedPreferences("datos", Context.MODE_PRIVATE);
                String correo = prefes.getString("email", "");
                bankCorresponsal = datos.getUserCorresponsal(correo);

                tvMostrarSaldo.setText(String.valueOf("" + userBankClient.getSaldo()));
                datos.updateUserBankCopnsulta(userBankClient);
                nuevoSaldo = bankCorresponsal.getSaldo() + 1000;
                bankCorresponsal.setSaldo(nuevoSaldo);

                // actualizar el usuario corresponsal
                datos.updateUserCorresponsal(bankCorresponsal);

                Toast.makeText(ConsultarSaldoActivity.this, "Se encontro el usuario", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(ConsultarSaldoActivity.this, "No se encontro el usuario", Toast.LENGTH_SHORT).show();
            }


        });

    }
}