package com.example.view.views.HistorialFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.model.Helpers.utils.Datos;
import com.example.bancoprueba.R;
import com.example.view.views.HistorialTransaccionesAdaptador;
import com.example.model.Helpers.models.ResultadoTransaccion;

import java.util.ArrayList;

public class DepositoFragment extends Fragment {
    RecyclerView rvDeposito;
    Datos datos;



    public DepositoFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datos = new Datos(getContext());




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_deposito, container, false);
        rvDeposito = vista.findViewById(R.id.rvDeposito);

        ArrayList<ResultadoTransaccion> transaccions = datos.getAllResultadosTransaccion();
        HistorialTransaccionesAdaptador adaptador = new HistorialTransaccionesAdaptador(transaccions);
        rvDeposito.setAdapter(adaptador);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvDeposito.setLayoutManager(manager);
        adaptador.notifyDataSetChanged();
        return vista;



    }
}