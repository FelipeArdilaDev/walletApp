package com.example.view.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bancoprueba.R;
import com.example.model.Helpers.models.ResultadoTransaccion;

public class VoucherActivity extends AppCompatActivity {

    TextView textViewRecibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
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

        builder.append("\n");
        builder.append("TRANSACCION EXITOSA");
        builder.append("\n"); //Codigo Regex para salto de linea
        builder.append("\n");
        builder.append("Fecha: ");
        builder.append(transaccion.getFecha());
        builder.append("\n");
        builder.append("Hora: ");
        builder.append(transaccion.getHora());
        builder.append("\n");
        builder.append("Corresponsal: ");
        builder.append(transaccion.getCuentaCorresponsal());
        builder.append("\n");
        builder.append("Cuenta principal: ");
        builder.append(transaccion.getCuentaPrincipal());
        builder.append("\n");
        builder.append("Tipo de transaccion: ");
        builder.append(transaccion.getTipoTransaccion());
        builder.append("\n");
        builder.append("Monto de la transaccion: ");
        builder.append(transaccion.getMonto());

        textViewRecibo.setText(builder.toString());
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Realmente desea salir sin enviar los datos de la transaccion?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(VoucherActivity.this, "Has finalizado la transaccion sin enviar", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(VoucherActivity.this, "Continue con la transaccion", Toast.LENGTH_SHORT).show();
            }
        });
        builder.create().show();
    }

    public void salirMenu(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void enviarWhatsapp(View v) {
        Intent sendIntent = new Intent(); sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,  textViewRecibo.getText().toString());
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);

    }
}