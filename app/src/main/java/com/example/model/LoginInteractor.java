package com.example.model;

import android.content.Context;

public interface LoginInteractor {

    interface OnLoginFinishedListener{

        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    void login(String username, String password, OnLoginFinishedListener listener, Context context);

}
