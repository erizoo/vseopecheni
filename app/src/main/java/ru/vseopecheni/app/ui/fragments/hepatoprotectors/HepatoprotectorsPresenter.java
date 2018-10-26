package ru.vseopecheni.app.ui.fragments.hepatoprotectors;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface HepatoprotectorsPresenter <V extends HepatoprotectorsMvpView> extends MvpPresenter<V> {

    void getFull(String s);

}
