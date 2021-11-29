package com.example.view.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.bancoprueba.R;
import com.example.model.Helpers.DBHelRepositoryImpl;
import com.example.model.LoginInteractorImpl;
import com.example.model.models.CorrespondentBankUser;
import com.example.model.utils.Datos;
import com.example.presenter.LoginPresenter;
import com.example.presenter.LoginPresenterImpl;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private TextInputEditText tiEmailAddress;
    private TextInputEditText tiPasswordLogin;
    private DBHelRepositoryImpl dbHelRepositoryImpl;
    private CheckBox checkBox;
    private LoginPresenter presenter;
    private ProgressBar progressBar;
    private Datos datos;
    private LinearLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_BancoPrueba);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tiPasswordLogin       = findViewById(R.id.tiPasswordLogin);
        tiEmailAddress        = findViewById(R.id.tiEmailAddress);
        progressBar           = findViewById(R.id.progressBar);
        checkBox              = findViewById(R.id.checkBox);
        menu                  = findViewById(R.id.menu);

        presenter             = new LoginPresenterImpl(this, new LoginInteractorImpl());
        dbHelRepositoryImpl   = new DBHelRepositoryImpl(this);

        findViewById(R.id.btnIniciarSecion).setOnClickListener(this);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onDestroy();
    }

    public void registrarCuenta(View v) {
        startActivity(new Intent(this,
                RegistroActivity.class));
    }

    /** LoginActivity
     * <p>
     * Validar que el correo electronico tenga @ y .com
     *
     * Felipe Ardila
     *
     *
     */

    @Override
    public void onClick(View v) {

        datos = new Datos(this);

        String email = tiEmailAddress.getText().toString();
        String password = tiPasswordLogin.getText().toString();


        if (!(!email.equals("") || !password.equals(""))) {
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

        if (checkBox.isChecked()) {
            SharedPreferences.Editor editor = getPreferences();
            editor.putString("email", email);
            editor.commit();
        }

        presenter.validateCredentials(tiEmailAddress.getText().toString(),
                tiPasswordLogin.getText().toString(), this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUserNameError() {
        showCustomDialog();

    }

    @Override
    public void setPasswordError() {
        showCustomDialog();

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void navigateToHome() {
        String email = tiEmailAddress.getText().toString();
        Intent intento = new Intent(this, ItemMenu.class);
        startActivity(intento);
        intento.putExtra("email", email);


    }

    public SharedPreferences.Editor getPreferences() {
        SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
        return preferencias.edit();
    }


    @Override
    public void showCustomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_warning);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString()
                    + " Clicked", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);


    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);



    }
}