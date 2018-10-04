package ru.vseopecheni.app.ui.base;

import io.reactivex.disposables.CompositeDisposable;
import ru.vseopecheni.app.data.RepositoryManager;

public interface MvpPresenter<V> {

    void onAttach(V mvpView);

    void onDetach();

    void onDestroy();

    CompositeDisposable getCompositeDisposable();

    V getMvpView();

    RepositoryManager getRepositoryManager();

}
