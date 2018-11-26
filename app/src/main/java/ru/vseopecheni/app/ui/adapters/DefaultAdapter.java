package ru.vseopecheni.app.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseFullRecipes;
import ru.vseopecheni.app.ui.base.BaseViewHolder;
import ru.vseopecheni.app.utils.Constant;

public class DefaultAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public Context context;
    private List<ResponseFullRecipes> responseFullRecipes = new ArrayList<>();
    private List<ResponseFullRecipes> filterList = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefaultAdapter.DefaultViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.items_recipes_rv, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return responseFullRecipes.size();
    }

    public void setItems(List<ResponseFullRecipes> responseFullRecipes) {
        this.responseFullRecipes.addAll(responseFullRecipes);
        filterList.addAll(responseFullRecipes);
        notifyDataSetChanged();
    }

    public void filter(String query) {
        responseFullRecipes.clear();
        if (query.isEmpty()) {
            responseFullRecipes.addAll(filterList);
        } else {
            query = query.toLowerCase();
            for (ResponseFullRecipes item : filterList) {
                if (item.getTitle().toLowerCase().contains(query) || item.getTitle().toLowerCase().contains(query)) {
                    responseFullRecipes.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class DefaultViewHolder extends BaseViewHolder {

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @Override
        public void onBind(int position) {
            if (!Constant.isInternet(context)) {
            }
        }
    }
}