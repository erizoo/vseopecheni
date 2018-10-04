package ru.vseopecheni.app.di.component;

import android.content.Context;

import javax.inject.Singleton;


import dagger.Component;
import ru.vseopecheni.app.App;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.di.ApplicationContext;
import ru.vseopecheni.app.di.module.ApiModule;
import ru.vseopecheni.app.di.module.ApplicationModule;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(App application);

    @ApplicationContext
    Context context();

    RepositoryManager getRepositoryManager();

}

