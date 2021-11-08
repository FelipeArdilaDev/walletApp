package com.example.view.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancoprueba.R;
import com.example.model.models.ResultadoTransaccion;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class HistorialTransaccionesAdaptador extends RecyclerView.Adapter<HistorialTransaccionesAdaptador.ViewHolder> {

    private ArrayList<ResultadoTransaccion> transaccions;

    public HistorialTransaccionesAdaptador(ArrayList<ResultadoTransaccion> transaccions) {
        this.transaccions = transaccions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResultadoTransaccion transaccion = transaccions.get(position);
        holder.tvTituloTrx.setText(transaccion.getTipoTransaccion());
        holder.tvMontoTrx.setText(formatMoneda(transaccion.getMonto()));
        holder.tvFechaTrx.setText(transaccion.getFecha());
    }

    @Override
    public int getItemCount() {
        return transaccions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTituloTrx;
        TextView tvMontoTrx;
        TextView tvFechaTrx;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTituloTrx = itemView.findViewById(R.id.tvTituloTrx);
            tvMontoTrx = itemView.findViewById(R.id.tvMontoTrx);
            tvFechaTrx = itemView.findViewById(R.id.tvFechaTrx);

        }
    }

    private String formatMoneda(String monto) {
        DecimalFormat format = new DecimalFormat("#,###", new DecimalFormatSymbols(Locale.ITALIAN));
        double d = 0.0;
        d = Double.parseDouble(monto);
        return "$ " + format.format(d);
    }
}
