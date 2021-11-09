package com.example.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.bancoprueba.R;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.utils.Datos;
import com.example.view.views.bottomNavigationFragments.HistoryFragment;
import com.example.view.views.bottomNavigationFragments.HomeFragment;
import com.example.view.views.bottomNavigationFragments.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class ItemMenu extends AppCompatActivity {

    private TextView tvSaldo;
    private TextView tvName;
    private CorrespondentBankUser correspondentBankUser;
    private Datos datos;
    private View parent_view;
    private View backDrop;
    private boolean rotate = false;
    private View lytRetirarDinero;
    private View lytDeposito;
    private View lytCrearUsuario;
    private View lytConsultarSaldo;
    private MeowBottomNavigation bnvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_menu);
        getSupportActionBar().hide();

        bnvMain = findViewById(R.id.bnvMain);
        bnvMain.add(new MeowBottomNavigation.Model(1, R.drawable.ic_history));
        bnvMain.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bnvMain.add(new MeowBottomNavigation.Model(3, R.drawable.ic_person));

        bnvMain.show(2, true);

        bnvMain.setOnClickMenuListener(model -> {
            switch (model.getId()) {
                case 1:
                    replace(new HistoryFragment());
                    break;
                case 3:
                    replace(new ProfileFragment());
                    break;
                default:
                    replace(new HomeFragment());
            }
            return null;
        });

        correspondentBankUser = new CorrespondentBankUser();
        datos = new Datos(this);

        parent_view = findViewById(android.R.id.content);
        backDrop = findViewById(R.id.backDrop);

        final FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        lytRetirarDinero = findViewById(R.id.lytRetirarDinero);
        lytDeposito = findViewById(R.id.lytDeposito);
        lytCrearUsuario = findViewById(R.id.lytCrearUsuario);
        lytConsultarSaldo = findViewById(R.id.lytConsultarSaldo);

        ViewAnimation.initShowOut(lytRetirarDinero);
        ViewAnimation.initShowOut(lytDeposito);
        ViewAnimation.initShowOut(lytCrearUsuario);
        ViewAnimation.initShowOut(lytConsultarSaldo);

        backDrop.setVisibility(View.GONE);

        fabAdd.setOnClickListener(this::toggleFabMode);

        backDrop.setOnClickListener(v -> toggleFabMode(fabAdd));

    }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        String user = prefe.getString("email", "");
        correspondentBankUser = datos.getUserCorresponsal(user);

        Log.d("user", correspondentBankUser.getEmail() + correspondentBankUser.getSaldo());

        datos.open();
        tvName = findViewById(R.id.tvUsuario);
        tvName.setText("" + correspondentBankUser.getName());
        tvSaldo = findViewById(R.id.tvSaldoUsuario);
        tvSaldo.setText(formatMoneda(String.valueOf(correspondentBankUser.getSaldo())));
        datos.close();
    }

    //iniciar actividad de registrar cuenta de cliente
    public void cardRegisterBank(View v) {
        startActivity(new Intent(this, RegisterNewBankActivity.class));
    }

    //iniciar actividad de retirar dinero usuario cliente
    public void cardRetirarMonto(View v) {
        startActivity(new Intent(this,
                RetiroActivity.class));
    }

    //iniciar actividad de depositar dinero de usuario cliente
    public void cardDepositarDinero(View v) {
        startActivity(new Intent(this, DepositoActivity.class));
    }

    //iniciar actividad de historial de consultar saldo
    public void cardConsultaDeSaldo(View v) {
        startActivity(new Intent(this, ConsultarSaldoActivity.class));
    }


    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lytRetirarDinero);
            ViewAnimation.showIn(lytDeposito);
            ViewAnimation.showIn(lytCrearUsuario);
            ViewAnimation.showIn(lytConsultarSaldo);
            backDrop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lytRetirarDinero);
            ViewAnimation.showOut(lytDeposito);
            ViewAnimation.showOut(lytCrearUsuario);
            ViewAnimation.showOut(lytConsultarSaldo);
            backDrop.setVisibility(View.GONE);
        }
    }

    private String formatMoneda(String monto) {
        DecimalFormat format = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.ITALIAN));
        double d = 0.0;
        d = Double.parseDouble(monto);
        return "$ " + format.format(d);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


}