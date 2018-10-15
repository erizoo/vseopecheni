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
import ru.vseopecheni.app.ui.fragments.recipes.FullRecipeMvpView;
import ru.vseopecheni.app.ui.fragments.recipes.FullRecipePresenter;
import ru.vseopecheni.app.ui.fragments.recipes.FullRecipePresenterImpl;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeMvpView;
import ru.vseopecheni.app.ui.fragments.recipes.RecipePresenter;
import ru.vseopecheni.app.ui.fragments.recipes.RecipePresenterImpl;
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

    @Provides
    @PerScreen
    FullRecipePresenter<FullRecipeMvpView> provideFullRecipePresenter(FullRecipePresenterImpl<FullRecipeMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    RecipePresenter<RecipeMvpView> provideRecipePresenter(RecipePresenterImpl<RecipeMvpView> presenter) {
        return presenter;
    }
}
