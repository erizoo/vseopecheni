package ru.vseopecheni.app.data.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenu;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;

public interface ApiMethods {

    @GET("jsonmn")
    Observable<List<ResponseProducts>> getProducts(@Query("key") String key);

    @GET("json2")
    Observable<List<ResponseFullRecipes>> getFullRecipe(@Query("key") String key,
                                                        @Query("type") String article);

    @GET("json-nedelka")
    Observable<ResponseMenuForWeek> getMenuForWeek(@Query("key") String key);

    @GET("json")
    Observable<List<ResponseAbout>>  getFull(@Query("type") String typeArticle,
                                      @Query("id") String id,
                                      @Query("key") String key);

    @GET("json")
    Observable<List<ResponseProducts>> getProductsForNet(@Query("type") String typeArticle,
                                                         @Query("id") String id,
                                                         @Query("key") String key);
}
