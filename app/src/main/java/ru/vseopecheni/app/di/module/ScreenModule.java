package ru.vseopecheni.app.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import ru.vseopecheni.app.di.ActivityContext;
import ru.vseopecheni.app.di.PerScreen;
import ru.vseopecheni.app.ui.MainActivityMvpView;
import ru.vseopecheni.app.ui.MainActivityPresenter;
import ru.vseopecheni.app.ui.MainActivityPresenterImpl;
import ru.vseopecheni.app.ui.fragments.MainMvpView;
import ru.vseopecheni.app.ui.fragments.MainPresenter;
import ru.vseopecheni.app.ui.fragments.MainPresenterImpl;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFull;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFullMvpView;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFullPresenter;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFullPresenterImpl;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseaseMvpView;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseasePresenter;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseasePresenterImpl;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsMvpView;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsPresenter;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsPresenterImpl;
import ru.vseopecheni.app.ui.fragments.menu.MenuForWeekMvpView;
import ru.vseopecheni.app.ui.fragments.menu.MenuForWeekPresenter;
import ru.vseopecheni.app.ui.fragments.menu.MenuForWeekPresenterImpl;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeMvpView;
import ru.vseopecheni.app.ui.fragments.recipes.RecipePresenter;
import ru.vseopecheni.app.ui.fragments.recipes.RecipePresenterImpl;
import ru.vseopecheni.app.ui.fragments.table.TableFiveMvpView;
import ru.vseopecheni.app.ui.fragments.table.TableFivePresenter;
import ru.vseopecheni.app.ui.fragments.table.TableFivePresenterImpl;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFullMvpView;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFullPresenter;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFullPresenterImpl;

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
    MainActivityPresenter<MainActivityMvpView> provideMainActivityPresenter(MainActivityPresenterImpl<MainActivityMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    MenuForWeekPresenter<MenuForWeekMvpView> provideMenuForWeekPresenter(MenuForWeekPresenterImpl<MenuForWeekMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    LiverDiseasePresenter<LiverDiseaseMvpView> provideLiverDiseasePresenter(LiverDiseasePresenterImpl<LiverDiseaseMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    AboutLiverFullPresenter<AboutLiverFullMvpView> provideAboutLiverFullPresenter(AboutLiverFullPresenterImpl<AboutLiverFullMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    HepatoprotectorsPresenter<HepatoprotectorsMvpView> provideHepatoprotectorsPresenter(HepatoprotectorsPresenterImpl<HepatoprotectorsMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    HowToTreatFullPresenter<HowToTreatFullMvpView> provideHowToTreatFullPresenter(HowToTreatFullPresenterImpl<HowToTreatFullMvpView> presenter) {
        return presenter;
    }

    @Provides
    @PerScreen
    RecipePresenter<RecipeMvpView> provideRecipePresenter(RecipePresenterImpl<RecipeMvpView> presenter) {
        return presenter;
    }

}
