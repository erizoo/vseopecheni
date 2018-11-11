package ru.vseopecheni.app.ui.fragments.alarm;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.base.BaseViewHolder;

public class AlarmFoodAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<AlarmModel> alarmModels = new ArrayList<>();

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
        return alarmModels.size();
    }

    public void setItems(List<AlarmModel> alarmModels) {
        this.alarmModels.addAll(alarmModels);
        notifyDataSetChanged();
    }

    public void setItem(AlarmModel alarmModel) {
        this.alarmModels.add(alarmModel);
        notifyDataSetChanged();
    }

    public class AlarmFoodViewHolder extends BaseViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        private Context context;
        private String firstTime;
        private String secondTime;
        private String thirdTime;

        AlarmFoodViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = itemView.getContext();
        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            name.setText(alarmModels.get(position).getName());
            try {
                firstTime = alarmModels.get(position).getTimes().get(0);
            } catch (Exception e) {
                firstTime = "";
            }
            try {
                secondTime = alarmModels.get(position).getTimes().get(1);
            } catch (Exception e) {
                secondTime = "";
            }
            try {
                thirdTime = alarmModels.get(position).getTimes().get(2);
            } catch (Exception e) {
                thirdTime = "";
            }
            time.setText(firstTime + "\n" + secondTime + "\n" + thirdTime);
        }
    }
}