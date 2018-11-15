package ru.vseopecheni.app.ui.fragments.alarm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;
import ru.vseopecheni.app.utils.NotificationAlarm;

public class AlarmFragment extends BaseFragment implements AlarmFoodAdapter.Callback {

    @BindView(R.id.medication_button)
    Button medicationButton;
    @BindView(R.id.food_intake_button)
    Button foodIntakeButton;
    @BindView(R.id.food_intake_rv)
    TextView foodIntakeText;
    @BindView(R.id.medication_button_rv)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private Button saveButton;
    private Button closeButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        foodIntakeText.setText(Constant.getFromSharedPreference("FOOD_ALARM_TIME",
                Objects.requireNonNull(getActivity())));
        return v;
    }

    @OnClick(R.id.medication_button)
    public void showMedication() {
        showPopupMenuAlarm(getView());
    }

    @OnClick(R.id.food_intake_button)
    public void showFoodPopup() {
        showPopupFoodIntake(Objects.requireNonNull(getView()));
    }

    private void showPopupFoodIntake(View v) {
        @SuppressLint("InflateParams") View popupView = LayoutInflater
                .from(getContext()).inflate(R.layout.popup_food, null);
        PopupWindow alarmPopup = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        alarmPopup.setElevation(8.0f);
        alarmPopup.setFocusable(true);
        alarmPopup.showAtLocation(v.findViewById(R.id.food_intake_button), Gravity.CENTER, 0, 0);
        EditText hour = popupView.findViewById(R.id.hours);
        EditText minute = popupView.findViewById(R.id.minutes);
        saveButton = popupView.findViewById(R.id.save_button);
        closeButton = popupView.findViewById(R.id.close_button);
        saveButton.setOnClickListener(v1 -> {
            Calendar time = getTime(hour.getText().toString() + ":"
                    + minute.getText().toString(), 0);
            Calendar time2 = getTime(hour.getText().toString() + ":"
                    + minute.getText().toString(), 2);
            Calendar time3 = getTime(hour.getText().toString() + ":"
                    + minute.getText().toString(), 4);
            Calendar time4 = getTime(hour.getText().toString() + ":"
                    + minute.getText().toString(), 6);
            Calendar time5 = getTime(hour.getText().toString() + ":"
                    + minute.getText().toString(), 8);
            startAlarm(time, time2, time3, time4, time5);
            medicationButton.setEnabled(true);
            alarmPopup.dismiss();
        });
        closeButton.setOnClickListener(v1 -> {
            alarmPopup.dismiss();
            medicationButton.setEnabled(true);
        });
    }

    private void showPopupMenuAlarm(View v) {

    }

    private void startAlarm(Calendar time, Calendar time2, Calendar time3, Calendar time4, Calendar time5) {
        NotificationAlarm.SheduleNotification(getContext(), time,
                "Время кушать!)", "1", 101);
        NotificationAlarm.SheduleNotification(getContext(), time2,
                "Время кушать!)", "2", 102);
        NotificationAlarm.SheduleNotification(getContext(), time3,
                "Время кушать!)", "3", 103);
        NotificationAlarm.SheduleNotification(getContext(), time4,
                "Время кушать!)", "4", 104);
        NotificationAlarm.SheduleNotification(getContext(), time5,
                "Время кушать!)", "5", 105);
        String sb = String.valueOf(time.get(Calendar.HOUR_OF_DAY)) + ":" + time.get(Calendar.MINUTE) + "," +
                time2.get(Calendar.HOUR_OF_DAY) + ":" + time2.get(Calendar.MINUTE) + "," +
                time3.get(Calendar.HOUR_OF_DAY) + ":" + time3.get(Calendar.MINUTE) + "," +
                time4.get(Calendar.HOUR_OF_DAY) + ":" + time4.get(Calendar.MINUTE) + "," +
                time5.get(Calendar.HOUR_OF_DAY) + ":" + time5.get(Calendar.MINUTE);
        foodIntakeText.setText(sb);
        Constant.saveToSharedPreference("FOOD_ALARM_TIME", sb, Objects.requireNonNull(getActivity()));
    }

    private Calendar getTime(String time, int hours) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.MINUTE, hours);
        return cal;
    }

    @Override
    public void editAlarm(AlarmModel alarmModel) {

    }

    @Override
    public void deleteItemFromAlarm(List<AlarmModel> alarmModels) {

    }

}

