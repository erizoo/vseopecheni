package ru.vseopecheni.app.ui.fragments.recipes;

import ru.vseopecheni.app.ui.base.MvpPresenter;

public interface FullRecipePresenter <V extends FullRecipeMvpView> extends MvpPresenter<V> {

    void getFullRecipe(String id);

}
