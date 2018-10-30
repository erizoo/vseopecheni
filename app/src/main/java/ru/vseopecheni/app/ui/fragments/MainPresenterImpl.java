package ru.vseopecheni.app.ui.fragments;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.data.network.ServiceNetwork;
import ru.vseopecheni.app.ui.base.BasePresenter;
import ru.vseopecheni.app.ui.model.SaveInfo;

public class MainPresenterImpl <V extends MainMvpView> extends BasePresenter<V>
        implements MainPresenter<V> {

    @Inject
    public MainPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void saveAll() {
        ServiceNetwork serviceNetwork = getRepositoryManager().getServiceNetwork();
        getCompositeDisposable().add(
                Observable.zip(
                        serviceNetwork.getProducts(),
                        serviceNetwork.getFullRecipe(),
                        SaveInfo::new
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onInfoSaved,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void saveProduct() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().saveProduct()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSaveProductUpdated,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void getFull(String s) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getFull(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSaveFullUpdated,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void saveMenuForWeek() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getMenuForWeek()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSavedMenuForWeek,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void getProducts() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onProductsUpdated,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void saveRecipes() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getFullRecipe()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSavedRecipes,
                                Throwable::printStackTrace
                        )
        );
    }
}
