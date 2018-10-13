package ru.vseopecheni.app.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.vseopecheni.app.di.ActivityContext;
import ru.vseopecheni.app.di.PerScreen;
import ru.vseopecheni.app.ui.MainMvpPresenter;
import ru.vseopecheni.app.ui.MainMvpPresenterImpl;
import ru.vseopecheni.app.ui.MainMvpView;
import ru.vseopecheni.app.ui.fragments.TableFiveMvpView;
import ru.vseopecheni.app.ui.fragments.TableFivePresenter;
import ru.vseopecheni.app.ui.fragments.TableFivePresenterImpl;

@Module
public class ScreenModule {

    private final AppCompatActivity activity;

    public ScreenModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    @PerScreen
    MainMvpPresenter<MainMvpView> provideEquipmentDeliveryCertificateMvpPresenter(MainMvpPresenterImpl<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    TableFivePresenter<TableFiveMvpView> provideTableFivePresenter(TableFivePresenterImpl<TableFiveMvpView> presenter) {
        return presenter;
    }
}
