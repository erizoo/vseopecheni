package ru.vseopecheni.app.ui.fragments.recipes;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class FullRecipePresenterImpl<V extends FullRecipeMvpView> extends BasePresenter<V>
        implements FullRecipePresenter<V> {

    @Inject
    public FullRecipePresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void getFullRecipe(String id) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getFullRecipe(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onFullRecipeUpdated,
                                Throwable::printStackTrace
                        )
        );
    }
}
