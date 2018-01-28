package com.example.varni.mybudgie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CompactCalendarView compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        final TextView monthText = (TextView) findViewById(R.id.month_text);

        compactCalendarView.setFirstDayOfWeek(Calendar.MONDAY);
        final String[] months={"January-2018","February-2018", "March-2018", "April-2018", "May-2018", "June-2018",
        "July-2018", "August-2018", "September-2018", "October-2018", "November-2018", "December-2018"};
        int i=0;
        monthText.setText(months[i]);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                requestData(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(firstDayOfNewMonth);
                int month = cal.get(Calendar.MONTH);
                monthText.setText(months[month]);
            }
        });

        findViewById(R.id.add_entry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AddActivity.class);
                startActivity(i);
            }
        });
    }

    private void requestData(final Date date){
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        final String dateString = sdf.format(date);
        System.out.println(dateString);
        Utils.getExpenses(dateString,this,new Utils.expenseListener() {
            @Override
            public void onResult(ArrayList<Expense> expenses) {
                final Intent i = new Intent(MainActivity.this,DayActivity.class);
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                i.putExtra("month",month);
                i.putExtra("day",day);
                i.putExtra("expenses",expenses);

                Utils.getEarnings(dateString, getBaseContext(), new Utils.earningListener() {
                    @Override
                    public void onResult(ArrayList<Earning> earnings) {
                        i.putExtra("earnings",earnings);
                        startActivity(i);
                    }
                });
            }
        });
    }

    public void onClick(View view){

    }
}
