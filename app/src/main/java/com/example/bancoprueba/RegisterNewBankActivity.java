package com.example.bancoprueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.Corresponsal.UserBank;
import com.example.Corresponsal.UserDataBase;
import com.example.Datos;
import com.example.SQLConstants;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterNewBankActivity extends AppCompatActivity {

    TextInputEditText id;
    TextInputEditText saldoInicial;
    TextInputEditText numberCount;
    TextInputEditText password;
    TextInputEditText passwordConfirm;
    Datos data;
    Button crearCuentaBank;
    UserDataBase userDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_new_bank);

        id = findViewById(R.id.idRegister);
        saldoInicial = findViewById(R.id.saldoInicial);
        numberCount = findViewById(R.id.numberCount);
        password = findViewById(R.id.passwordRegister);
        passwordConfirm = findViewById(R.id.tiPinConfirm);
        crearCuentaBank = findViewById(R.id.crearCuentaBank);

        crearCuentaBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password = findViewById(R.id.passwordRegister);
                String pin = password.getText().toString();
                passwordConfirm = findViewById(R.id.tiPinConfirm);
                String pinConfirm = passwordConfirm.getText().toString();

                UserBank usuariosBank = new UserBank(
                        id.getText().toString(),
                        Integer.valueOf(saldoInicial.getText().toString()),
                        numberCount.getText().toString(),
                        password.getText().toString()
                );


                if(pin.equals(pinConfirm)) {
                    data = new Datos(getApplicationContext());
                    data.open();
                    data.inserUsuarioBank(usuariosBank);
                    Toast.makeText(RegisterNewBankActivity.this, "Se agrego el usuario", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterNewBankActivity.this, "El pin coincide correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterNewBankActivity.this, "El pin no coincide", Toast.LENGTH_SHORT).show();
                    passwordConfirm.setError("El pin no coincide");

                }

            }
        });
    }
}