package com.example.view.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bancoprueba.R;
import com.example.model.Helpers.DBHelRepositoryImpl;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.utils.Datos;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText tiEmailAddress;
    TextInputEditText tiPasswordLogin;
    Button btnIniciarSecion;
    DBHelRepositoryImpl dbHelRepositoryImpl;
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

        dbHelRepositoryImpl = new DBHelRepositoryImpl(this);
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

        /** LoginActivity
         * <p>
         * Validar que el correo electronico tenga @ y .com
         *
         * Felipe Ardila
         *
         *
         */

        if (!(!tiEmailAddress.equals("") || !tiPasswordLogin.equals(""))) {
            tiPasswordLogin.setError("Los campos no pueden estar vacios");
            tiEmailAddress.setError("Los campos no pueden estar vacios");

        }
        if (!Datos.checkEmail(email)) {
            tiEmailAddress.setError("El correo no es valido");
        }

        //validar que la contraseña cumpla los requisitos
        if (!datos.validatePassword(tiPasswordLogin)) {
            tiPasswordLogin.setError("Contraseña muy debil");
        }

        //validar que el usuario corresponsal exista en la base de datos
        if (datos.validateUserCorrespondent(correspondentBankUser)) {

            if (checkBox.isChecked()) {
                String emailC = correspondentBankUser.getEmail();
                SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferencias.edit();
                editor.putString("email", emailC);
                editor.commit();
            }

            Intent intento = new Intent(this, ItemMenu.class);
            startActivity(intento);
            intento.putExtra("email", email);

        } else {
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
        }

    }
}