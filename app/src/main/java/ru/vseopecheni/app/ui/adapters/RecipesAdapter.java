package ru.vseopecheni.app.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseRecipes;
import ru.vseopecheni.app.ui.MainActivity;
import ru.vseopecheni.app.ui.base.BaseViewHolder;
import ru.vseopecheni.app.ui.fragments.recipes.FullRecipeFragment;
import ru.vseopecheni.app.utils.ImageSaver;

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
        notifyDataSetChanged();
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
            if (isInternet()){
                new Thread(() -> {
                    Bitmap theBitmap = null;
                    try {
                        theBitmap = Glide.with(context)
                                .asBitmap()
                                .load(responseRecipes.get(position).getImage())
                                .into(500, 300)
                                .get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    new ImageSaver(context).
                            setFileName(responseRecipes.get(position).getId() + ".jpg")
                            .setDirectoryName("images")
                            .save(theBitmap);
                }).start();
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
            } else {
                Bitmap bitmap = new ImageSaver(context).
                        setFileName(responseRecipes.get(position).getId() + ".jpg")
                        .setDirectoryName("images")
                        .load();
                Glide.with(context)
                        .asBitmap()
                        .load(bitmap)
                        .apply(new RequestOptions().fitCenter())
                        .into(imageViewRecipes);
                imageViewRecipes.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("id", responseRecipes.get(position).getId());
                    bundle.putParcelable("BitmapImage", bitmap);
                    FullRecipeFragment fullRecipeFragment = new FullRecipeFragment();
                    fullRecipeFragment.setArguments(bundle);
                    ((MainActivity)Objects.requireNonNull(context)).moveToNewFragment(fullRecipeFragment);
                });
            }
        }

        public boolean isInternet(){
            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
            else {
                return false;
            }
        }
    }




}
