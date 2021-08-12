package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Corresponsal.CorrespondentBankUser;
import com.example.Datos;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity {
    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText password;
    TextInputEditText passwordConfirm;
    TextInputEditText phone;
    TextInputEditText id;
    Button crearCuenta;
    Datos data;


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    ".{4,10}" +
                    "$");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();

        name = findViewById(R.id.nameUser);
        email = findViewById(R.id.email);
        password = findViewById(R.id.passwordRegister);
        passwordConfirm = findViewById(R.id.tiPassword);
        phone = findViewById(R.id.numberPhone);
        id = findViewById(R.id.idRegister);
        crearCuenta = findViewById(R.id.retirarDinero);

        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CorrespondentBankUser usuario;
                usuario = new CorrespondentBankUser(
                        id.getText().toString(),
                        name.getText().toString(),
                        email.getText().toString(),
                        password.getText().toString(),
                        phone.getText().toString()
                );
                if (validatePassword(password)){
                    Toast.makeText(RegistroActivity.this, "Contraseña Segura", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegistroActivity.this, "Contraseña invalida", Toast.LENGTH_SHORT).show();
                    password.setError("Contraseña invalida");
                }
                if (passwordConfirm.equals(passwordConfirm)) {
                    data = new Datos(getApplicationContext());
                    data.open();
                    data.insertUsuario(usuario);
                    Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegistroActivity.this, "Se agrego el usuario", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "Error al registrarse contrasñas no coinciden", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private boolean validatePassword(TextInputEditText passwordRegister) {
        String passwordInput = passwordRegister.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            passwordRegister.setError("El campo no puede estar vacío");
            return false;
        }


        else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            passwordRegister.setError("La contraseña es demasiado débil");
            return false;
        } else {

            return true;
        }

    }



}