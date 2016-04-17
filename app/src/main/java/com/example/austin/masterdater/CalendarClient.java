package com.example.austin.masterdater;

import com.cs407_android.masterdater.CalendarEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by James on 2/26/2016.
 */
public interface CalendarClient {

    @GET("/calendar")
    Call<List<CalendarEvent>> games();



}

