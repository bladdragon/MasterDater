package com.example.austin.masterdater;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.GET;


/**
 * Created by Austin on 3/26/2016.
 */
public class CalendarActivity  extends AppCompatActivity {

    private ArrayList<CalendarEvent> EventList;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        ActionBar ab = getSupportActionBar();

        ab.setDisplayHomeAsUpEnabled(true);

        EventList = new ArrayList<>();
        adapter = new CustomAdapter(this, EventList);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer2, CalendarFragment.newInstance(null, null))
                .addToBackStack(null)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (item.getItemId()) {

            case R.id.action_NFC:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer2, NFCFragment.newInstance(null, null))
                        .addToBackStack(null)
                        .commit();
                return true;

            case R.id.action_contacts:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer2, ShareByContactsFragment.newInstance(null, null))
                        .addToBackStack(null)
                        .commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    private void getCalendarEventsRetro(String username, int password){

        //create the REST client
        CalendarClient calendarService = ServiceGenerator.createService(CalendarClient.class);
        Call<List<CalendarEvent>> call = calendarService.Events();

        call.enqueue(new Callback<List<CalendarEvent>>() {

            @Override
            public void onResponse(Call<List<CalendarEvent>> call, Response<List<CalendarEvent>> response) {
                if (response.isSuccess()) {
                    Log.d("HTTP_GET_RESPONSE", response.raw().toString());
                    EventList.addAll(response.body());

                } else {
                    // error response, no access to resource?
                    Log.d("HTTP_GET_RESPONSE", response.raw().toString());

                    //TODO do something to say you got nothing back, maybe not

                }
            }

            @Override
            public void onFailure(Call<List<CalendarEvent>> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.d("Error", t.getMessage());

                //TODO do something to handle failure

            }
        });
    }

    public void deleteCalendarEvent(CalendarEvent calendarEvent){
//        Long i = calendarEvent.getId();

      //  @DELETE(i)

    }

//    private void testCase1(){
//        //fetch backend stuffs using some s
//        Gson gson = new GsonBuilder().create();
//        CalendarEvent CalendarEvents = gson.fromJson(readJSON(getResources(), R.raw.simple_json), CalendarEvent.class);
//        if(CalendarEvents == null){
//            Toast.makeText(this, "DIDN'T WORK", Toast.LENGTH_SHORT).show();
//        }else {
//            EventList.add(CalendarEvents);
//            adapter.notifyDataSetChanged();
//        }
//
//    }
//
//    private void testCase2(){
//        Gson gson = new GsonBuilder().create();
//        CalendarEvent[] CalendarEvents = gson.fromJson(readJSON(getResources(), R.raw.multi_json), CalendarEvent[].class);
//        if(CalendarEvents == null){
//            Toast.makeText(this, "DIDN'T WORK", Toast.LENGTH_SHORT).show();
//        }else {
//            EventList.addAll(Arrays.asList(CalendarEvents));
//            adapter.notifyDataSetChanged();
//        }
//
//    }

    /**
     * Read from a resources file and create a object that will allow the creation of other
     * objects from this resource.
     *
     * @param resources An application {@link Resources} object.
     * @param id The id for the resource to load, typically held in the raw/ folder.
     */
    public String readJSON(Resources resources, int id) {
        InputStream inputStream = resources.openRawResource(id);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toString();
    }

}
