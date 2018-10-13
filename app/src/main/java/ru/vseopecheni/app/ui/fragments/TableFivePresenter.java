package ru.vseopecheni.app.ui.fragments;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface TableFivePresenter <V extends TableFiveMvpView> extends MvpPresenter<V> {

    void getProducts(String drinksId);

}
