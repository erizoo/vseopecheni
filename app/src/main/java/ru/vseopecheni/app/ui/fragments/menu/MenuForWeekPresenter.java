package ru.vseopecheni.app.ui.fragments.menu;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface MenuForWeekPresenter <V extends MenuForWeekMvpView> extends MvpPresenter<V> {

    void getMenuForWeek();

}
