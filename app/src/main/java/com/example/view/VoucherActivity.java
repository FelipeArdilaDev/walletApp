package com.example.view;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.bancoprueba.R;
import com.example.bancoprueba.databinding.ActivityVoucherBinding;
import com.example.bancoprueba.databinding.CustomizedDialogBinding;
import com.example.model.models.ResultadoTransaccion;

public class VoucherActivity extends AppCompatActivity {

    private ActivityVoucherBinding binding;
    private CustomizedDialogBinding customsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVoucherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEnviarWhatsapp.setOnClickListener(v -> {

            boolean installed = isAppInstalled("com.whatsapp");
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

        binding.btnSalir.setOnClickListener(v -> startActivity(new Intent(this, MenuActivity.class)));

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

    public void showCustomDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before

        this.customsDialog = CustomizedDialogBinding.inflate(getLayoutInflater());

        LayoutParams lp = new LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WRAP_CONTENT;
        lp.height = WRAP_CONTENT;

        dialog.setContentView(this.customsDialog.getRoot(), lp);
        dialog.setCancelable(true);
        customsDialog.title.setText(R.string.app_not_install);
        customsDialog.content.setText(R.string.muchi_texto);
        customsDialog.btClose.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString()
                    + " Clicked", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    public boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}


