package ru.vseopecheni.app.di.component;

import dagger.Component;
import ru.vseopecheni.app.di.PerScreen;
import ru.vseopecheni.app.di.module.ScreenModule;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.fragments.MainFragment;
import ru.vseopecheni.app.ui.fragments.about.AboutLiverFull;
import ru.vseopecheni.app.ui.fragments.disease.LiverDiseaseFragment;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsFragment;
import ru.vseopecheni.app.ui.fragments.menu.MenuWeekFragment;
import ru.vseopecheni.app.ui.fragments.recipes.FullRecipeFragment;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeFragment;
import ru.vseopecheni.app.ui.fragments.table.TableFiveFragment;
import ru.vseopecheni.app.ui.fragments.treat.HowToTreatFullFragment;

@PerScreen
@Component(modules = ScreenModule.class, dependencies = ApplicationComponent.class)
public interface ScreenComponent {

    void inject(MainActivity activity);

    void inject(TableFiveFragment fragment);

    void inject(MainFragment fragment);

    void inject(FullRecipeFragment fragment);

    void inject(RecipeFragment fragment);

    void inject(MenuWeekFragment fragment);

    void inject(LiverDiseaseFragment fragment);

    void inject(AboutLiverFull fragment);

    void inject(HepatoprotectorsFragment fragment);

    void inject(HowToTreatFullFragment fragment);
}
