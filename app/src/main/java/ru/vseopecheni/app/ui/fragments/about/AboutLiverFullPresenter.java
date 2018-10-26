package ru.vseopecheni.app.ui.fragments.about;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface AboutLiverFullPresenter <V extends AboutLiverFullMvpView> extends MvpPresenter<V> {

    void getFull(String number);

}
