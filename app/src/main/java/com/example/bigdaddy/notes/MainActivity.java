package com.example.bigdaddy.notes;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    DBHelper dbHelper;
    Button add_note_button;
    LinearLayout container;
    LinearLayout.LayoutParams layoutParams;
    TextView note;
    Calendar calendar;
    int year, month, day, dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        dbHelper = new DBHelper(this);
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
    public void add_notes(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
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
                    addNote(cursor.getString(textIndex));
                }
                Log.d("myLog", "ID = " + cursor.getInt(idIndex) +
                        ", text = " + cursor.getString(textIndex) +
                        ", date = " + cursor.getInt(dateIndex) +
                        ", repeat = " + cursor.getInt(repeatIndex) +
                        ", days = " + cursor.getInt(daysIndex));
            } while (cursor.moveToNext());
        }
        else {
            addNote("На сегодня дел нет");
        }
        cursor.close();
    }
    public void addNote(String str){
        note = new TextView(MainActivity.this);
        note.setText(str);
        note.setLayoutParams(layoutParams);
        note.setTextSize(14);
        int paddingPx = dpToPx(18);
        note.setPadding(paddingPx, 20, 20, 20);
        note.setBackgroundColor(getResources().getColor(R.color.colorNote));
        note.invalidate();
        container.addView(note);
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
