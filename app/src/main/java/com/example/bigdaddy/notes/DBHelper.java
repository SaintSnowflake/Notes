package com.example.bigdaddy.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper  extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "notesDB";
    public static final String TABLE_NAME = "notes";

    public static final String KEY_ID = "_id";
    public static final String TEXT = "text";
    public static final String DATE = "date";
    public static final String REPEAT = "repeat";
    public static final String DAYS = "days";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + KEY_ID
                + " integer primary key," + TEXT + " string," +
                DATE + " long," + REPEAT + " integer," + DAYS + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}
