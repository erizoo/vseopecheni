package ru.vseopecheni.app.utils;

import java.util.List;

import ru.vseopecheni.app.ui.fragments.alarm.AlarmModel;

public interface IDatabaseHandler {

    public void addAlarm(AlarmModel alarmModel);
    public List<AlarmModel> getAllAlarms();
    public int updateAlarm(AlarmModel alarmModel);
    public void deleteAlarm(AlarmModel alarmModel);

}
