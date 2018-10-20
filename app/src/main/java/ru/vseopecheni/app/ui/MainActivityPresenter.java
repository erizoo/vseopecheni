package ru.vseopecheni.app.ui;

import android.content.Context;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface MainActivityPresenter  <V extends MainActivityMvpView> extends MvpPresenter<V> {

    void saveAll();

    void saveRecipesImages(List<ResponseFullRecipes> responseFullRecipes, Context context, MainActivity mainActivity);
}
