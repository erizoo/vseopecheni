package ru.vseopecheni.app.ui.fragments.alarm;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import ru.vseopecheni.app.utils.AlarmManagerBroadcastReceiver;
import ru.vseopecheni.app.utils.FifthPushNotif;
import ru.vseopecheni.app.utils.FourthPushNotif;
import ru.vseopecheni.app.utils.LocalData;
import ru.vseopecheni.app.utils.NotificationScheduler;
import ru.vseopecheni.app.utils.SecondPushNotif;
import ru.vseopecheni.app.utils.ThirdPushNotif;

import static android.content.Context.MODE_PRIVATE;

public class AlarmFragment extends BaseFragment {

    @BindView(R.id.medication_button)
    Button medicationButton;
    @BindView(R.id.food_intake_rv)
    TextView foodIntakeText;

    private Unbinder unbinder;
    private View v;
    private TextView first;
    private TextView second;
    private TextView third;
    private EditText name;
    private TextView firstTime;
    private Button saveButton;
    private Button closeButton;
    private SharedPreferences sharedPreferences;
    private String time;
    private String time2;
    private String time3;
    private String time4;
    private String time5;
    private String foodTime;
    int myHour = 14;
    int myMinute = 35;
    private String firstTimeAlarm;
    private String secondTimeAlarm;
    private String thirdTimeAlarm;
    private AlarmFoodAdapter alarmFoodAdapter;

