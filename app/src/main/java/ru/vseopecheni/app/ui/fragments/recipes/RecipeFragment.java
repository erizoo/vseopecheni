package ru.vseopecheni.app.ui.fragments.recipes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseProducts;
import ru.vseopecheni.app.data.models.ResponseRecipes;
import ru.vseopecheni.app.ui.adapters.RecipesAdapter;

public class RecipeFragment extends Fragment {

    private Unbinder unbinder;
    private RecyclerView recyclerViewRecipes;
    private RecipesAdapter recipesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        Bundle bundle = this.getArguments();
        if(bundle != null){
            Gson gson = new Gson();
            Type listOfObject = new TypeToken<List<ResponseRecipes>>(){}.getType();
            List<ResponseRecipes> responseRecipes = gson.fromJson(bundle.getString("recipes"), listOfObject);
            recipesAdapter.setItems(responseRecipes);
        }
        return v;
    }

}
