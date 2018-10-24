package ru.vseopecheni.app.ui.fragments.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.data.models.ResponseMenuForWeek;
import ru.vseopecheni.app.ui.base.BaseViewHolder;

public class MenuForWeekAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<ResponseMenuForWeek> menuForWeekList = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuForWeekAdapter.MenuForWeekViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_for_week, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return menuForWeekList.size();
    }

    public class MenuForWeekViewHolder extends BaseViewHolder {

        private Context context;

        @BindView(R.id.type_menu_rv)
        TextView typeMenu;
        @BindView(R.id.image_menu)
        ImageView imageView;
        @BindView(R.id.title_recipe_rv)
        TextView title;

        MenuForWeekViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
        }
    }
}
