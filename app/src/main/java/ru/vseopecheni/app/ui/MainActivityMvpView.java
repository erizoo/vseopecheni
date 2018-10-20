package ru.vseopecheni.app.ui;

import ru.vseopecheni.app.ui.base.MvpView;
import ru.vseopecheni.app.ui.model.SaveInfo;

public interface MainActivityMvpView extends MvpView {

    void onInfoSaved(SaveInfo saveInfo);

    void onImagesSaved();
}
