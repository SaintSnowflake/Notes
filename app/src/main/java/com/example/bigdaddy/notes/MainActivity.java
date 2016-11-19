package com.example.bigdaddy.notes;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    DBHelper dbHelper;
    Button add_note_button;
    LinearLayout container;
    LinearLayout.LayoutParams layoutParams;
    TextView note;
    Calendar calendar;
    int year, month, day, dayOfWeek;
    float startX, startY;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();
        container = (LinearLayout)findViewById(R.id.container);
        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 10, 10, 0);
        add_note_button = (Button) findViewById(R.id.add_note_button);
        add_note_button.setOnClickListener(this);
        calendar = Calendar.getInstance();
        year = calendar.get(calendar.YEAR);
        month = calendar.get(calendar.MONTH) + 1;
        day = calendar.get(calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1)
            dayOfWeek = 7;
        else
            dayOfWeek--;
        delete_old_notes();
        add_notes();
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.add_note_button:
                intent = new Intent(this, AddNoteActivity.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE: // движение
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                float endX = event.getX();
                float endY = event.getY();
                if (Math.abs(endX - startX) > 300 && Math.abs(endY - startY) < 40){
                    if (startX < endX) {
                        int id = v.getId();
                        v.setVisibility(View.GONE);
                        database.delete(DBHelper.TABLE_NAME, "_id = " + id, null);
                    }
                }
                break;
        }
        return true;
    }
    public void delete_old_notes(){
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, DBHelper.DATE);
        int curDate = year * 10000 + month * 100 + day;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int dateIndex = cursor.getColumnIndex(DBHelper.DATE);
            int repeatIndex = cursor.getColumnIndex(DBHelper.REPEAT);
            do {
                if (cursor.getInt(dateIndex) < curDate && cursor.getInt(repeatIndex) == 0) {
                    database.delete(DBHelper.TABLE_NAME, "_id = " + cursor.getInt(idIndex), null);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    public void add_notes(){
        Cursor cursor = database.query(DBHelper.TABLE_NAME, null, null, null, null, null, DBHelper.DATE);
        int curDate = year * 10000 + month * 100 + day;
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);

            int textIndex = cursor.getColumnIndex(DBHelper.TEXT);
            int dateIndex = cursor.getColumnIndex(DBHelper.DATE);

            int repeatIndex = cursor.getColumnIndex(DBHelper.REPEAT);
            int daysIndex = cursor.getColumnIndex(DBHelper.DAYS);

            do {
                int days = cursor.getInt(daysIndex);
                for (int i = 0; i < 7 - dayOfWeek; i++){
                    days /= 10;
                }
                days = days % 10;
                if (cursor.getInt(dateIndex) == curDate || cursor.getInt(repeatIndex) == 2 || days == 1) {
                    addNote(cursor.getString(textIndex), cursor.getInt(idIndex));
                }
            } while (cursor.moveToNext());
        }
        else {
            addNote("На сегодня дел нет", 0);
        }
        cursor.close();
    }
    public void addNote(String str, int id){
        note = new TextView(MainActivity.this);
        note.setText(str);
        note.setId(id);
        note.setLayoutParams(layoutParams);
        note.setTextSize(14);
        note.setPadding(dpToPx(18), 20, 20, 20);
        note.setBackgroundColor(getResources().getColor(R.color.colorNote));
        note.setOnTouchListener(this);
        container.addView(note);
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
