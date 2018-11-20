package ru.vseopecheni.app.utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ru.vseopecheni.app.ui.fragments.alarm.AlarmModel;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "alarmManagers";
    private static final String TABLE_CONTACTS = "alarms";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_FIRST_TIME = "firstTime";
    private static final String KEY_SECOND_TIME = "secondTime";
    private static final String KEY_THIRD_TIME = "thirdTime";
    private static final String KEY_FIRST_TIME_ID = "firstTimeId";
    private static final String KEY_SECOND_TIME_ID = "secondTimeId";
    private static final String KEY_THIRD_TIME_ID = "thirdTimeId";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_FIRST_TIME + " TEXT," + KEY_SECOND_TIME + " TEXT," + KEY_THIRD_TIME + " TEXT,"
                + KEY_FIRST_TIME_ID + " INTEGER," + KEY_SECOND_TIME_ID + " INTEGER,"
                + KEY_THIRD_TIME_ID + " INTEGER" + ")";
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
        values.put(KEY_FIRST_TIME, alarmModel.getFirstTime());
        values.put(KEY_SECOND_TIME, alarmModel.getSecondTime());
        values.put(KEY_THIRD_TIME, alarmModel.getThirdTime());
        values.put(KEY_FIRST_TIME_ID, alarmModel.getFirstTimeId());
        values.put(KEY_SECOND_TIME_ID, alarmModel.getSecondTimeId());
        values.put(KEY_THIRD_TIME_ID, alarmModel.getThirdTimeId());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    @Override
    public List<AlarmModel> getAllAlarms() {
        List<AlarmModel> alarmModels = new ArrayList<AlarmModel>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                AlarmModel alarmModel = new AlarmModel();
                alarmModel.setId(Integer.parseInt(cursor.getString(0)));
                alarmModel.setName(cursor.getString(1));
                alarmModel.setFirstTime(cursor.getString(2));
                alarmModel.setSecondTime(cursor.getString(3));
                alarmModel.setThirdTime(cursor.getString(4));
                alarmModel.setFirstTimeId(cursor.getInt(5));
                alarmModel.setSecondTimeId(cursor.getInt(6));
                alarmModel.setThirdTimeId(cursor.getInt(7));
                alarmModels.add(alarmModel);
            } while (cursor.moveToNext());
        }
        return alarmModels;
    }

    @Override
    public int updateAlarm(AlarmModel alarmModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, alarmModel.getName());
        values.put(KEY_FIRST_TIME, alarmModel.getFirstTime());
        values.put(KEY_SECOND_TIME, alarmModel.getSecondTime());
        values.put(KEY_THIRD_TIME, alarmModel.getThirdTime());
        values.put(KEY_FIRST_TIME_ID, alarmModel.getFirstTimeId());
        values.put(KEY_SECOND_TIME_ID, alarmModel.getSecondTimeId());
        values.put(KEY_THIRD_TIME_ID, alarmModel.getThirdTimeId());
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
