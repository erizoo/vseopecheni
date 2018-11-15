package ru.vseopecheni.app.ui.fragments.alarm;

import java.util.List;

public class AlarmModel {

    private int id;
    private String name;
    private List<String> times;

    public AlarmModel() {
    }

    public AlarmModel(String name, List<String> times) {
        this.name = name;
        this.times = times;
    }

    public AlarmModel(int id, String name, List<String> times) {
        this.id = id;
        this.name = name;
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }
}
