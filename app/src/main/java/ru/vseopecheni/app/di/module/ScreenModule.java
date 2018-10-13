package ru.vseopecheni.app.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.vseopecheni.app.di.ActivityContext;
import ru.vseopecheni.app.di.PerScreen;
import ru.vseopecheni.app.ui.fragments.MainMvpView;
import ru.vseopecheni.app.ui.fragments.MainPresenter;
import ru.vseopecheni.app.ui.fragments.MainPresenterImpl;
import ru.vseopecheni.app.ui.fragments.table.TableFiveMvpView;
import ru.vseopecheni.app.ui.fragments.table.TableFivePresenter;
import ru.vseopecheni.app.ui.fragments.table.TableFivePresenterImpl;

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
    TableFivePresenter<TableFiveMvpView> provideTableFivePresenter(TableFivePresenterImpl<TableFiveMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    MainPresenter<MainMvpView> provideMainPresenter(MainPresenterImpl<MainMvpView> presenter) {
        return presenter;
    }
}
