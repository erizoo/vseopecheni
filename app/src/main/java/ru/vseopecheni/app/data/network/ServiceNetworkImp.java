package ru.vseopecheni.app.data.network;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenu;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;


public class ServiceNetworkImp implements ServiceNetwork {

    private static final String KEY = "hfiwehlgewjhg2342few";
    private static final String TYPE_RECIPES = "list_article_rcp_json";

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
    public Observable<ResponseMenu> getMenuForWeek() {
        return apiMethods.getMenuForWeek();
    }

    @Override
    public Observable<List<ResponseFullRecipes>> getFullRecipe() {
        return apiMethods.getFullRecipe(KEY, TYPE_RECIPES);
    }
}
