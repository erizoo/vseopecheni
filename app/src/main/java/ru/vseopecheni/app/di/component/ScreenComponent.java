package ru.vseopecheni.app.di.component;

import dagger.Component;
import ru.vseopecheni.app.di.PerScreen;
import ru.vseopecheni.app.di.module.ScreenModule;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.fragments.TableFiveFragment;

@PerScreen
@Component(modules = ScreenModule.class, dependencies = ApplicationComponent.class)
public interface ScreenComponent {

    void inject(MainActivity activity);

    void inject(TableFiveFragment fragment);
}
