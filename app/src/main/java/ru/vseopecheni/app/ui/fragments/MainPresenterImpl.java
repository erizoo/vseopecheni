package ru.vseopecheni.app.ui.fragments;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class MainPresenterImpl <V extends MainMvpView> extends BasePresenter<V>
        implements MainPresenter<V> {

    @Inject
    public MainPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void getRecipes() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getRecipes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onRecipesUpdated,
                                Throwable::printStackTrace
                        )
        );
    }
}
