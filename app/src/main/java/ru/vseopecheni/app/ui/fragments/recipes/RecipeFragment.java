package ru.vseopecheni.app.ui.fragments.recipes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.adapters.RecipesAdapter;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.fragments.table.TableFiveProductsFragment;
import ru.vseopecheni.app.ui.model.RecipeCompositionModel;
import ru.vseopecheni.app.utils.Constant;

public class RecipeFragment extends Fragment implements RecipeMvpView {

    @Inject
    RecipePresenter<RecipeMvpView> presenter;

    private Unbinder unbinder;
    private RecyclerView recyclerViewRecipes;
    private RecipesAdapter recipesAdapter;

    private List<ResponseRecipes> responseRecipes = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        presenter.onAttach(this);
        if (isInternet()){
            presenter.getRecipes();
        } else {
            FileInputStream stream = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                stream = Objects.requireNonNull(getActivity()).openFileInput("jsonRecipes");
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                } finally {
                    Gson gson = new Gson();
                    Type listOfObject = new TypeToken<List<ResponseRecipes>>() {}.getType();
                    List<ResponseRecipes> responseRecipes = gson.fromJson(sb.toString(), listOfObject);
                    this.responseRecipes.addAll(responseRecipes);
                    stream.close();
                }
            } catch (Exception e) {
                Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        recyclerViewRecipes = v.findViewById(R.id.recipes_rv);
        recipesAdapter = new RecipesAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewRecipes.setLayoutManager(layoutManager);
        recyclerViewRecipes.setAdapter(recipesAdapter);

        if (!isInternet()){
            recipesAdapter.setItems(this.responseRecipes);
        }
        return v;
    }

    @Override
    public void onRecipesUpdated(List<ResponseRecipes> responseRecipes) {
        FileOutputStream outputStream;
        try {
            outputStream = Objects.requireNonNull(getActivity()).openFileOutput("jsonRecipes", Context.MODE_PRIVATE);
            outputStream.
                    write(new Gson().toJson(responseRecipes).getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        recipesAdapter.setItems(responseRecipes);
    }

    public boolean isInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager)Objects.requireNonNull(getContext()).getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else {
            return false;
        }
    }
}
