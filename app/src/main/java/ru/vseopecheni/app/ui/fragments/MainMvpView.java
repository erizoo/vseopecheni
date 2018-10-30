package ru.vseopecheni.app.ui.fragments;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;
import ru.vseopecheni.app.ui.base.MvpView;
import ru.vseopecheni.app.ui.model.SaveInfo;

public interface MainMvpView extends MvpView {


    void onInfoSaved(SaveInfo saveInfo);

    void onSaveProductUpdated(Object o);

    void onSaveFullUpdated(List<ResponseAbout> responseAbouts);

    void onSavedMenuForWeek(ResponseMenuForWeek responseMenuForWeek);

    void onProductsUpdated(List<ResponseProducts> responseProducts);

    void onSavedRecipes(List<ResponseFullRecipes> responseFullRecipes);
}
