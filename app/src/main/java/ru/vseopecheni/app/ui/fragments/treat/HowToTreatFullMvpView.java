package ru.vseopecheni.app.ui.fragments.treat;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.ui.base.MvpView;

public interface HowToTreatFullMvpView extends MvpView {

    void onInfoAboutTreatUpdated(List<ResponseAbout> responseAbouts);

}
