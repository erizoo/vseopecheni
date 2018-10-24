package ru.vseopecheni.app.data.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;

public interface ApiMethods {

    @GET("jsonmn")
    Observable<List<ResponseProducts>> getProducts(@Query("key") String key);

    @GET("json2")
    Observable<List<ResponseFullRecipes>> getFullRecipe(@Query("key") String key,
                                                        @Query("type") String article);

    @GET("18tnro")
    Observable<List<ResponseMenuForWeek>> getMenuForWeek();
}
