package ru.vseopecheni.app.ui.fragments.treat;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface HowToTreatFullPresenter <V extends HowToTreatFullMvpView> extends MvpPresenter<V> {

    void getInfoAboutTreat(String number);

}
