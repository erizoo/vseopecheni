package ru.vseopecheni.app.ui.fragments.recipes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.ui.base.BaseActivity;
import ru.vseopecheni.app.ui.base.BaseFragment;

public class FullRecipeFragment extends BaseFragment implements FullRecipeMvpView {

    @Inject
    FullRecipePresenter<FullRecipeMvpView> presenter;

    @BindView(R.id.full_recipe_imageView)
    ImageView imageView;
    @BindView(R.id.title_textView)
    TextView title;

    private Unbinder unbinder;
    private String id;

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
        presenter.onAttach(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("id");
            presenter.getFullRecipe(id);
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onFullRecipeUpdated(List<ResponseFullRecipes> responseFullRecipes) {
        Glide.with(Objects.requireNonNull(getContext()))
                .asBitmap()
                .load("https://vseopecheni.ru" + responseFullRecipes.get(0).getImage())
                .apply(RequestOptions.circleCropTransform())
                .apply(new RequestOptions().fitCenter())
                .into(imageView);
        title.setText(responseFullRecipes.get(0).getTitle());
    }
}
