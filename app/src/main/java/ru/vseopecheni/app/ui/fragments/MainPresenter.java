package ru.vseopecheni.app.ui.fragments;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface MainPresenter <V extends MainMvpView> extends MvpPresenter<V> {

    void saveAll();

    void saveProduct();

    void getFull(String s);

    void saveMenuForWeek();

    void getProducts();

    void saveRecipes();
}
