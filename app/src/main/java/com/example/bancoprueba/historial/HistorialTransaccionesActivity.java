package com.example.bancoprueba.historial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.Datos;
import com.example.bancoprueba.R;
import com.example.modelos.ResultadoTransaccion;

import java.util.ArrayList;

public class HistorialTransaccionesActivity extends AppCompatActivity {

    RecyclerView rvTransacciones;
    Datos datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_transacciones);

        rvTransacciones = findViewById(R.id.rvTransacciones);
        datos = new Datos(this);

        datos.open();
        ArrayList<ResultadoTransaccion> transaccions = datos.getAllResultadosTransaccion();

        HistorialTransaccionesAdaptador adaptador = new HistorialTransaccionesAdaptador(transaccions);

        rvTransacciones.setAdapter(adaptador);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvTransacciones.setLayoutManager(manager);
        adaptador.notifyDataSetChanged();

    }
}