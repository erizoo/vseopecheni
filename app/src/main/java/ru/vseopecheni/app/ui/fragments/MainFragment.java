package ru.vseopecheni.app.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;

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
        presenter.getRecipes();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onRecipesUpdated(List<ResponseRecipes> responseRecipes) {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("recipes", new Gson().toJson(responseRecipes));
        recipeFragment.setArguments(bundle);
        ((MainActivity)Objects.requireNonNull(getActivity())).moveToNewFragment(recipeFragment);
    }
}