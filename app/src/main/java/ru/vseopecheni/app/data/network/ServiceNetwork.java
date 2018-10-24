package ru.vseopecheni.app.data.network;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenu;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;

public interface ServiceNetwork {


    Observable<List<ResponseFullRecipes>> getFullRecipe();

    Observable<List<ResponseProducts>> getProducts();

    Observable<ResponseMenu> getMenuForWeek();
}
