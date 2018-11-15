package ru.vseopecheni.app.utils;

import ru.vseopecheni.app.ui.fragments.alarm.AlarmModel;

public interface IDatabaseHandler {

    public void addAlarm(AlarmModel alarmModel);
    public int updateAlarm(AlarmModel alarmModel);
    public void deleteAlarm(AlarmModel alarmModel);

}
