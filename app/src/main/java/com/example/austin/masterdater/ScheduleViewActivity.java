package com.example.austin.masterdater;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.usage.UsageEvents.Event;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleViewActivity extends AppCompatActivity {

    public TimeSlot[] EventArray;
    public TimeSlot[] CommonArray;
    private TextView syncedUser;
    Date currDate;
    Calendar c;
    Button incrementButton;
    Button decrementButton;
    private Button syncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//               FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        syncedUser = (TextView) findViewById(R.id.currentFriendView);
        syncButton = (Button) findViewById(R.id.syncButton);
        final DateSwitcher DS = new DateSwitcher();
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        long cDate = 0;
        cDate = getIntent().getLongExtra("MESSAGE", -1);
        c = Calendar.getInstance();
        c.setTimeInMillis(cDate);
        currDate = c.getTime();

        Cursor calCursor;
        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//        EventArray = new TimeSlot[49];

        DS.set(formatter.format(currDate));
        FT.add(R.id.detailFragment, DS);
        FT.commit();

//
//        for (int i = 0; i < EventArray.length; i++) {
//            if (i % 2 == 1) {
//                EventArray[i] = new TimeSlot(new Time(i / 2, 30, 0), false);
//            } else {
//                EventArray[i] = new TimeSlot(new Time(i / 2, 0, 0), false);
//            }
//        }
        EventArray = CalendarActivity.getEvents(currDate, "user");
      //  getEvents();


        fillCalendar("user");

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO call their sync method
                Toast.makeText(v.getContext(), "Added ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCommonArray(){
        TimeSlot[] FriendArray = CalendarActivity.getEvents(currDate, "friend");
        CommonArray = new TimeSlot[49];
        for (int i = 0; i < CommonArray.length; i++) {
            if (i % 2 == 1) {
                CommonArray[i] = new TimeSlot(new Time(i / 2, 30, 0), false);
            } else {
                CommonArray[i] = new TimeSlot(new Time(i / 2, 0, 0), false);
            }
        }

        for(int i = 0; i< FriendArray.length; i++){
            if(EventArray[i].isActive() || FriendArray[i].isActive()){
                CommonArray[i].setActive(true);
            }
        }

    }

    public void Incrementor(){
        c.add(c.DATE, 1);
        currDate = c.getTime();
        EventArray = CalendarActivity.getEvents(currDate, "user");
        fillCalendar("user");
    }

    public void Decrementor(){
        c.add(c.DATE, -1);
        currDate = c.getTime();
       // getEvents();
        EventArray = CalendarActivity.getEvents(currDate, "user");
        fillCalendar("user");
    }

//    public void getEvents() {
//        Cursor calCursor;
//        ContentResolver contentResolver = this.getContentResolver();
//        String[] proj = new String[]{CalendarContract.Events._ID, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.RRULE, CalendarContract.Events.TITLE};
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        calCursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, proj, null, null, null);
//
//        for(int i = 0; i<EventArray.length; i++){
//            EventArray[i].setActive(false);
//            EventArray[i].removeCenter();
//        }
//        while (calCursor.moveToNext()) {
//            String title = calCursor.getString(4);
//            long beginVal = calCursor.getLong(1);
//            long eventID = calCursor.getLong(0);
//            //TODO:(1) GO through all events.(2)Add events with current date into array.
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTimeInMillis(beginVal);
//            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//            Date startDate = calendar.getTime();
//            Date endDate = new Date(calCursor.getLong(2));
//
//
//            Date startDateCompressed = null;
//            try {
//                startDateCompressed = formatter.parse(formatter.format(calendar.getTime()));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Date endDateCompressed = null;
//            try {
//                endDateCompressed = formatter.parse(formatter.format(calCursor.getLong(2)));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            Date currDateCompressed = null;
//            try {
//                currDateCompressed = formatter.parse(formatter.format(currDate));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            //TODO: Handle case where event extends all-day.
//            //if the startingDate is less than the current Date and the endingDate is greater than the current Date
//            if(startDateCompressed.compareTo(currDateCompressed) < 0  && endDateCompressed.compareTo(currDateCompressed) > 1 ){
//                EventArray[49].setActive(true);
//            }else if(startDateCompressed.compareTo(currDateCompressed) == 0 && endDateCompressed.compareTo(currDateCompressed) == 0){
//                int startIndex = dateHashFunction(startDate, false);
//                int endIndex = dateHashFunction(endDate, true);
//                int centerIndex = startIndex + (endIndex-startIndex)/2;
//                for(int i = startIndex; i<= endIndex; i++){
//                    EventArray[i].setActive(true);
//                    if(i == centerIndex) EventArray[i].setCenter();
//                }
//            }else if(startDateCompressed.compareTo(currDateCompressed) < 0 && endDateCompressed.compareTo(currDateCompressed) == 0){
//                int endIndex = dateHashFunction(endDate, true);
//                int centerIndex =(endIndex)/2;
//                for(int i = 0; i<=endIndex; i++){
//                    EventArray[i].setActive(true);
//                    if(i == centerIndex) EventArray[i].setCenter();
//                }
//            }else if(startDateCompressed.compareTo(currDateCompressed) == 0 && endDateCompressed.compareTo(currDateCompressed) > 0){
//                int startIndex = dateHashFunction(startDate, false);
//                int centerIndex = startIndex+ (48-startIndex)/2;
//                for(int i = startIndex; i<=48; i++){
//                    EventArray[i].setActive(true);
//                    if(i == centerIndex) EventArray[i].setCenter();
//                }
//            }
//        }
//    }

    public void fillCalendar(String type){
        final ListView tempList = (ListView) findViewById(R.id.listView);
        TimeSlot[] tempArray = new TimeSlot[49];
        if(type.toUpperCase().equals("USER")){
            tempArray = EventArray;
        }else if(type.toUpperCase().equals("COMMON")){
            getCommonArray();
            tempArray = CommonArray;
        }else{
            System.out.println("ERROR IN FILLCALENDAR OF SCHEDULEVIEWACTIVITY");
        }

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        String[] Values = new String[48];
        for (int i = 0; i < EventArray.length - 1; i++) {
            DateFormat formatter = new SimpleDateFormat("hh:mm:a");
            Values[i] = formatter.format(EventArray[i].getTime());
        }
        ArrayList<TimeSlot> adapter2 = new ArrayList<TimeSlot>();
        UserAdapter adapter = new UserAdapter(this, adapter2);
        tempList.setAdapter(adapter);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  Values);
