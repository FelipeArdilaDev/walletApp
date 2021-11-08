package com.example.view.views.bottomNavigationFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancoprueba.R;
import com.example.model.models.ResultadoTransaccion;
import com.example.model.utils.Datos;
import com.example.view.views.HistorialTransaccionesAdaptador;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    RecyclerView rvTransacciones;
    Datos datos;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.activity_historial_transacciones, container, false);

        rvTransacciones = vista.findViewById(R.id.rvTransacciones);

        datos = new Datos(getContext());

        datos.open();
        ArrayList<ResultadoTransaccion> transaccions = datos.getAllResultadosTransaccion();

        HistorialTransaccionesAdaptador adaptador = new HistorialTransaccionesAdaptador(transaccions);

        rvTransacciones.setAdapter(adaptador);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvTransacciones.setLayoutManager(manager);
        adaptador.notifyDataSetChanged();

        return vista;


    }


}