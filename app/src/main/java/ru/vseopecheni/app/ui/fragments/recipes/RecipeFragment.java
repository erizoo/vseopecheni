package ru.vseopecheni.app.ui.fragments.recipes;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.ui.adapters.RecipesAdapter;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.utils.Constant;

import static android.content.Context.MODE_PRIVATE;

public class RecipeFragment extends Fragment {

    private Unbinder unbinder;
    private RecyclerView recyclerViewRecipes;
    private RecipesAdapter recipesAdapter;
    private SharedPreferences sharedPreferences;
    private String id;

    private List<ResponseFullRecipes> responseFullRecipes = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);

        ((BaseActivity) getActivity()).showLoading();
        sharedPreferences = getActivity().getPreferences(MODE_PRIVATE);
        id = sharedPreferences.getString("id", "");
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
                Type listOfObject = new TypeToken<List<ResponseFullRecipes>>() {
                }.getType();
                List<ResponseFullRecipes> responseFullRecipes = gson.fromJson(sb.toString(), listOfObject);
                this.responseFullRecipes.addAll(responseFullRecipes);
                stream.close();
                ((BaseActivity) getActivity()).hideLoading();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Подключите интернет", Toast.LENGTH_LONG).show();
            Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
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
        recipesAdapter.setItems(responseFullRecipes);

        for (int i = 0; i < responseFullRecipes.size() - 1; i++) {
            if (responseFullRecipes.get(i).getId().equals(id)){
                recyclerViewRecipes.scrollToPosition(i);
            }
        }
        return v;
    }
}
