package com.example.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.model.models.UserBankClient;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.utils.Datos;
import com.example.model.Helpers.DBHelRepositoryImpl;
import com.example.bancoprueba.R;
import com.example.model.models.ResultadoTransaccion;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RetiroActivity extends AppCompatActivity {

    TextInputEditText timontoRetiro;
    TextInputEditText numberDocument;
    TextInputEditText pinRetiro;
    TextInputEditText pinRetiroConfirm;
    Button retirarDinero;
    DBHelRepositoryImpl dbHelRepositoryImpl;
    Datos datos;
    UserBankClient userBankClient;
    CorrespondentBankUser correspondentBankUser;

    String correoCorresponsal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);


        timontoRetiro = findViewById(R.id.timontoRetiro);
        numberDocument = findViewById(R.id.numberDocument);
        pinRetiro = findViewById(R.id.pinRetiro);
        pinRetiroConfirm = findViewById(R.id.pinRetiroConfirm);


        dbHelRepositoryImpl = new DBHelRepositoryImpl(this);
        datos = new Datos(this);
        userBankClient = new UserBankClient();
        correspondentBankUser = new CorrespondentBankUser();


        retirarDinero = findViewById(R.id.retirarDinero);
        retirarDinero.setOnClickListener(v -> {

            String id = numberDocument.getText().toString();
            userBankClient.setId(id);

            String pin = pinRetiro.getText().toString();
            userBankClient.setPassword(pin);


            datos.open();

            String pinConfirm = pinRetiroConfirm.getText().toString();
            String pinR = pinRetiro.getText().toString();
            userBankClient.setPassword(pinR);


            int retiroMonto = Integer.parseInt(timontoRetiro.getText().toString());
            userBankClient.setSaldo(retiroMonto);

            int montoRetiro;
            montoRetiro = (retiroMonto);


            int nuevoSaldoC;

            // validar si el usuario cliente existe
            if (datos.validateUserClient(userBankClient)) {
                Toast.makeText(getApplicationContext(), "Usuario encontrado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RetiroActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }


            // validar si el confimrar pin coincide con el pin de la cuenta
            if (pin.equals(pinConfirm)) {
                Toast.makeText(RetiroActivity.this, "El pin Coincide", Toast.LENGTH_SHORT).show();
            } else {
                pinRetiroConfirm.setError("pin no coincide");
            }

            // validar que el usuario tiene saldo suficiente
            if (datos.validarMontoRetiro(userBankClient)) {
                datos.open();

                //actualizar usuario cliente
                datos.updateUserBank(userBankClient);
                SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);

                //Traer del shared
                correoCorresponsal = prefe.getString("email", "");
                correspondentBankUser = datos.getUserCorresponsal(correoCorresponsal);

                //operacion para el retiro
                nuevoSaldoC = correspondentBankUser.getSaldo() + 2000 + montoRetiro;
                correspondentBankUser.setSaldo(nuevoSaldoC);

                //actualizar usuario corresponsal
                datos.updateUserCorresponsal(correspondentBankUser);
                datos.close();

                //Crear objeto ResultadoTransaccion
                crearResultadoTransaccion();

            } else {
                timontoRetiro.setError("Saldo insuficiente");
            }
        });


    }

    private void crearResultadoTransaccion() {
        ResultadoTransaccion resultadoTransaccion = new ResultadoTransaccion();
        resultadoTransaccion.setTipoTransaccion("RETIRO");
        resultadoTransaccion.setFecha(getFechaActual());
        resultadoTransaccion.setHora(getHoraActual());
        resultadoTransaccion.setMonto(timontoRetiro.getText().toString());
        resultadoTransaccion.setCuentaPrincipal(numberDocument.getText().toString());
        resultadoTransaccion.setCuentaCorresponsal(correoCorresponsal);

        datos.open();

        if (datos.insertResultadoTransaccion(resultadoTransaccion)) {
            Intent intent = new Intent(getApplicationContext(), VoucherActivity.class);
            intent.putExtra("ResultadoTransaccion", resultadoTransaccion);
            startActivity(intent);
        } else {
            showCustomDialog();
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

    public void showCustomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString()
                    + " Clicked", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);


    }
}
