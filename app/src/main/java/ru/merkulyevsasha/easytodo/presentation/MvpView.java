package ru.merkulyevsasha.easytodo.presentation;


public interface MvpView {

    void showProgress();
    void hideProgress();

    void showMessage(int message);


}
