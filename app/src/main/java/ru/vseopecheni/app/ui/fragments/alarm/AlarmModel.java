package ru.vseopecheni.app.ui.fragments.alarm;

import java.util.List;

public class AlarmModel {

    private int id;
    private String name;
    private String firstTime;
    private String secondTime;
    private String thirdTime;
    private int firstTimeId;
    private int secondTimeId;
    private int thirdTimeId;


    public AlarmModel() {
    }

    public AlarmModel(int id, String name, String firstTime, String secondTime, String thirdTime, int firstTimeId, int secondTimeId, int thirdTimeId) {
        this.id = id;
        this.name = name;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.thirdTime = thirdTime;
        this.firstTimeId = firstTimeId;
        this.secondTimeId = secondTimeId;
        this.thirdTimeId = thirdTimeId;
    }

    public AlarmModel(String name, String firstTime, String secondTime, String thirdTime, int firstTimeId, int secondTimeId, int thirdTimeId) {
        this.name = name;
        this.firstTime = firstTime;
        this.secondTime = secondTime;
        this.thirdTime = thirdTime;
        this.firstTimeId = firstTimeId;
        this.secondTimeId = secondTimeId;
        this.thirdTimeId = thirdTimeId;
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

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getSecondTime() {
        return secondTime;
    }

    public void setSecondTime(String secondTime) {
        this.secondTime = secondTime;
    }

    public String getThirdTime() {
        return thirdTime;
    }

    public void setThirdTime(String thirdTime) {
        this.thirdTime = thirdTime;
    }

    public int getFirstTimeId() {
        return firstTimeId;
    }

    public void setFirstTimeId(int firstTimeId) {
        this.firstTimeId = firstTimeId;
    }

    public int getSecondTimeId() {
        return secondTimeId;
    }

    public void setSecondTimeId(int secondTimeId) {
        this.secondTimeId = secondTimeId;
    }

    public int getThirdTimeId() {
        return thirdTimeId;
    }

    public void setThirdTimeId(int thirdTimeId) {
        this.thirdTimeId = thirdTimeId;
    }
}
