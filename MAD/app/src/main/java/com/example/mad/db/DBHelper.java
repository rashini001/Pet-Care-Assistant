package com.example.mad.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mad.models.HealthLog;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pet.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE health_logs (id INTEGER PRIMARY KEY, activity_name TEXT, date TEXT, notes TEXT, is_done INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS health_logs");
        onCreate(db);
    }

    public void insertLog(HealthLog log) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("activity_name", log.getActivityName());
        values.put("date", log.getDate());
        values.put("notes", log.getNotes());
        values.put("is_done", log.isDone() ? 1 : 0);
        db.insert("health_logs", null, values);
        db.close();
    }

    public List<HealthLog> getAllLogs() {
        List<HealthLog> logs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM health_logs", null);

        if (cursor.moveToFirst()) {
            do {
                logs.add(new HealthLog(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4) == 1
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return logs;
    }
}
