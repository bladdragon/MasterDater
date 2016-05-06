package com.example.austin.masterdater;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.ListView;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;



//http://stackoverflow.com/questions/29292689/calendarview-in-android
public class CalendarActivity extends AppCompatActivity {
    Calendar cal;
    private CalendarView calendar;
    Long date;
    private String MyNumber;
    private String FriendNumber;

    public void setFriendNumber(String friendNumber) {
        FriendNumber = friendNumber;
    }

    public void setMyNumber(String myNumber) {
        MyNumber = myNumber;
    }

    public String getFriendNumber() {
        return FriendNumber;
    }

    public String getMyNumber() {
        return MyNumber;
    }

//////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent transaction = new Intent(this, ScheduleViewActivity.class);
        setContentView(R.layout.activity_calendar);
       // View contentView = findViewById(R.id.ContentMain1);
        calendar=(CalendarView) findViewById(R.id.calendarView1);
        date = calendar.getDate();
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                System.out.println(date);
                System.out.println(calendar.getDate());
                //if(calendar.getDate() != date){
                    //date = calendar.getDate(); //new current date
                    //date is changed on real click...do things.
                    cal = new GregorianCalendar(year, month, dayOfMonth);
                    Date temp1 = cal.getTime();

                    //Sends a message to the final activity that includes the final score
                    //Passes onto the final activity.

                    transaction.putExtra("MESSAGE", temp1.getTime());

                    startActivity(transaction);

                //}
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.clear();
        menu.add(R.id.action_settings, 0,100, "Calendar");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getGroupId();
        int itemID = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (itemID == 0 && id == R.id.action_settings) {
            final Intent transaction = new Intent(this, ScheduleViewActivity.class);
            Calendar tempCal = Calendar.getInstance();
            Date temp1 = tempCal.getTime();

            //Sends a message to the final activity that includes the final score
            //Passes onto the final activity.

            transaction.putExtra("MESSAGE", temp1.getTime());
            startActivity(transaction);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
