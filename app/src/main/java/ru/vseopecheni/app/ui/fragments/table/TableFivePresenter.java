package ru.vseopecheni.app.ui.fragments.table;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface TableFivePresenter <V extends TableFiveMvpView> extends MvpPresenter<V> {

    void getProducts(String drinksId);

}
