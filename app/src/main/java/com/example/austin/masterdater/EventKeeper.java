package com.example.austin.masterdater;
import java.util.Date;
/**
 * Created by kartikayala on 5/5/16.
 */
public class EventKeeper {
    Date startDate;
    Date endDate;
    String title;

    public EventKeeper(Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public EventKeeper(Date startDate, Date endDate, String title) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
