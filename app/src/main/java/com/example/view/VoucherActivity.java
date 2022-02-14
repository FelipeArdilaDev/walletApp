package com.example.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.example.bancoprueba.R;
import com.example.bancoprueba.databinding.ActivityVoucherBinding;
import com.example.model.models.ResultadoTransaccion;
import com.example.model.utils.Datos;

public class VoucherActivity extends AppCompatActivity {

    private ActivityVoucherBinding binding;
    Datos datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEnviarWhatsapp.setOnClickListener(v -> {

            boolean installed = datos.isAppInstalled("com.whatsapp");
            if (installed) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, binding.textViewRecibo.getText().toString());
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                startActivity(sendIntent);
            } else {
                showCustomDialog();

            }


        });

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

        binding.textViewRecibo.setText(builder.toString());
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Realmente desea salir sin enviar los datos de la transaccion?");
        builder.setPositiveButton("Confirmar", (dialog, which) -> {
            Toast.makeText(VoucherActivity.this, "Has finalizado la transaccion sin enviar", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
        builder.setNegativeButton("cancelar", (dialog, which) ->
                Toast.makeText(VoucherActivity.this, "Continue con la transaccion", Toast.LENGTH_SHORT).show());
        builder.create().show();
    }

    public void salirMenu(){
        startActivity(new Intent(this, MainActivity.class));
    }


    public void showCustomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setTitle("La aplicación no está instalada actualmente en su teléfono");
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


