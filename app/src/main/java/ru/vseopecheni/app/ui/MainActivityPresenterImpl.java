package ru.vseopecheni.app.ui;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Handler;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.data.network.ServiceNetwork;
import ru.vseopecheni.app.ui.base.BasePresenter;
import ru.vseopecheni.app.ui.model.SaveInfo;
import ru.vseopecheni.app.utils.ImageSaver;

public class MainActivityPresenterImpl<V extends MainActivityMvpView> extends BasePresenter<V>
        implements MainActivityPresenter<V> {

    @Inject
    public MainActivityPresenterImpl(RepositoryManager repositoryManager, CompositeDisposable compositeDisposable) {
        super(repositoryManager, compositeDisposable);
    }

    @Override
    public void saveAll() {
        ServiceNetwork serviceNetwork = getRepositoryManager().getServiceNetwork();
        getCompositeDisposable().add(
                Observable.zip(
                        serviceNetwork.getProducts(),
                        serviceNetwork.getFullRecipe(),
                        SaveInfo::new
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onInfoSaved,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void saveRecipesImages(List<ResponseFullRecipes> responseFullRecipes, Context context, MainActivity mainActivity) {
        new Thread(() -> {
            for (ResponseFullRecipes items : responseFullRecipes) {
                Bitmap theBitmap = null;
                try {
                    if (items.getImage().contains("images")) {
                        theBitmap = Glide.with(context)
                                .asBitmap()
                                .load("https://vseopecheni.ru/" + items.getImage())
                                .into(500, 300)
                                .get();
                    } else {
                        theBitmap = Glide.with(context)
                                .asBitmap()
                                .load("https://vseopecheni.ru" + items.getImage())
                                .into(500, 300)
                                .get();
                    }
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                new ImageSaver(context).
                        setFileName(items.getId() + ".jpg")
                        .setDirectoryName("images")
                        .save(theBitmap);
        }
            mainActivity.runOnUiThread(() -> getMvpView().onImagesSaved());
        }).start();
    }

    @Override
    public void getFull(String s) {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getFull(s)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSaveFullUpdated,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void getProducts() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getProducts()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onProductsUpdated,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void saveMenuForWeek() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getMenuForWeek()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSavedMenuForWeek,
                                Throwable::printStackTrace
                        )
        );
    }

    @Override
    public void saveRecipes() {
        getCompositeDisposable().add(
                getRepositoryManager().getServiceNetwork().getFullRecipe()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                getMvpView()::onSavedRecipes,
                                Throwable::printStackTrace
                        )
        );
    }

}
