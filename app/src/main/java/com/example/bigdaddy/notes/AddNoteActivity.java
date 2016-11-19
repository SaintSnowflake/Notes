package com.example.bigdaddy.notes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

public class AddNoteActivity extends AppCompatActivity implements View.OnClickListener{
    enum NotesType {NO_REPEAT, REPEAT, EVERYDAY}
    EditText text;
    DBHelper dbHelper;
    NotesType note_type = NotesType.NO_REPEAT;
    boolean[] daysOfWeek = new boolean[] {false, false, false, false, false, false, false};
    Button day1_button, day2_button, day3_button, day4_button, day5_button, day6_button, day7_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        getSupportActionBar().hide();
        dbHelper = new DBHelper(this);
        Button add_button = (Button) findViewById(R.id.add_button);
        Button back_button = (Button) findViewById(R.id.back_button);
        Button repeat_button = (Button) findViewById(R.id.repeat_button);
        Button no_repeat_button = (Button) findViewById(R.id.no_repeat_button);
        Button everyday_button = (Button) findViewById(R.id.everyday_button);
        day1_button = (Button) findViewById(R.id.day1_button);
        day2_button = (Button) findViewById(R.id.day2_button);
        day3_button = (Button) findViewById(R.id.day3_button);
        day4_button = (Button) findViewById(R.id.day4_button);
        day5_button = (Button) findViewById(R.id.day5_button);
        day6_button = (Button) findViewById(R.id.day6_button);
        day7_button = (Button) findViewById(R.id.day7_button);
        text = (EditText) findViewById(R.id.text_add_note);
        add_button.setOnClickListener(this);
        back_button.setOnClickListener(this);
        repeat_button.setOnClickListener(this);
        no_repeat_button.setOnClickListener(this);
        everyday_button.setOnClickListener(this);
        day1_button.setOnClickListener(this);
        day2_button.setOnClickListener(this);
        day3_button.setOnClickListener(this);
        day4_button.setOnClickListener(this);
        day5_button.setOnClickListener(this);
        day6_button.setOnClickListener(this);
        day7_button.setOnClickListener(this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container);
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        Button repeat_button = (Button) findViewById(R.id.repeat_button);
        Button no_repeat_button = (Button) findViewById(R.id.no_repeat_button);
        Button everyday_button = (Button) findViewById(R.id.everyday_button);
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.container);
        switch (v.getId()) {
            case R.id.add_button:
                if (!text.getText().toString().equals("")) {
                    addNoteInDB();
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.back_button:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.no_repeat_button:
                repeat_button.setBackgroundColor(getResources().getColor(R.color.colorNote));
                no_repeat_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                everyday_button.setBackgroundColor(getResources().getColor(R.color.colorNote));
                datePicker.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                note_type = NotesType.NO_REPEAT;
                break;
            case R.id.repeat_button:
                repeat_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                no_repeat_button.setBackgroundColor(getResources().getColor(R.color.colorNote));
                everyday_button.setBackgroundColor(getResources().getColor(R.color.colorNote));
                datePicker.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                note_type = NotesType.REPEAT;
                break;
            case R.id.everyday_button:
                repeat_button.setBackgroundColor(getResources().getColor(R.color.colorNote));
                no_repeat_button.setBackgroundColor(getResources().getColor(R.color.colorNote));
                everyday_button.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                datePicker.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                note_type = NotesType.EVERYDAY;
                break;
            case R.id.day1_button:
                if (daysOfWeek[0]){
                    day1_button.setBackgroundResource(R.drawable.icon);
                    day1_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    day1_button.setBackgroundResource(R.drawable.icon_dark);
                    day1_button.setTextColor(getResources().getColor(R.color.colorHeader));
                }
                daysOfWeek[0] = !daysOfWeek[0];
                break;
            case R.id.day2_button:
                if (daysOfWeek[1]){
                    day2_button.setBackgroundResource(R.drawable.icon);
                    day2_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    day2_button.setBackgroundResource(R.drawable.icon_dark);
                    day2_button.setTextColor(getResources().getColor(R.color.colorHeader));
                }
                daysOfWeek[1] = !daysOfWeek[1];
                break;
            case R.id.day3_button:
                if (daysOfWeek[2]){
                    day3_button.setBackgroundResource(R.drawable.icon);
                    day3_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    day3_button.setBackgroundResource(R.drawable.icon_dark);
                    day3_button.setTextColor(getResources().getColor(R.color.colorHeader));
                }
                daysOfWeek[2] = !daysOfWeek[2];
                break;
            case R.id.day4_button:
                if (daysOfWeek[3]){
                    day4_button.setBackgroundResource(R.drawable.icon);
                    day4_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    day4_button.setBackgroundResource(R.drawable.icon_dark);
                    day4_button.setTextColor(getResources().getColor(R.color.colorHeader));
                }
                daysOfWeek[3] = !daysOfWeek[3];
                break;
            case R.id.day5_button:
                if (daysOfWeek[4]){
                    day5_button.setBackgroundResource(R.drawable.icon);
                    day5_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    day5_button.setBackgroundResource(R.drawable.icon_dark);
                    day5_button.setTextColor(getResources().getColor(R.color.colorHeader));
                }
                daysOfWeek[4] = !daysOfWeek[4];
                break;
            case R.id.day6_button:
                if (daysOfWeek[5]){
                    day6_button.setBackgroundResource(R.drawable.icon);
                    day6_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    day6_button.setBackgroundResource(R.drawable.icon_dark);
                    day6_button.setTextColor(getResources().getColor(R.color.colorHeader));
                }
                daysOfWeek[5] = !daysOfWeek[5];
                break;
            case R.id.day7_button:
                if (daysOfWeek[6]){
                    day7_button.setBackgroundResource(R.drawable.icon);
                    day7_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    day7_button.setBackgroundResource(R.drawable.icon_dark);
                    day7_button.setTextColor(getResources().getColor(R.color.colorHeader));
                }
                daysOfWeek[6] = !daysOfWeek[6];
                break;
        }
    }
    public void addNoteInDB(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        switch (note_type){
            case NO_REPEAT:
                DatePicker datePicker;
                datePicker = (DatePicker) findViewById(R.id.datePicker);
                contentValues.put(DBHelper.TEXT, text.getText().toString());
                int date = datePicker.getYear() * 10000;
                date += (datePicker.getMonth() + 1) * 100;
                date += datePicker.getDayOfMonth();
                contentValues.put(DBHelper.DATE, date);
                contentValues.put(DBHelper.REPEAT, 0);
                break;
            case REPEAT:
                contentValues.put(DBHelper.TEXT, text.getText().toString());
                contentValues.put(DBHelper.REPEAT, 1);
                int days = 0;
                for (int i = 0; i < 7; i++){
                    if (daysOfWeek[i])
                        days++;
                    if (days > 0)
                        days *= 10;
                }
                days /= 10;
                contentValues.put(DBHelper.DAYS, days);
                break;
            case EVERYDAY:
                contentValues.put(DBHelper.TEXT, text.getText().toString());
                contentValues.put(DBHelper.REPEAT, 2);
                break;
        }
        database.insert(DBHelper.TABLE_NAME, null, contentValues);
    }
}
