package ru.vseopecheni.app.ui.fragments.hepatoprotectors;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.ui.base.MvpView;

public interface HepatoprotectorsMvpView extends MvpView {

    void onFullInfoUpdated(List<ResponseAbout> responseAbouts);

}
