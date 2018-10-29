package ru.vseopecheni.app.ui.fragments.recipes;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.ui.base.BasePresenter;

public class RecipePresenterImpl <V extends RecipeMvpView> extends BasePresenter<V>
        implements RecipePresenter<V>{

    @Inject
    public RecipePresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void gertRecipies() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getFullRecipe()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onProductsUpdate,
                                Throwable::printStackTrace
                        )
        );
    }
}
