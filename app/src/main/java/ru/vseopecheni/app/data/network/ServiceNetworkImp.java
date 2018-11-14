package ru.vseopecheni.app.data.network;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenu;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;


public class ServiceNetworkImp implements ServiceNetwork {

    private static final String KEY = "hfiwehlgewjhg2342few";
    private static final String TYPE_RECIPES = "list_article_rcp_json";
    private static final String TYPE_ARTICLE = "article";
    private String id;

    private ApiMethods apiMethods;

    @Inject
    public ServiceNetworkImp(ApiMethods apiMethods) {
        this.apiMethods = apiMethods;
    }


    @Override
    public Observable<List<ResponseProducts>> getProducts() {
        return apiMethods.getProducts(KEY);
    }

    @Override
    public Observable<ResponseMenuForWeek> getMenuForWeek() {
        return apiMethods.getMenuForWeek(KEY);
    }

    @Override
    public Observable<List<ResponseAbout>> getFull(String number) {
        if (number.equals("1")){
           id = "197";
        }
        if (number.equals("2")){
            id = "166";
        }
        if (number.equals("3")){
            id = "9";
        }
        if (number.equals("4")){
            id = "8";
        }
        if (number.equals("5")){
            id = "10";
        }
        if (number.equals("6")){
            id = "11";
        }
        if (number.equals("210")){
            id = "210";
        }
        if (number.equals("204")){
            id = "204";
        }
        if (number.equals("212")){
            id = "212";
        }
        if (number.equals("213")){
            id = "213";
        }
        if (number.equals("16")){
            id = "16";
        }
        return apiMethods.getFull(TYPE_ARTICLE, id, KEY);
    }

    @Override
    public Observable<List<ResponseAbout>> getInfoAboutTreat(String number) {
        return apiMethods.getFull(TYPE_ARTICLE, number, KEY);
    }

    @Override
    public Observable<List<ResponseProducts>> getProductsForNet(String drinksId) {
        return apiMethods.getProductsForNet(TYPE_ARTICLE, drinksId, KEY);
    }

    @Override
    public Observable<Object> saveProduct() {
        return null;
    }

    @Override
    public Observable<List<ResponseAbout>> getFullAbout(String s) {
        return apiMethods.getFull(TYPE_ARTICLE, s, KEY);
    }

    @Override
    public Observable<List<ResponseFullRecipes>> getFullRecipe() {
        return apiMethods.getFullRecipe(KEY, TYPE_RECIPES);
    }
}
