package com.example.austin.masterdater;


        import java.util.List;

        import retrofit2.Call;
        import retrofit2.http.GET;

public interface CalendarClient {

    @GET("/calendar")
    Call<List<CalendarEvent>> Events();



}

