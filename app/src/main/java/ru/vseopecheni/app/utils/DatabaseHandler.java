package ru.vseopecheni.app.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.vseopecheni.app.ui.fragments.alarm.AlarmModel;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarmManager";
    private static final String TABLE_CONTACTS = "alarms";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FIRST_TIME = "firstTime";
    private static final String KEY_SECOND_TIME = "secondTime";
    private static final String KEY_THIRD_TIME = "thirdTime";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_FIRST_TIME + " TEXT," + KEY_SECOND_TIME + " TEXT," + KEY_THIRD_TIME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    @Override
    public void addAlarm(AlarmModel alarmModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmModel.getName());
        values.put(KEY_FIRST_TIME, alarmModel.getTimes().get(0));
        values.put(KEY_SECOND_TIME, alarmModel.getTimes().get(1));
        values.put(KEY_THIRD_TIME, alarmModel.getTimes().get(2));

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    @Override
    public int updateAlarm(AlarmModel alarmModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmModel.getName());
        values.put(KEY_FIRST_TIME, alarmModel.getTimes().get(0));
        values.put(KEY_SECOND_TIME, alarmModel.getTimes().get(1));
        values.put(KEY_THIRD_TIME, alarmModel.getTimes().get(2));
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(alarmModel.getId())});
    }

    @Override
    public void deleteAlarm(AlarmModel alarmModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?", new String[]{String.valueOf(alarmModel.getId())});
        db.close();
    }

}
