package ru.vseopecheni.app.ui.fragments.treat;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class HowToTreatFullPresenterImpl <V extends HowToTreatFullMvpView> extends BasePresenter<V>
        implements HowToTreatFullPresenter<V> {

    @Inject
    public HowToTreatFullPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void getInfoAboutTreat(String number) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getInfoAboutTreat(number)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onInfoAboutTreatUpdated,
                                Throwable::printStackTrace
                        )
        );
    }
}
