package ru.vseopecheni.app.ui.fragments.recipes;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseAbout;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.ui.fragments.MainFragment;
import ru.vseopecheni.app.ui.model.RecipeCompositionModel;
import ru.vseopecheni.app.utils.Constant;

import static android.content.Context.MODE_PRIVATE;

public class FullRecipeFragment extends BaseFragment {


    @BindView(R.id.full_recipe_imageView)
    ImageView imageView;
    @BindView(R.id.title_textView)
    TextView title;
    @BindView(R.id.composition_recipe)
    TextView compositionRecipe;
    @BindView(R.id.cooking_method_full)
    TextView cookingMethodFull;

    private Unbinder unbinder;
    private String id;
    private String json;
    private Bitmap bitmap;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_full_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        ((BaseActivity) Objects.requireNonNull(getActivity())).getScreenComponent().inject(this);
        Bundle bundle = this.getArguments();
        if (!Constant.isInternet(getContext())){
            if (bundle != null) {
                id = bundle.getString("id");
                FileInputStream stream = null;
                StringBuilder sb = new StringBuilder();
                String line;
                ResponseFullRecipes responseFullRecipes = new ResponseFullRecipes();
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
                        List<ResponseFullRecipes> responseFullRecipesList = gson.fromJson(sb.toString(), listOfObject);
                        for (ResponseFullRecipes item : responseFullRecipesList) {
                            if (item.getId().equals(id)) {
                                responseFullRecipes.setContent(item.getContent());
                                responseFullRecipes.setSostav(item.getSostav());
                                responseFullRecipes.setTitle(item.getTitle());
                            }
                        }
                        stream.close();
                        ((BaseActivity) getActivity()).hideLoading();
                    }
                } catch (Exception e) {
                    Log.d(Constant.TAG, "Файла нет или произошла ошибка при чтении");
                }
                writeView(responseFullRecipes);
                bitmap = bundle.getParcelable("BitmapImage");
                Glide.with(Objects.requireNonNull(getContext()))
                        .asBitmap()
                        .load(bitmap)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(new RequestOptions().fitCenter())
                        .into(imageView);
            }
        } else {
            json = Objects.requireNonNull(bundle).getString("json");
            Gson gson = new Gson();
            ResponseFullRecipes responseFullRecipes = gson.fromJson(json, ResponseFullRecipes.class);
            Glide.with(Objects.requireNonNull(getContext()))
                    .asBitmap()
                    .load("https://app.vseopecheni.ru/" + responseFullRecipes.getImage())
                    .apply(RequestOptions.circleCropTransform())
                    .apply(new RequestOptions().fitCenter())
                    .into(imageView);
            Type listOfObject = new TypeToken<List<RecipeCompositionModel>>() {
            }.getType();
            List<RecipeCompositionModel> recipeCompositionModels = gson.fromJson(
                    responseFullRecipes.getSostav(), listOfObject);
            StringBuilder sb = new StringBuilder();
            for (RecipeCompositionModel items : recipeCompositionModels) {
                sb.append(".").append(" ").append(items.getName()).append(" ").append(items.getValue()).append(System.getProperty("line.separator"));
            }
            compositionRecipe.setText(sb.toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                cookingMethodFull.setText(Html.fromHtml(responseFullRecipes.getContent(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                cookingMethodFull.setText(Html.fromHtml(responseFullRecipes.getContent()));
            }
            title.setText(responseFullRecipes.getTitle());
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK ) {
                sharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putString("id", id);
                ed.apply();
                ((BaseActivity)getActivity()).moveToNewFragment(new RecipeFragment());
                return true;
            }
            return false;
        });
    }

    public void writeView(ResponseFullRecipes responseFullRecipes) {
        title.setText(responseFullRecipes.getTitle());
        Gson gson = new Gson();
        Type listOfObject = new TypeToken<List<RecipeCompositionModel>>() {
        }.getType();
        List<RecipeCompositionModel> recipeCompositionModels = gson.fromJson(
                responseFullRecipes.getSostav(), listOfObject);
        StringBuilder sb = new StringBuilder();
        try {
            for (RecipeCompositionModel items : recipeCompositionModels) {
                sb.append(".").append(" ").append(items.getName()).append(" ").append(items.getValue()).append(System.getProperty("line.separator"));
            }
        } catch (Exception e){
            sb.append("-");
        }
        compositionRecipe.setText(sb.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cookingMethodFull.setText(Html.fromHtml(responseFullRecipes.getContent(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            cookingMethodFull.setText(Html.fromHtml(responseFullRecipes.getContent()));
        }
    }
}
