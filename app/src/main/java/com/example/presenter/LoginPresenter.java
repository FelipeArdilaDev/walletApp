package com.example.presenter;


import android.content.Context;

public interface LoginPresenter {

    void validateCredentials(String username, String password, Context context);

    void onDestroy();
}