//        tempList.setAdapter(adapter);
        for (int i = 0; i < EventArray.length - 1; i++) {
            adapter.add(EventArray[i]);
        }
        // tempList.

        // ListView Item Click Listener
        tempList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                TimeSlot itemValue = (TimeSlot) tempList.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

    public Date getDate(){
        return currDate;
    }

    public void setDate(Date d){
        this.currDate = d;
    }

//    public int dateHashFunction(Date d, boolean ceiling){
//        /*
//        5:30 = (5*2 = 10) + 1 = 11
//        //ceiling = true = increment
//            1. is the minute mark less than 30?
//                 1. end at h:30
//            2. Otherwise:
//                 1. end at  h+1
//            4:45 = 5:00
//            4:20 = 4:30
//        //ceiling = false = decrement
//            1. Is the minute mark less than 30?
//                1. Start at h
//            2. Otherwise:
//                1. Start at h:30
//            4:45 = 4:30
//            4:14 = 4:00
//         */
//        int hour = d.getHours();
//        int minutes = d.getMinutes();
//
//        if(ceiling == false){
//            if(minutes >= 30){
//                return (hour*2) + 1;
//            }else{
//                return hour*2;
//            }
//        }else{
//            if(minutes > 30){
//                return (hour + 1) *2-1;
//            }else if(minutes == 0){
//                return (hour * 2)-1;
//            }else{
//                return (hour *2);
//            }
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.clear();
        menu.add(R.id.action_NFC, 0, 100, "Add by NFC");
        menu.add(R.id.action_contacts, 0,100, "Add by Contacts");
        menu.add(R.id.action_settings, 0, 100, "Return to Calendar");

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
        if (itemID == 0 && id == R.id.action_NFC) {
            final Intent transaction = new Intent(this, NFCActivity.class);
            startActivity(transaction);

            return true;
        }
        if (itemID == 0 && id == R.id.action_contacts) {
            final Intent transaction = new Intent(this, ShareByContactsActivity.class);
            startActivity(transaction);

            return true;
        }
        if (itemID == 0 && id == R.id.action_settings) {
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!CalendarActivity.getFriendNumber().equals("")){
            syncedUser.setText("Ready to sync with: " + CalendarActivity.getFriendNumber());
        }

    }
}

//= getContentResolver().query(Calendars.CONTENT_URI, projection, Calendars.VISIBLE + " = 1", null, Calendars._ID + " ASC");
//        if (calCursor.moveToFirst()) {
//            do {
//                long id = calCursor.getLong(0);
//                String displayName = calCursor.getString(1);
//                String temp = calCursor.getString(0);
//
//                String accNme = null;
//                String accType = null;
//                String disName = null;
//
//                // Get the field values
//                calID = calCursor.getLong(0);
//                accNme = calCursor.getString(1);
//                accType = calCursor.getString(2);
//                disName = calCursor.getString(3);
//
//                Log.v("log_tag", "calId     : " + calID);
//                Log.v("log_tag", "accNme    : " + accNme);
//                Log.v("log_tag", "accType   : " + accType);
//                Log.v("log_tag", "disName   : " + disName);
//
//            } while (calCursor.moveToNext());
//        }


//        Goal == LIST all events on the current date.