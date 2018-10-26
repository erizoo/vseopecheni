package ru.vseopecheni.app.ui.fragments.hepatoprotectors;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class HepatoprotectorsPresenterImpl <V extends HepatoprotectorsMvpView> extends BasePresenter<V>
        implements HepatoprotectorsPresenter<V> {

    @Inject
    public HepatoprotectorsPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void getFull(String number) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getFull(number)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onFullInfoUpdated,
                                Throwable::printStackTrace
                        )
        );
    }
}
