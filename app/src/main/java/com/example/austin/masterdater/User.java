package com.example.austin.masterdater;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table USER.
 */
public class User {

    private String Name;
    private String PhoneNumber;

    /** Used for active entity operations. */
//    private List<CalendarEvent> calendarEventList;
    private ArrayList<Date> calendarEventList;

    public User() {
    }


    public User(String Name, String PhoneNumber, ArrayList<Date> ce) {
        this.Name = Name;
        this.PhoneNumber = PhoneNumber;
        this.calendarEventList = ce;
    }

    /** called by internal mechanisms, do not call yourself. */
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public ArrayList<Date> getCalendarEventList(){
        return this.calendarEventList;
    }

    public void addCalendarEvent(Date d){
         calendarEventList.add(d);
    }

    public void setCalendarEventList(ArrayList<Date> D) { this.calendarEventList = D;}




}
