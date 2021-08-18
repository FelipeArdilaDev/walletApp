package com.example.bancoprueba;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Datos;
import com.example.Helpers.DBHelper;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText tiEmailAddress;
    TextInputEditText tiPasswordLogin;
    Button btnIniciarSecion;
    DBHelper dbHelper;
    CorrespondentBankUser correspondentBankUser;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTheme(R.style.Theme_BancoPrueba);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        btnIniciarSecion = findViewById(R.id.btnIniciarSecion);
        checkBox = findViewById(R.id.checkBox);

        dbHelper = new DBHelper(this);
        correspondentBankUser = new CorrespondentBankUser();
    }


    public void registrarCuenta(View v) {
        Intent intento = new Intent(this, RegistroActivity.class);
        startActivity(intento);
    }

    public void iniciarSesion(View v) {
        tiEmailAddress = findViewById(R.id.tiEmailAddress);
        String email = tiEmailAddress.getText().toString();
        correspondentBankUser.setEmail(email);
        tiPasswordLogin = findViewById(R.id.tiPasswordLogin);
        String password = tiPasswordLogin.getText().toString();
        correspondentBankUser.setPassword(password);

        Datos datos = new Datos(this);
        datos.open();

        if (Datos.checkEmail(email)) {
            Toast.makeText(this, "Correo valido", Toast.LENGTH_SHORT).show();
        } else {
            tiEmailAddress.setError("El correo no es valido");
        }

        if (datos.validatePassword(tiPasswordLogin)) {
            Toast.makeText(getApplicationContext(), "Contraseña segura", Toast.LENGTH_SHORT).show();


        } else {
            tiPasswordLogin.setError("Contraseña muy debil");
        }

        if (datos.validateUserCorrespondent(correspondentBankUser)) {
            Intent intento = new Intent(this, MenuActivity.class);
            startActivity(intento);
            intento.putExtra("email", email);
            //datos.guardarDato(correspondentBankUser);
            Toast.makeText(getApplicationContext(), "Has iniciado sesion", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

        if (checkBox.isChecked()) {
            String emailC = correspondentBankUser.getEmail();
            SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("email", emailC);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Datos grabados", Toast.LENGTH_SHORT).show();

        } else {

        }
    }
}