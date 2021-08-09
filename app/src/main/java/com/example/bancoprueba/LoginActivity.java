package com.example.bancoprueba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Corresponsal.UserBank;
import com.example.Corresponsal.UserDataBase;
import com.example.Datos;
import com.example.Helpers.DBHelper;
import com.example.SQLConstants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.BreakIterator;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText tiEmailAddress;
    TextInputEditText tiPasswordLogin;
    Button btnIniciarSecion;
    DBHelper dbHelper;


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

        dbHelper = new DBHelper(this);
    }


    public void registrarCuenta(View v) {
        Intent intento = new Intent(this, RegistroActivity.class);
        startActivity(intento);
    }

    public void iniciarSesion(View v){
        tiEmailAddress = findViewById(R.id.tiEmailAddress);
        String email = tiEmailAddress.getText().toString();
        tiPasswordLogin = findViewById(R.id.tiPasswordLogin);
        String password = tiPasswordLogin.getText().toString();

        Datos datos = new Datos(this);
        UserDataBase userDataBase = new UserDataBase();
        datos.open();

        if (Datos.checkEmail(email)){
            Toast.makeText(this, "Correo valido", Toast.LENGTH_SHORT).show();
        } else {
            tiEmailAddress.setError("El correo no es valido");
        }


        if (datos.validatePassword(tiPasswordLogin)){
            Toast.makeText(getApplicationContext(), "Contraseña segura", Toast.LENGTH_SHORT).show();


        } else {
            tiPasswordLogin.setError("Contraseña muy debil");
        }

        if (dbHelper.validateUser(email,password)){
            Intent intento = new Intent(this, MenuActivity.class);
            startActivity(intento);
            intento.putExtra("email", email);
            Toast.makeText(getApplicationContext(), "Has iniciado sesion", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
        }
    }





}