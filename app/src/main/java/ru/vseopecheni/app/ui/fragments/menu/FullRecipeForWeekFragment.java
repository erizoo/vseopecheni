package ru.vseopecheni.app.ui.fragments.menu;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseFullRecipesForMenu;
import ru.vseopecheni.app.data.models.ResponseMenuSostav;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;
import ru.vseopecheni.app.utils.ImageSaver;

public class FullRecipeForWeekFragment extends BaseFragment {

    @BindView(R.id.full_recipe_imageView)
    ImageView imageView;
    @BindView(R.id.title_textView)
    TextView title;
    @BindView(R.id.composition_recipe)
    TextView compositionRecipe;
    @BindView(R.id.cooking_method_full)
    TextView cookingMethodFull;

    private Unbinder unbinder;
    private String json;
    private String type;
    private String typeTwo;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.recipe_full_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        showLoading();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            json = bundle.getString("json");
            type = bundle.getString("type");
            typeTwo = bundle.getString("typeTwo");
        }
        Gson gson = new Gson();
        ResponseFullRecipesForMenu responseFullRecipesForMenu = gson.fromJson(json, ResponseFullRecipesForMenu.class);

        title.setText(responseFullRecipesForMenu.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (ResponseMenuSostav items : responseFullRecipesForMenu.getSostav()) {
                stringBuilder.append(".").append(" ").append(items.getName()).append("-").append(items.getValue()).append("\n");
            }
        } catch (Exception e) {
            stringBuilder.append("");
        }
        if (Constant.isInternet(getContext())) {
            Glide.with(this)
                    .asBitmap()
                    .load("https://app.vseopecheni.ru" + responseFullRecipesForMenu.getImage())
                    .apply(new RequestOptions().fitCenter())
                    .into(imageView);
        } else {
            Bitmap bitmap = new ImageSaver(getContext()).
                    setFileName(typeTwo)
                    .setDirectoryName(type)
                    .load();
            Glide.with(Objects.requireNonNull(getContext()))
                    .asBitmap()
                    .load(bitmap)
                    .apply(new RequestOptions().fitCenter())
                    .into(imageView);
        }

        compositionRecipe.setText(stringBuilder);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cookingMethodFull.setText(Html.fromHtml(responseFullRecipesForMenu.getContent(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            cookingMethodFull.setText(Html.fromHtml(responseFullRecipesForMenu.getContent()));
        }
        hideLoading();
        return v;
    }
}