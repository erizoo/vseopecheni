package ru.vseopecheni.app.data.network;

import java.util.List;

import io.reactivex.Observable;
import ru.vseopecheni.app.data.models.ResponseProducts;

public interface ServiceNetwork {

    Observable<List<ResponseProducts>> getProducts(String drinksId);
}
