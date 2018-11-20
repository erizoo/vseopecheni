package ru.vseopecheni.app.data.network;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseId;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;

public interface ServiceNetwork {


    Observable<List<ResponseFullRecipes>> getFullRecipe();

    Observable<List<ResponseProducts>> getProducts();

    Observable<ResponseMenuForWeek> getMenuForWeek();

    Observable<List<ResponseAbout>> getFull(String number);

    Observable<List<ResponseAbout>> getInfoAboutTreat(String number);

    Observable<List<ResponseProducts>> getProductsForNet(String drinksId);

    Observable<Object> saveProduct();

    Observable<List<ResponseAbout>> getFullAbout(String s);

    Observable<List<ResponseId>> getAllId();
}
