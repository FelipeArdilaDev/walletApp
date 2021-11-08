package com.example.model.models;

import android.content.ContentValues;

import com.example.model.Helpers.SQLConstants;

import java.io.Serializable;

public class ResultadoTransaccion implements Serializable {

    public String id;
    public String tipoTransaccion;
    public String fecha;
    public String hora;
    public String monto;
    public String cuentaPrincipal;
    public String cuentaSecundaria;
    public String cuentaCorresponsal;

    public ResultadoTransaccion() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getCuentaPrincipal() {
        return cuentaPrincipal;
    }

    public void setCuentaPrincipal(String cuentaPrincipal) {
        this.cuentaPrincipal = cuentaPrincipal;
    }

    public String getCuentaSecundaria() {
        return cuentaSecundaria;
    }

    public void setCuentaSecundaria(String cuentaSecundaria) {
        this.cuentaSecundaria = cuentaSecundaria;
    }

    public String getCuentaCorresponsal() {
        return cuentaCorresponsal;
    }

    public void setCuentaCorresponsal(String cuentaCorresponsal) {
        this.cuentaCorresponsal = cuentaCorresponsal;
    }

    public ContentValues toValues() {
        ContentValues contentValues = new ContentValues(8);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_ID, id);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_TIPO, tipoTransaccion);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_FECHA, fecha);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_HORA, hora);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_MONTO, monto);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_CUENTAPRINCIPAL, cuentaPrincipal);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_CUENTASECUNDARIA, cuentaSecundaria);
        contentValues.put(SQLConstants.COLUMN_TRANSACCION_CUENTACORRESPONSAL, cuentaCorresponsal);
        return contentValues;
    }
}
