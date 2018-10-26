package ru.vseopecheni.app.ui.fragments.disease;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;
import ru.vseopecheni.app.ui.fragments.MainPresenter;

public class LiverDiseasePresenterImpl <V extends LiverDiseaseMvpView> extends BasePresenter<V>
        implements LiverDiseasePresenter<V> {

    @Inject
    public LiverDiseasePresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }
    
}
