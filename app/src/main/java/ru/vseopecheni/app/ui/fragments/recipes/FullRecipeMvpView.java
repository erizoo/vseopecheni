package ru.vseopecheni.app.ui.fragments.recipes;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.ui.base.MvpView;

public interface FullRecipeMvpView extends MvpView {

    void onFullRecipeUpdated(List<ResponseFullRecipes> responseFullRecipes);

}
