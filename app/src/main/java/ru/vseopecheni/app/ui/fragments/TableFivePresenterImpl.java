package ru.vseopecheni.app.ui.fragments;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class TableFivePresenterImpl <V extends TableFiveMvpView> extends BasePresenter<V>
        implements TableFivePresenter<V> {

    @Inject
    public TableFivePresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void getProducts(String drinksId) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getProducts(drinksId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onProductsUpdate,
                                Throwable::printStackTrace
                        )
        );
    }
}
