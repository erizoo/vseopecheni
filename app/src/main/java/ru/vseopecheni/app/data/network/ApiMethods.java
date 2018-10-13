package ru.vseopecheni.app.data.network;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vseopecheni.app.data.models.ResponseProducts;

public interface ApiMethods {

    @GET("json")
    Observable<List<ResponseProducts>> getProducts(@Query("key") String key,
                                                   @Query("id") String id,
                                                   @Query("type") String article);

}
