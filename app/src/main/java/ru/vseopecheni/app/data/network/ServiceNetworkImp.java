package ru.vseopecheni.app.data.network;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;


public class ServiceNetworkImp implements ServiceNetwork {

    private static final String KEY = "hfiwehlgewjhg2342few";
    private static final String TYPE = "article";
    private static final String TYPE_RECIPES = "list_article_json";

    private ApiMethods apiMethods;

    @Inject
    public ServiceNetworkImp(ApiMethods apiMethods) {
        this.apiMethods = apiMethods;
    }


    @Override
    public Observable<List<ResponseProducts>> getProducts(String drinksId) {
        return apiMethods.getProducts(KEY, drinksId, TYPE);
    }

    @Override
    public Observable<List<ResponseRecipes>> getRecipes() {
        return apiMethods.getRecipes(KEY, TYPE_RECIPES);
    }
}
