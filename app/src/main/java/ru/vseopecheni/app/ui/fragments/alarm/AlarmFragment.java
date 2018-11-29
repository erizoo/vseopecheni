package ru.vseopecheni.app.ui.fragments.alarm;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ru.vseopecheni.app.R;
import ru.vseopecheni.app.ui.base.BaseFragment;
import ru.vseopecheni.app.utils.Constant;
import ru.vseopecheni.app.utils.DatabaseHandler;
import ru.vseopecheni.app.utils.InputFilterMinMax;
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
    @BindView(R.id.layout_rl)
    RelativeLayout relativeLayout;

    private Unbinder unbinder;
    private Button saveButton;
    private Button closeButton;
    private DatabaseHandler db;
    private AlarmFoodAdapter alarmFoodAdapter;

    private EditText firstTimeHour;
    private EditText secondTimeHour;
    private EditText thirdTimeHour;
    private EditText firstTimeMinutes;
    private EditText secondTimeMinutes;
    private EditText thirdTimeMinutes;

    private boolean isFirst;
    private boolean isSecond;
    private boolean isThird;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alarm_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);
        db = new DatabaseHandler(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        alarmFoodAdapter = new AlarmFoodAdapter();
        alarmFoodAdapter.setCallback(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(alarmFoodAdapter);

        alarmFoodAdapter.setItems(db.getAllAlarms());

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
            Calendar time = getTime(hour.getText().toString(), minute.getText().toString(), 0);
            Calendar time2 = getTime(hour.getText().toString(), minute.getText().toString(), 3);
            Calendar time3 = getTime(hour.getText().toString(), minute.getText().toString(), 6);
            Calendar time4 = getTime(hour.getText().toString(), minute.getText().toString(), 9);
            Calendar time5 = getTime(hour.getText().toString(), minute.getText().toString(), 12);
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
        @SuppressLint("InflateParams") View popupView = LayoutInflater
                .from(getContext()).inflate(R.layout.popup, null);
        PopupWindow alarmPopup = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        alarmPopup.setElevation(8.0f);
        alarmPopup.setFocusable(true);
        alarmPopup.showAtLocation(v.findViewById(R.id.food_intake_button), Gravity.CENTER, 0, 0);
        EditText name = popupView.findViewById(R.id.medicine_name);
        firstTimeHour = popupView.findViewById(R.id.hoursFirstTime);
        firstTimeHour.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});
        secondTimeHour = popupView.findViewById(R.id.hoursSecondTime);
        secondTimeHour.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});
        thirdTimeHour = popupView.findViewById(R.id.hoursThirdTime);
        thirdTimeHour.setFilters(new InputFilter[]{new InputFilterMinMax("0", "23")});
        firstTimeMinutes = popupView.findViewById(R.id.minutesFirstTime);
        firstTimeMinutes.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        secondTimeMinutes = popupView.findViewById(R.id.minutesSecondTime);
        secondTimeMinutes.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        thirdTimeMinutes = popupView.findViewById(R.id.minutesThirdTime);
        thirdTimeMinutes.setFilters(new InputFilter[]{new InputFilterMinMax("0", "59")});
        popupView.findViewById(R.id.save_button).setOnClickListener(v1 -> {
            String firstTime = null;
            String secondTime = null;
            String thirdTime = null;
            if (!name.getText().toString().equals("")) {
                try {
                    if (firstTimeHour.getText().toString().equals("") ||
                            firstTimeMinutes.getText().toString().equals("")) {
                        isFirst = false;
                        Toast.makeText(getContext(), "Неправильно введено первое время", Toast.LENGTH_LONG).show();
                    } else if (!firstTimeHour.getText().toString().equals("") &&
                            firstTimeMinutes.getText().toString().equals("")) {
                        isFirst = false;
                        Toast.makeText(getContext(), "Неправильно введено первое время", Toast.LENGTH_LONG).show();
                    } else if (firstTimeHour.getText().toString().equals("") &&
                            !firstTimeMinutes.getText().toString().equals("")) {
                        isFirst = false;
                        Toast.makeText(getContext(), "Неправильно введено первое время", Toast.LENGTH_LONG).show();
                    } else {
                        isFirst = true;
                        firstTime = "Первый прием: " + firstTimeHour.getText().toString() + ":" + firstTimeMinutes.getText().toString();
                    }

                    if (!secondTimeHour.getText().toString().equals("") &&
                            secondTimeMinutes.getText().toString().equals("")) {
                        isSecond = false;
                        Toast.makeText(getContext(), "Неправильно введено второе время", Toast.LENGTH_LONG).show();
                    } else if (secondTimeHour.getText().toString().equals("") &&
                            !secondTimeMinutes.getText().toString().equals("")) {
                        isSecond = false;
                        Toast.makeText(getContext(), "Неправильно введено второе время", Toast.LENGTH_LONG).show();
                    } else {
                        secondTime = "Второй прием: " + secondTimeHour.getText().toString() + ":" + secondTimeMinutes.getText().toString();
                        isSecond = true;
                    }

                    if (!thirdTimeHour.getText().toString().equals("") &&
                            thirdTimeMinutes.getText().toString().equals("")) {
                        isThird = false;
                        Toast.makeText(getContext(), "Неправильно введено третье время", Toast.LENGTH_LONG).show();
                    } else if (thirdTimeHour.getText().toString().equals("") &&
                            !thirdTimeMinutes.getText().toString().equals("")) {
                        isThird = false;
                        Toast.makeText(getContext(), "Неправильно введено третье время", Toast.LENGTH_LONG).show();
                    } else {
                        thirdTime = "Третий прием: " + thirdTimeHour.getText().toString() + ":" + thirdTimeMinutes.getText().toString();
                        isThird = true;
                    }
                    if (!isFirst || !isSecond || !isThird) {
                        Toast.makeText(getContext(), "Неправильно введено время", Toast.LENGTH_LONG).show();
                    } else {
                        AlarmModel alarmModel = new AlarmModel(name.getText().toString(), firstTime, secondTime,
                                thirdTime, 0, 0, 0);
                        try {
                            db.addAlarm(alarmModel);
                            alarmFoodAdapter.setItems(db.getAllAlarms());
                            alarmPopup.dismiss();
                            if (isFirst) {
                                int idAlarm = (int) System.currentTimeMillis();
                                NotificationAlarm.scheduleNotification(getContext(), getTime(firstTimeHour.getText().toString(),
                                        firstTimeMinutes.getText().toString(), 0),
                                        name.getText().toString(), "Время принять лекарство", idAlarm);
                                alarmModel.setFirstTimeId(idAlarm);
                                db.updateAlarm(alarmModel);
                            }
                            if (isFirst && isSecond) {
                                int idAlarm = (int) System.currentTimeMillis();
                                NotificationAlarm.scheduleNotification(getContext(), getTime(secondTimeHour.getText().toString(),
                                        secondTimeMinutes.getText().toString(), 0),
                                        name.getText().toString(), "Время принять лекарство", idAlarm);
                                alarmModel.setSecondTimeId(idAlarm);
                                db.updateAlarm(alarmModel);
                            }
                            if (isFirst && isSecond && isThird) {
                                int idAlarm = (int) System.currentTimeMillis();
                                NotificationAlarm.scheduleNotification(getContext(), getTime(thirdTimeHour.getText().toString(),
                                        thirdTimeMinutes.getText().toString(), 0),
                                        name.getText().toString(), "Время принять лекарство", idAlarm);
                                alarmModel.setThirdTimeId(idAlarm);
                                db.updateAlarm(alarmModel);
                            }
                        }catch (Exception e){
                            alarmPopup.dismiss();
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Неправильно введено время", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getContext(), "Введите название лекарства", Toast.LENGTH_LONG).show();
            }
        });
        popupView.findViewById(R.id.close_button).setOnClickListener(v2 -> {
            alarmPopup.dismiss();
        });
    }

    @OnClick(R.id.imageButton)
    public void cancelAlarmFood(){
        NotificationAlarm.cancelReminder(getContext(), 101);
        NotificationAlarm.cancelReminder(getContext(), 102);
        NotificationAlarm.cancelReminder(getContext(), 103);
        NotificationAlarm.cancelReminder(getContext(), 104);
        NotificationAlarm.cancelReminder(getContext(), 105);
        relativeLayout.setVisibility(View.GONE);
        foodIntakeText.setText("");
    }

    private void startAlarm(Calendar time, Calendar time2, Calendar time3, Calendar time4, Calendar time5) {
        NotificationAlarm.scheduleNotification(getContext(), time,
                "Время кушать!)", "1", 101);
        NotificationAlarm.scheduleNotification(getContext(), time2,
                "Время кушать!)", "2", 102);
        NotificationAlarm.scheduleNotification(getContext(), time3,
                "Время кушать!)", "3", 103);
        NotificationAlarm.scheduleNotification(getContext(), time4,
                "Время кушать!)", "4", 104);
        NotificationAlarm.scheduleNotification(getContext(), time5,
                "Время кушать!)", "5", 105);
        String sb = String.valueOf(time.get(Calendar.HOUR_OF_DAY)) + ":" + time.get(Calendar.MINUTE) + "," +
                time2.get(Calendar.HOUR_OF_DAY) + ":" + time2.get(Calendar.MINUTE) + "," +
                time3.get(Calendar.HOUR_OF_DAY) + ":" + time3.get(Calendar.MINUTE) + "," +
                time4.get(Calendar.HOUR_OF_DAY) + ":" + time4.get(Calendar.MINUTE) + "," +
                time5.get(Calendar.HOUR_OF_DAY) + ":" + time5.get(Calendar.MINUTE);
        relativeLayout.setVisibility(View.VISIBLE);
        foodIntakeText.setText(sb);
        Constant.saveToSharedPreference("FOOD_ALARM_TIME", sb, Objects.requireNonNull(getActivity()));
    }

    private Calendar getTime(String hour, String minutes, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        cal.set(Calendar.MINUTE, Integer.parseInt(minutes));
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal;
    }

    @Override
    public void editAlarm(AlarmModel alarmModel) {
        db.updateAlarm(alarmModel);
    }

    @Override
    public void deleteItemFromAlarm(AlarmModel alarmModels) {
        if (alarmModels.getFirstTimeId() != 0){
            NotificationAlarm.cancelReminder(getContext(), alarmModels.getFirstTimeId());
        }
        if (alarmModels.getSecondTimeId() != 0){
            NotificationAlarm.cancelReminder(getContext(), alarmModels.getSecondTimeId());
        }
        if (alarmModels.getThirdTimeId() != 0){
            NotificationAlarm.cancelReminder(getContext(), alarmModels.getThirdTimeId());
        }
        db.deleteAlarm(alarmModels);
    }

}

