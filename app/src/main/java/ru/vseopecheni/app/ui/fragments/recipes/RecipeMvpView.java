package ru.vseopecheni.app.ui.fragments.recipes;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseRecipes;
import ru.vseopecheni.app.ui.base.MvpView;

public interface RecipeMvpView extends MvpView {

    void onRecipesUpdated(List<ResponseRecipes> responseRecipes);

}
