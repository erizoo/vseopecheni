package ru.vseopecheni.app.ui.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SearchView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import ru.vseopecheni.app.App;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.di.component.DaggerScreenComponent;
import ru.vseopecheni.app.di.component.ScreenComponent;
import ru.vseopecheni.app.di.module.ScreenModule;
import ru.vseopecheni.app.ui.ViewPagerAdapter;
import ru.vseopecheni.app.ui.fragments.MainFragment;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFragment;
import ru.vseopecheni.app.ui.fragments.menu.MenuWeekFragment;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeFragment;
import ru.vseopecheni.app.ui.fragments.table.TableFiveFragment;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFragment;
import ru.vseopecheni.app.utils.Constant;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    @Inject
    CompositeDisposable compositeDisposable;

    private Unbinder unbinder;
    private ScreenComponent screenComponent;
    private ProgressDialog progressDialog;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        viewPager = findViewById(R.id.viewpager);
        fragmentManager = getSupportFragmentManager();

        screenComponent = DaggerScreenComponent.builder()
                .screenModule(new ScreenModule(this))
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .build();
    }

    protected abstract int getContentView();

    public ScreenComponent getScreenComponent() {
        return screenComponent;
    }


    public void showLoading() {
        progressDialog = Constant.showLoadingDialog(this);
    }

    public void showLoadingWithMessage(String title) {
        progressDialog = Constant.showLoadingDialog(this, title);
    }

    public void hideLoading() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    @SuppressLint("CommitTransaction")
    public void moveToNewFragment(Fragment fragment) {
        fragmentManager.beginTransaction().addToBackStack(fragment.getClass().getName()).commit();
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(fragment);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        List<Fragment> fragmentList = fragmentManager.getFragments();
        String nameFragment = fragmentList.get(fragmentList.size() - 1).getClass().getSimpleName();
        switch (nameFragment) {
            case "TableFiveProductsFragment":
                moveToNewFragment(new TableFiveFragment());
                break;
            case "TableFiveFragment":
                moveToNewFragment(new MainFragment());
                break;
            case "HepatoprotectorsFragment":
                moveToNewFragment(new MainFragment());
                break;
            case "AboutLiverFull":
                moveToNewFragment(new AboutLiverFragment());
                break;
            case "FullRecipeForWeekFragment":
                moveToNewFragment(new MenuWeekFragment());
                break;
            case "FullRecipeFragment":
                break;
            case "MainFragment":
                break;
            case "HowToTreatFullFragment":
                moveToNewFragment(new HowToTreatFragment());
                break;
            default:
                moveToNewFragment(new MainFragment());
                break;
        }

    }
}

