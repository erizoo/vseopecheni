package ru.vseopecheni.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import ru.vseopecheni.app.di.component.ApplicationComponent;
import ru.vseopecheni.app.di.component.DaggerApplicationComponent;
import ru.vseopecheni.app.di.module.ApplicationModule;

import static android.app.NotificationManager.IMPORTANCE_LOW;

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
