package com.example.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.CheckBox;

import com.example.model.models.CorrespondentBankUser;
import com.example.model.utils.Datos;


public class LoginInteractorImpl implements LoginInteractor {

    private CorrespondentBankUser bankUser;
    private Datos datos;


    @Override
    public void login(String username, String password, OnLoginFinishedListener listener, Context context) {

        new Handler().postDelayed(() -> {

            datos = new Datos(context);
            bankUser = new CorrespondentBankUser();
            bankUser.setEmail(username);
            bankUser.setPassword(password);
            datos.open();


            if (TextUtils.isEmpty(username)) {
                listener.onUsernameError();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                listener.onPasswordError();
                return;
            }

            if (datos.validateUserCorrespondent(bankUser)) {
                listener.onSuccess();

            } else {
                listener.onUsernameError();

            }
        }, 1000);

    }


}
