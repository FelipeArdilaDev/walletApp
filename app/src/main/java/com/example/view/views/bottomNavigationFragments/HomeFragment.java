package com.example.view.views.bottomNavigationFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bancoprueba.R;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.utils.Datos;
import com.example.view.views.ViewAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

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

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefe = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String user = prefe.getString("email", "");
        correspondentBankUser = datos.getUserCorresponsal(user);

        Log.d("user", correspondentBankUser.getEmail() + correspondentBankUser.getSaldo());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_home, container, false);

        correspondentBankUser = new CorrespondentBankUser();
        datos = new Datos(getContext());

        parent_view = vista.findViewById(android.R.id.content);
        backDrop = vista.findViewById(R.id.backDrop);

        final FloatingActionButton fabAdd = (FloatingActionButton) vista.findViewById(R.id.fabAdd);

        lytRetirarDinero = vista.findViewById(R.id.lytRetirarDinero);
        lytDeposito = vista.findViewById(R.id.lytDeposito);
        lytCrearUsuario = vista.findViewById(R.id.lytCrearUsuario);
        lytConsultarSaldo = vista.findViewById(R.id.lytConsultarSaldo);

        ViewAnimation.initShowOut(lytRetirarDinero);
        ViewAnimation.initShowOut(lytDeposito);
        ViewAnimation.initShowOut(lytCrearUsuario);
        ViewAnimation.initShowOut(lytConsultarSaldo);

        backDrop.setVisibility(View.GONE);

        fabAdd.setOnClickListener(v -> toggleFabMode(v));

        backDrop.setOnClickListener(v -> toggleFabMode(fabAdd));

        SharedPreferences prefe = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String user = prefe.getString("email", "");
        correspondentBankUser = datos.getUserCorresponsal(user);

        Log.d("user", correspondentBankUser.getEmail() + correspondentBankUser.getSaldo());
        datos.open();
        tvName = vista.findViewById(R.id.tvUsuario);
        tvName.setText("" + correspondentBankUser.getName());
        tvSaldo = vista.findViewById(R.id.tvSaldoUsuario);
        tvSaldo.setText("" + correspondentBankUser.getSaldo());
        datos.close();


        return vista;
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

}