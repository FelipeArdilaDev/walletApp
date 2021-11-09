package com.example.view.views;

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUserNameError();

    void setPasswordError();

    void navigateToHome();

    void showCustomDialog();
}
