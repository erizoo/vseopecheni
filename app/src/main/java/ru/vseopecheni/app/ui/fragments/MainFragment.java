package ru.vseopecheni.app.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseRecipes;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.ui.fragments.hepatoprotectors.HepatoprotectorsFragment;
import ru.vseopecheni.app.ui.fragments.menu.MenuWeekFragment;
import ru.vseopecheni.app.ui.fragments.recipes.RecipeFragment;
import ru.vseopecheni.app.ui.fragments.table.TableFiveFragment;

public class MainFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainPresenter<MainMvpView> presenter;

    private ImageView tableFive;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        hideLoading();
        return v;
    }


    @OnClick(R.id.table_five_image)
    public void getTable(){
        TableFiveFragment tableFiveFragment = new TableFiveFragment();
        ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(tableFiveFragment);
    }

    @OnClick(R.id.recipes_image)
    public void getRecipes(){
        RecipeFragment recipeFragment = new RecipeFragment();
        ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(recipeFragment);
    }

    @OnClick(R.id.hepatoprotectors)
    public void getHepatoprotectors(){
        HepatoprotectorsFragment hepatoprotectorsFragment = new HepatoprotectorsFragment();
        ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(hepatoprotectorsFragment);
    }

    @OnClick(R.id.menu_week)
    public void getMenuForWeek(){
        MenuWeekFragment menuWeekFragment = new MenuWeekFragment();
        ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(menuWeekFragment);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        OnClickListener myClickListener =  myClickListener = (dialog, which) -> {
            switch (which) {
                case Dialog.BUTTON_POSITIVE:
                    break;
                case Dialog.BUTTON_NEGATIVE:
                    break;
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        };

        AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
        adb.setTitle(R.string.title_dialog);
        adb.setMessage(R.string.content_dialog);
        adb.setIcon(android.R.drawable.ic_dialog_info);
        adb.setPositiveButton(R.string.yes_dialog, myClickListener);
        adb.setNegativeButton(R.string.no_dialog, myClickListener);
        adb.setCancelable(false);
        adb.create();
        adb.show();
        super.onViewCreated(view, savedInstanceState);
    }

}