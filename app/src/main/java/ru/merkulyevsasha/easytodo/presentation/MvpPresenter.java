package ru.merkulyevsasha.easytodo.presentation;


public interface MvpPresenter {

    void onStop();
    void onStart(MvpView view);

}
