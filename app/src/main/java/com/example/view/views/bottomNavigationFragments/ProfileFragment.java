package com.example.view.views.bottomNavigationFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bancoprueba.R;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.models.UserBankClient;
import com.example.model.utils.Datos;

public class ProfileFragment extends Fragment {

    private TextView saldoDispo;
    private UserBankClient userBankClient;
    private Datos datos;
    private CorrespondentBankUser bankCorresponsal;

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
        saldoDispo.setText(String.valueOf("" + userBankClient.getSaldo()));
        datos.close();

        return vista;
    }
}