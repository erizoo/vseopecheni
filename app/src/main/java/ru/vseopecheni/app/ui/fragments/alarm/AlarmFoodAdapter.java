package ru.vseopecheni.app.ui.fragments.alarm;

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
import ru.vseopecheni.app.ui.base.BaseViewHolder;

public class AlarmFoodAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private String name;
    private List<String> times = new ArrayList<>();

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AlarmFoodAdapter.AlarmFoodViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm_food, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public void setItems(AlarmModel alarmModel) {
        name = alarmModel.getName();
        times.addAll(alarmModel.getTimes());
    }

    public class AlarmFoodViewHolder extends BaseViewHolder {

        private Context context;

        AlarmFoodViewHolder(View itemView) {
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