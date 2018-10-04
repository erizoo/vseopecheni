package ru.vseopecheni.app.di.component;

import dagger.Component;
import ru.vseopecheni.app.di.PerScreen;
import ru.vseopecheni.app.di.module.ScreenModule;
import ru.vseopecheni.app.ui.MainActivity;

@PerScreen
@Component(modules = ScreenModule.class, dependencies = ApplicationComponent.class)
public interface ScreenComponent {

    void inject(MainActivity activity);

}
