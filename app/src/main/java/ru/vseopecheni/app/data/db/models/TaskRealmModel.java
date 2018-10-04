package ru.vseopecheni.app.data.db.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;

public class TaskRealmModel extends RealmObject {

    @PrimaryKey
    private String id;
    private String title;

    public TaskRealmModel() {
    }

    public TaskRealmModel(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
