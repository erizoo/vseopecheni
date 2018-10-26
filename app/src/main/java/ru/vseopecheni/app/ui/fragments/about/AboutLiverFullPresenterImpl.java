package ru.vseopecheni.app.ui.fragments.about;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseaseMvpView;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseasePresenter;

public class AboutLiverFullPresenterImpl  <V extends AboutLiverFullMvpView> extends BasePresenter<V>
        implements AboutLiverFullPresenter<V> {

    @Inject
    public AboutLiverFullPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
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
