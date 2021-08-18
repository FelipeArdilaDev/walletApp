package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.modelos.ResultadoTransaccion;

public class VoucherRetiroActivity extends AppCompatActivity {

    TextView textViewRecibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_retiro);
        getSupportActionBar().hide();

        textViewRecibo = findViewById(R.id.textViewRecibo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ResultadoTransaccion transaccion = (ResultadoTransaccion) getIntent().getSerializableExtra("ResultadoTransaccion");
            crearRecibo(transaccion);
        }
    }

    private void crearRecibo(ResultadoTransaccion transaccion) {
        StringBuilder builder = new StringBuilder();

        builder.append("TRANSACCION EXITOSA");
        builder.append("\n"); //Codigo Regex para salto de linea
        builder.append("Fecha: ");
        builder.append(transaccion.getFecha());
        builder.append("\n");
        builder.append("Hora: ");
        builder.append(transaccion.getHora());
        builder.append("\n");


        textViewRecibo.setText(builder.toString());
    }
}