package com.example.austin.masterdater;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;
import android.widget.ListView;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


//http://stackoverflow.com/questions/29292689/calendarview-in-android
public class CalendarActivity extends AppCompatActivity {
    Calendar cal;
    private CalendarView calendar;
    static final long ONE_MINUTE_IN_MILLIS=60000;
    Long date;
    static ArrayList<EventKeeper> EventList = new ArrayList<EventKeeper>();
    static ArrayList<EventKeeper> FriendEventList = new ArrayList<EventKeeper>();
    static private User userFriend;
    static private Firebase mRef;
    private static String MyNumber = "";
    private static String FriendNumber = "";


    public static void setFriendNumber(String friendNumber) {
        FriendNumber = friendNumber;
    }

    public static void setMyNumber(String myNumber) {
        MyNumber = myNumber;
    }

    public static String getFriendNumber() {
        return FriendNumber;
    }

    public static String getMyNumber() {
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

        Firebase.setAndroidContext(this);
        mRef = new Firebase("https://datesync.firebaseio.com");
        getEvents();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.clear();
        menu.add(R.id.action_NFC, 0, 100, "Add by NFC");
        menu.add(R.id.action_contacts, 0,100, "Add by Contacts");
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

        return super.onOptionsItemSelected(item);
    }


    public void getEvents() {

        ArrayList<Date> pushArray = new ArrayList<Date>();
        Cursor calCursor;
        ContentResolver contentResolver = this.getContentResolver();
        String[] proj = new String[]{CalendarContract.Events._ID, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.RRULE, CalendarContract.Events.TITLE};
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            //Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        calCursor = contentResolver.query(CalendarContract.Events.CONTENT_URI, proj, null, null, null);


        while (calCursor.moveToNext()) {
            String title = calCursor.getString(4);
            long beginVal = calCursor.getLong(1);
            //(1) GO through all events.(2)Add events with current date into array.

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(beginVal);
            Date startDate = calendar2.getTime();
            Date endDate = new Date(calCursor.getLong(2));

            //if pushArra % 2 = 0. this means you have the start date
            //if pushArra % 2 = 1, this means you have the end date.
            pushArray.add(startDate);
            pushArray.add(endDate);

            //PUSH OUT THE PUSH ARRAY

            EventList.add(new EventKeeper(startDate, endDate, title));
        }
        // push "PUSHARRAY" to the server here/////////////////
        User thisUser = LoginFragment.getThisUser();
        thisUser.setCalendarEventList(pushArray);
        mRef.child(thisUser.getPhoneNumber()).setValue(thisUser);
        ///////////////////////////////////////////////////////////
    }

    public static void getFriendEvents(){
        //User userFriend = LoginFragment.getThisUser();
        mRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot friendSnapshot : dataSnapshot.getChildren()){
                    if(friendSnapshot.getKey().equals(FriendNumber)){
                        System.out.println("Austin: " + "we got into the loop");
                        ArrayList<Date> receivedArray123 = new ArrayList<Date>();
                        userFriend = friendSnapshot.getValue(User.class);
                       receivedArray123 = userFriend.getCalendarEventList();

                        for(int i = 0; i< receivedArray123.size(); i++){
                            Date start = receivedArray123.get(i);
                            i++;
                            Date end = receivedArray123.get(i);
                            FriendEventList.add(new EventKeeper(start, end));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public static void clearFriendList(){
        FriendEventList.clear();
    }


    public static TimeSlot[] getEvents(Date date, String type) {
        //USEAGE:
            //type = true means
        ArrayList<EventKeeper> receivedArray = new ArrayList<EventKeeper>();

        if(type.toUpperCase().equals("FRIEND")){
            receivedArray = FriendEventList;
        }else if(type.toUpperCase().equals("USER")){
            receivedArray = EventList;
        }

        TimeSlot[] dateArray = new TimeSlot[49];

        for (int i = 0; i < dateArray.length; i++) {
            if (i % 2 == 1) {
                dateArray[i] = new TimeSlot(new Time(i / 2, 30, 0), false);
            } else {
                dateArray[i] = new TimeSlot(new Time(i / 2, 0, 0), false);
            }
        }

        for(int i = 0; i<receivedArray.size(); i++){
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

            Date StartDate = receivedArray.get(i).getStartDate();
            Date EndDate = receivedArray.get(i).getEndDate();
            Date startDateCompressed = null;
            try {
                startDateCompressed = formatter.parse(formatter.format(StartDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date endDateCompressed = null;
            try {
                endDateCompressed = formatter.parse(formatter.format(EndDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date currDateCompressed = null;
            try {
                currDateCompressed = formatter.parse(formatter.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }


            //TODO: Handle case where event extends all-day.
            //if the startingDate is less than the current Date and the endingDate is greater than the current Date
            if(startDateCompressed.compareTo(currDateCompressed) < 0  && endDateCompressed.compareTo(currDateCompressed) > 1 ){
                dateArray[49].setActive(true);
            }else if(startDateCompressed.compareTo(currDateCompressed) == 0 && endDateCompressed.compareTo(currDateCompressed) == 0){
                int startIndex = dateHashFunction(StartDate, false);
                int endIndex = dateHashFunction(EndDate, true);
                int centerIndex = startIndex + (endIndex-startIndex)/2;
                for(int j = startIndex; j<= endIndex; j++){
                    dateArray[j].setActive(true);
                    if(j == centerIndex) dateArray[j].setCenter();
                }
            }else if(startDateCompressed.compareTo(currDateCompressed) < 0 && endDateCompressed.compareTo(currDateCompressed) == 0){
                int endIndex = dateHashFunction(EndDate, true);
                int centerIndex =(endIndex)/2;
                for(int j = 0; j<=endIndex; j++){
                    dateArray[j].setActive(true);
                    if(j == centerIndex) dateArray[j].setCenter();
                }
            }else if(startDateCompressed.compareTo(currDateCompressed) == 0 && endDateCompressed.compareTo(currDateCompressed) > 0){
                int startIndex = dateHashFunction(StartDate, false);
                int centerIndex = startIndex+ (48-startIndex)/2;
                for(int j = startIndex; j<=48; j++){
                    dateArray[j].setActive(true);
                    if(j == centerIndex) dateArray[j].setCenter();
                }
            }
        }

        return dateArray;
    }


    public static int dateHashFunction(Date d, boolean ceiling){
        /*
        5:30 = (5*2 = 10) + 1 = 11
        //ceiling = true = increment
            1. is the minute mark less than 30?
                 1. end at h:30
            2. Otherwise:
                 1. end at  h+1
            4:45 = 5:00
            4:20 = 4:30
        //ceiling = false = decrement
            1. Is the minute mark less than 30?
                1. Start at h
            2. Otherwise:
                1. Start at h:30
            4:45 = 4:30
            4:14 = 4:00
         */
        int hour = d.getHours();
        int minutes = d.getMinutes();

        if(ceiling == false){
            if(minutes >= 30){
                return (hour*2) + 1;
            }else{
                return hour*2;
            }
        }else{
            if(minutes > 30){
                return (hour + 1) *2-1;
            }else if(minutes == 0){
                return (hour * 2)-1;
            }else{
                return (hour *2);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
