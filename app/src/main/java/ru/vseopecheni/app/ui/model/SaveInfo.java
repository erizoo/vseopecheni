package ru.vseopecheni.app.ui.model;

import java.util.List;

import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.models.ResponseProducts;

public class SaveInfo {

    private List<ResponseFullRecipes> responseFullRecipes;
    private List<ResponseProducts> responseProducts;

    public SaveInfo() {
    }

    public SaveInfo(List<ResponseProducts> responseProducts, List<ResponseFullRecipes> responseFullRecipes) {
        this.responseFullRecipes = responseFullRecipes;
        this.responseProducts = responseProducts;
    }

    public List<ResponseFullRecipes> getResponseFullRecipes() {
        return responseFullRecipes;
    }

    public void setResponseFullRecipes(List<ResponseFullRecipes> responseFullRecipes) {
        this.responseFullRecipes = responseFullRecipes;
    }

    public List<ResponseProducts> getResponseProducts() {
        return responseProducts;
    }

    public void setResponseProducts(List<ResponseProducts> responseProducts) {
        this.responseProducts = responseProducts;
    }
}
