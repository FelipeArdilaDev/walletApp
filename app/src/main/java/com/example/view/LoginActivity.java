package com.example.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.bancoprueba.R;
import com.example.bancoprueba.databinding.ActivityLoginBinding;
import com.example.model.Helpers.DBHelRepository;
import com.example.model.LoginInteractorImpl;
import com.example.model.utils.Datos;
import com.example.presenter.LoginPresenter;
import com.example.presenter.LoginPresenterImpl;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private LoginPresenter presenter;
    private Datos datos;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_BancoPrueba);
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        presenter = new LoginPresenterImpl(this, new LoginInteractorImpl());
        DBHelRepository dbHelRepository = new DBHelRepository(this);

        binding.menu.setOnClickListener(this::hideKeyboard);

        binding.btnRegistrarCuenta.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), RegistroActivity.class)));

        binding.btnIniciarSecion.setOnClickListener(v -> {

            datos = new Datos(this);

            String email = binding.tiEmailAddress.getText().toString();
            String password = binding.tiPasswordLogin.getText().toString();


            if (!(!email.equals("") || !password.equals(""))) {
                binding.tiPasswordLogin.setError("Los campos no pueden estar vacios");
                binding.tiEmailAddress.setError("Los campos no pueden estar vacios");
            }

            if (!Datos.checkEmail(email)) {
                binding.tiEmailAddress.setError("El correo no es valido");
            }

            //validar que la contraseña cumpla los requisitos
            if (!datos.validatePassword(binding.tiPasswordLogin)) {
                binding.tiPasswordLogin.setError("Contraseña muy debil");
            }

            if (binding.checkBox.isChecked()) {
                SharedPreferences.Editor editor = getPreferences();
                editor.putString("email", email);
                editor.commit();
            }

            presenter.validateCredentials(binding.tiEmailAddress.getText().toString(),
                    binding.tiPasswordLogin.getText().toString(), this);

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onDestroy();
    }

    /**
     * LoginActivity
     * <p>
     * Validar que el correo electronico tenga @ y .com
     * <p>
     * Felipe Ardila
     */

    @Override
    public void onClick(View v) {
    }

    @Override
    public void showProgress() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
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
        String email = binding.tiEmailAddress.getText().toString();
        Intent intento = new Intent(this, MenuActivity.class);
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
        dialog.setContentView(R.layout.customized_dialog);
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