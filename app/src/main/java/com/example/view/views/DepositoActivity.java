package com.example.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.models.CorrespondentBankUser;
import com.example.model.models.UserBankClient;
import com.example.model.utils.Datos;
import com.example.model.Helpers.DBHelRepositoryImpl;
import com.example.bancoprueba.R;
import com.example.model.models.ResultadoTransaccion;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositoActivity extends AppCompatActivity {

    TextInputEditText tiDocument;
    TextInputEditText tiDepotiso;
    TextInputEditText tiMontoDeposito;
    Button depositar;
    DBHelRepositoryImpl dbHelRepositoryImpl;
    Datos datos;
    UserBankClient userBankClient;
    CorrespondentBankUser correspondentBankUser;
    String correoCorresponsal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);

        tiDocument = findViewById(R.id.tiDocumentDeposito);
        tiDepotiso = findViewById(R.id.tiDocumentoPersona);
        tiMontoDeposito = findViewById(R.id.tiMontoDeposito);


        correspondentBankUser = new CorrespondentBankUser();
        userBankClient = new UserBankClient();
        dbHelRepositoryImpl = new DBHelRepositoryImpl(this);


        depositar = findViewById(R.id.depositar);
        depositar.setOnClickListener(v -> {
            String id = tiDocument.getText().toString();
            userBankClient.setId(id);

            datos = new Datos(getApplicationContext());
            datos.open();
            SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
            int saldo = prefe.getInt("saldo", 0);
            correspondentBankUser.setSaldo(saldo);


            String deposito = tiMontoDeposito.getText().toString();

            int saldoNuevo;
            int montoDeposito;
            montoDeposito = Integer.parseInt(deposito);
            int nuevoSaldo;


            // validar si el usuario cliente existe
            if (datos.validateUserClientDeposito(userBankClient)) {
                SharedPreferences prefes = getSharedPreferences("datos", Context.MODE_PRIVATE);
                String correo = prefes.getString("email", "");
                correspondentBankUser = datos.getUserCorresponsal(correo);


                nuevoSaldo = userBankClient.getSaldo() + montoDeposito;
                saldoNuevo = correspondentBankUser.getSaldo() - montoDeposito + 1000;
                userBankClient.setSaldo(nuevoSaldo);
                correspondentBankUser.setSaldo(saldoNuevo);
                datos.open();
                // actualizar el usuario cliente
                datos.updateUserBank(userBankClient);

                //Traer del shared
                correoCorresponsal = prefe.getString("email", "");
                correspondentBankUser = datos.getUserCorresponsal(correoCorresponsal);

                // actualizar el usuario corresponsal
                datos.updateUserCorresponsal(correspondentBankUser);

                //Crear objeto ResultadoTransaccion
                crearResultadoTransaccion();

            } else {
                Toast.makeText(DepositoActivity.this, "No existe el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearResultadoTransaccion() {
        ResultadoTransaccion resultadoTransaccion = new ResultadoTransaccion();
        resultadoTransaccion.setTipoTransaccion("DEPOSITO");
        resultadoTransaccion.setFecha(getFechaActual());
        resultadoTransaccion.setHora(getHoraActual());
        resultadoTransaccion.setMonto(tiMontoDeposito.getText().toString());
        resultadoTransaccion.setCuentaPrincipal(tiDocument.getText().toString());
        resultadoTransaccion.setCuentaCorresponsal(correoCorresponsal);

        datos.open();

        if (datos.insertResultadoTransaccion(resultadoTransaccion)) {
            Intent intent = new Intent(getApplicationContext(), VoucherActivity.class);
            intent.putExtra("ResultadoTransaccion", resultadoTransaccion);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error al guardar Resultado Transaccion", Toast.LENGTH_SHORT).show();
        }
    }

    private String getHoraActual() {
        Date date = new Date();
        String formatoFecha = "HH:mm:ss";

        String fecha = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatoFecha);
            fecha = format.format(date);
        } catch (Exception exception) {
            Log.e("ERROR", exception.toString());
        }

        return fecha;
    }

    private String getFechaActual() {
        Date date = new Date();
        String formatoFecha = "yyyy-MM-dd";

        String fecha = "";
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatoFecha);
            fecha = format.format(date);
        } catch (Exception exception) {
            Log.e("ERROR", exception.toString());
        }

        return fecha;

    }


}