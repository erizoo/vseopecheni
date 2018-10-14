package ru.vseopecheni.app.data.network;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;

public interface ServiceNetwork {

    Observable<List<ResponseProducts>> getProducts(String drinksId);

    Observable<List<ResponseRecipes>> getRecipes();

    Observable<List<ResponseFullRecipes>> getFullRecipe(String id);
}
