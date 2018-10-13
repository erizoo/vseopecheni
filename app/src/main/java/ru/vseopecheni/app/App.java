package ru.vseopecheni.app;

import android.app.Application;

import ru.vseopecheni.app.di.component.ApplicationComponent;
import ru.vseopecheni.app.di.component.DaggerApplicationComponent;
import ru.vseopecheni.app.di.module.ApplicationModule;

public class App extends Application {


    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }


}