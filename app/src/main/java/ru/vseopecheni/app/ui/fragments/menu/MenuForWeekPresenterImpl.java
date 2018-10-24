package ru.vseopecheni.app.ui.fragments.menu;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class MenuForWeekPresenterImpl <V extends MenuForWeekMvpView> extends BasePresenter<V>
        implements MenuForWeekPresenter<V> {

    @Inject
    public MenuForWeekPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void getMenuForWeek() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getMenuForWeek()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onMenuForWeekUpdated,
                                Throwable::printStackTrace
                        )
        );
    }
}
