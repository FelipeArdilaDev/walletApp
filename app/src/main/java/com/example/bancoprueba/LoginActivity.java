package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Corresponsal.UserBank;
import com.example.Corresponsal.UserDataBase;
import com.example.Datos;
import com.example.SQLConstants;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText tiEmailAddress;
    TextInputEditText tiPasswordLogin;
    Datos datos;
    Button btnIniciarSecion;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{4,20}" +
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        setTheme(R.style.Theme_BancoPrueba);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        btnIniciarSecion = findViewById(R.id.btnIniciarSecion);
    }

    public void registrarCuenta(View v) {
        Intent intento = new Intent(this, RegistroActivity.class);
        startActivity(intento);

    }

    public void iniciarSesion(View v){
        tiEmailAddress = findViewById(R.id.tiEmailAddress);
        String email = tiEmailAddress.getText().toString();
        tiPasswordLogin = findViewById(R.id.tiPasswordLogin);
        UserDataBase userDataBase = new UserDataBase();
        Datos datos = new Datos(this);

        datos.open();
        Intent intento = new Intent(this, MenuActivity.class);
        startActivity(intento);
        intento.putExtra("email", email);


        if (datos.checkEmail(email)){
            Toast.makeText(this, "Correo valido", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Correo no valido", Toast.LENGTH_SHORT).show();
            tiEmailAddress.setError("El correo no es valido");
        }


        if (datos.validatePassword(tiPasswordLogin)){
            Toast.makeText(this, "Contraseña valida", Toast.LENGTH_SHORT).show();
        } else {
            tiPasswordLogin.setError("Contraseña invalida");
        }

        if (datos.validateUser(userDataBase)){

            Toast.makeText(this, "Usuario correcto", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
        }
    }





}