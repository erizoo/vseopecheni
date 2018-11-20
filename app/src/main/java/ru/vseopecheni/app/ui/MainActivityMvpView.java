package ru.vseopecheni.app.ui;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseId;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.ui.base.MvpView;
import ru.vseopecheni.app.ui.model.SaveInfo;

public interface MainActivityMvpView extends MvpView {

    void onInfoSaved(SaveInfo saveInfo);

    void onImagesSaved();

    void onSaveFullUpdated(List<ResponseAbout> responseAbouts);

    void onProductsUpdated(List<ResponseProducts> responseProducts);

    void onSavedMenuForWeek(ResponseMenuForWeek responseMenuForWeek);

    void onSavedRecipes(List<ResponseFullRecipes> responseFullRecipes);

    void onGetedAll(List<ResponseId> responseIds);
}
