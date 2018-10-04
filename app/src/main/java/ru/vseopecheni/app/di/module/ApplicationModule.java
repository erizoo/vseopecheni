package ru.vseopecheni.app.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.vseopecheni.app.App;
import ru.vseopecheni.app.data.RepositoryManager;
import ru.vseopecheni.app.data.RepositoryManagerImpl;
import ru.vseopecheni.app.di.ApplicationContext;

@Module
public class ApplicationModule {

    private final App application;

    public ApplicationModule(App application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    App provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    RepositoryManager provideRepositoryManager(RepositoryManagerImpl repositoryManager) {
        return repositoryManager;
    }
}