    private List<String> times = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.alarm_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        alarmFoodAdapter = new AlarmFoodAdapter();
        sharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
        foodTime = sharedPreferences.getString("FOOD_ALARM", "");
        if (!Objects.requireNonNull(foodTime).equals("")){
            foodIntakeText.setText(foodTime);
        }
        return v;
    }

    @OnClick(R.id.food_intake_button)
    public void showFoodIntake() {
        showPopupFoodIntake(v);

    }

    @OnClick(R.id.medication_button)
    public void showMedication() {
        showPopupMenuAlarm(v);
        medicationButton.setEnabled(false);
    }

    private void showPopupFoodIntake(View v) {
        @SuppressLint("InflateParams") View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup_food, null);
        PopupWindow alarmPopup = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        alarmPopup.setElevation(8.0f);
        alarmPopup.setFocusable(false);
        alarmPopup.showAtLocation(v.findViewById(R.id.food_intake_button), Gravity.CENTER, 0, 0);
        firstTime = popupView.findViewById(R.id.time);
        saveButton = popupView.findViewById(R.id.save_button);
        closeButton = popupView.findViewById(R.id.close_button);
        firstTime.setOnClickListener(v1 -> {
            TimePickerDialog tpd = new TimePickerDialog(getContext(), myCallBack, myHour, myMinute, true);
            tpd.show();
        });
        saveButton.setOnClickListener(v1 -> {
            startAlarm(time, time2, time3, time4, time5);
            medicationButton.setEnabled(true);
            alarmPopup.dismiss();
        });
        closeButton.setOnClickListener(v1 -> {
            alarmPopup.dismiss();
            medicationButton.setEnabled(true);
        });
    }

    private void startAlarm(String time, String time2, String time3, String time4, String time5) {
        String[] str = time.split(":");
        String[] str2 = time2.split(":");
        String[] str3 = time3.split(":");
        String[] str4 = time4.split(":");
        String[] str5 = time5.split(":");
        NotificationScheduler.setReminder(getContext(), AlarmManagerBroadcastReceiver.class, Integer.parseInt(str[0]), Integer.parseInt(str[1]), 100);
        NotificationScheduler.setReminder(getContext(), SecondPushNotif.class, Integer.parseInt(str2[0]), Integer.parseInt(str2[1]), 101);
        NotificationScheduler.setReminder(getContext(), ThirdPushNotif.class, Integer.parseInt(str3[0]), Integer.parseInt(str3[1]), 102);
        NotificationScheduler.setReminder(getContext(), FourthPushNotif.class, Integer.parseInt(str4[0]), Integer.parseInt(str4[1]), 103);
        NotificationScheduler.setReminder(getContext(), FifthPushNotif.class, Integer.parseInt(str5[0]), Integer.parseInt(str5[1]), 104);
    }

    TimePickerDialog.OnTimeSetListener myCallBack = (view, hourOfDay, minute) -> {
        myHour = hourOfDay;
        myMinute = minute;
        StringBuilder sb = new StringBuilder();
        time = myHour + ":" + myMinute;
        time2 = getTime(time, 3);
        time3 = getTime(time, 6);
        time4 = getTime(time, 9);
        time5 = getTime(time, 12);
        sb.append("Приемы пищи: ").append(myHour).append(":").append(myMinute).append(",").append(" ")
                .append(time2).append(" ")
                .append(time3).append(" ")
                .append(time4).append(" ")
                .append(time5).append(" ");
        firstTime.setText(sb);
        foodIntakeText.setText(sb);
        sharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("FOOD_ALARM", sb.toString());
        ed.apply();
    };

    TimePickerDialog.OnTimeSetListener myCallBackFirst = new TimePickerDialog.OnTimeSetListener() {
        @SuppressLint("SetTextI18n")
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            first.setText("Первый прием " + myHour + " : " + myMinute);
            firstTimeAlarm = myHour + ":" + myMinute;
        }
    };

    TimePickerDialog.OnTimeSetListener myCallBackSecond = new TimePickerDialog.OnTimeSetListener() {
        @SuppressLint("SetTextI18n")
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            second.setText("Второй прием " + myHour + " : " + myMinute);
            secondTimeAlarm = myHour + ":" + myMinute;
        }
    };

    TimePickerDialog.OnTimeSetListener myCallBackThird = new TimePickerDialog.OnTimeSetListener() {
        @SuppressLint("SetTextI18n")
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
            third.setText("Третий прием " + myHour + " : " + myMinute);
            thirdTimeAlarm = myHour + ":" + myMinute;
        }
    };

    @SuppressLint("SetTextI18n")
    private void showPopupMenuAlarm(View v) {
        @SuppressLint("InflateParams") View popupView = LayoutInflater.from(getContext()).inflate(R.layout.popup, null);
        PopupWindow alarmPopup = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        alarmPopup.setElevation(8.0f);
        alarmPopup.setFocusable(true);
        alarmPopup.showAtLocation(v.findViewById(R.id.medication_button), Gravity.CENTER, 0, 0);
        first = popupView.findViewById(R.id.first_appointment);
        second = popupView.findViewById(R.id.second_appointment);
        third = popupView.findViewById(R.id.third_appointment);
        name = popupView.findViewById(R.id.medicine_name);
        saveButton = popupView.findViewById(R.id.save_button);
        closeButton = popupView.findViewById(R.id.close_button);
        first.setOnClickListener(v1 -> {
            TimePickerDialog tpd = new TimePickerDialog(getContext(), myCallBackFirst, myHour, myMinute, true);
            tpd.show();
        });
        second.setOnClickListener(v1 -> {
            TimePickerDialog tpd = new TimePickerDialog(getContext(), myCallBackSecond, myHour, myMinute, true);
            tpd.show();
        });
        third.setOnClickListener(v1 -> {
            TimePickerDialog tpd = new TimePickerDialog(getContext(), myCallBackThird, myHour, myMinute, true);
            tpd.show();
        });
        saveButton.setOnClickListener(v1 -> {
            medicationButton.setEnabled(true);
            times = new ArrayList<>();
            if (!first.getText().toString().equals("")){
                times.add(first.getText().toString());
            }
            if (!second.getText().toString().equals("")){
                times.add(second.getText().toString());
            }
            if (!third.getText().toString().equals("")){
                times.add(third.getText().toString());
            }
            AlarmModel alarmModel = new AlarmModel(name.getText().toString(), times);
            alarmFoodAdapter.setItems(alarmModel);
            alarmPopup.dismiss();
            System.out.println();
        });
        closeButton.setOnClickListener(v1 -> {
            alarmPopup.dismiss();
            medicationButton.setEnabled(true);
        });
    }

    private String getTime(String time, int hours) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = df.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.HOUR, hours);
        return df.format(cal.getTime());
    }

}
