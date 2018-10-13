package ru.vseopecheni.app.ui.fragments;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.ui.base.MvpView;

public interface TableFiveMvpView extends MvpView {

    void onProductsUpdate(List<ResponseProducts> responseProducts);

}
