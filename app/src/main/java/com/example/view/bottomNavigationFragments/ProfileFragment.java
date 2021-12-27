package com.example.view.bottomNavigationFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bancoprueba.R;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.models.UserBankClient;
import com.example.model.utils.Datos;
import com.example.view.PersonalData;

public class ProfileFragment extends Fragment {

    private TextView saldoDispo;
    private UserBankClient userBankClient;
    private Datos datos;
    private CorrespondentBankUser bankCorresponsal;
    private LinearLayout datosPersonales;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_profile, container, false);
        userBankClient = new UserBankClient();
        datos = new Datos(getContext());
        bankCorresponsal = new CorrespondentBankUser();

        datos.open();
        SharedPreferences prefes = getContext().getSharedPreferences("datos", Context.MODE_PRIVATE);
        String correo = prefes.getString("email", "");
        bankCorresponsal = datos.getUserCorresponsal(correo);
        saldoDispo = vista.findViewById(R.id.saldoDispo);
        datosPersonales = vista.findViewById(R.id.datosPersonales);

        datosPersonales.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), PersonalData.class));
            Toast.makeText(getContext(), "Click datos personales", Toast.LENGTH_SHORT).show();
        });
        saldoDispo.setText(datos.formatMoneda(String.valueOf(bankCorresponsal.getSaldo())));
        datos.close();

        return vista;
    }
}