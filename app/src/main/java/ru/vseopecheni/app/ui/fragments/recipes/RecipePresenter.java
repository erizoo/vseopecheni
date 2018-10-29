package ru.vseopecheni.app.ui.fragments.recipes;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface RecipePresenter <V extends RecipeMvpView> extends MvpPresenter<V> {

    void gertRecipies();

}
