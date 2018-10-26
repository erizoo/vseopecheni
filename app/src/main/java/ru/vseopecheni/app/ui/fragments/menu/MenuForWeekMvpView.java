package ru.vseopecheni.app.ui.fragments.menu;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseMenu;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.ui.base.MvpView;

public interface MenuForWeekMvpView extends MvpView {

    void onMenuForWeekUpdated(ResponseMenuForWeek responseMenuForWeek);

}
