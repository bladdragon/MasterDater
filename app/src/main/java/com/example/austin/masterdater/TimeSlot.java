package com.example.austin.masterdater;

import java.sql.Time;

/**
 * Created by Cam on 5/5/2016.
 */
public class TimeSlot {

    Time time;
    boolean active;
    boolean isCenter;

    public TimeSlot(Time time, boolean active) {
        this.time = time;
        this.active = active;
    }

    public void setCenter(){
        this.isCenter = true;
    }
    public void removeCenter(){ this.isCenter = false;}
    public boolean isCenter(){
        return isCenter;
    }
    public boolean isActive() {
        return active;
    }

    public Time getTime() {
        return time;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setTime(Time time) {
        this.time = time;
    }

}
