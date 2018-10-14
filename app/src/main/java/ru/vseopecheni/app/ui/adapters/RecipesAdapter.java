package ru.vseopecheni.app.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseRecipes;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseViewHolder;
import ru.vseopecheni.app.ui.fragments.recipes.FullRecipeFragment;

public class RecipesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ResponseRecipes> responseRecipes = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecipesAdapter.RecipesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recipes_rv, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return responseRecipes.size();
    }

    public Context context;

    public void setItems(List<ResponseRecipes> responseRecipes) {
        this.responseRecipes.addAll(responseRecipes);
    }

    public class RecipesViewHolder extends BaseViewHolder {

        @BindView(R.id.image_recipes)
        ImageView imageViewRecipes;

        public RecipesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @Override
        public void onBind(int position) {
            Glide.with(context)
                    .asBitmap()
                    .load(responseRecipes.get(position).getImage())
                    .apply(new RequestOptions().fitCenter())
                    .into(imageViewRecipes);
            imageViewRecipes.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString("id", responseRecipes.get(position).getId());
                FullRecipeFragment fullRecipeFragment = new FullRecipeFragment();
                fullRecipeFragment.setArguments(bundle);
                ((MainActivity)Objects.requireNonNull(context)).moveToNewFragment(fullRecipeFragment);
            });
        }
    }


}
