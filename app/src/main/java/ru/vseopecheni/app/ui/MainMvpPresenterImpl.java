package ru.vseopecheni.app.ui;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class MainMvpPresenterImpl <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V>{

    @Inject
    public MainMvpPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

}
