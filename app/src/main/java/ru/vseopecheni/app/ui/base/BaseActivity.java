package ru.vseopecheni.app.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import ru.vseopecheni.app.App;
import ru.vseopecheni.app.di.component.DaggerScreenComponent;
import ru.vseopecheni.app.di.component.ScreenComponent;
import ru.vseopecheni.app.di.module.ScreenModule;
import ru.vseopecheni.app.utils.Constant;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    @Inject
    CompositeDisposable compositeDisposable;

    private Unbinder unbinder;
    private ScreenComponent screenComponent;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);

        screenComponent = DaggerScreenComponent.builder()
                .screenModule(new ScreenModule(this))
                .applicationComponent(((App) getApplication()).getApplicationComponent())
                .build();

    }

    protected abstract int getContentView();

    public ScreenComponent getScreenComponent() {
        return screenComponent;
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

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

}

