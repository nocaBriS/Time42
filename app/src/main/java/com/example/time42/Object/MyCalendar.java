package com.example.time42.Object;

import com.google.firebase.Timestamp;

public class MyCalendar {
    String name;
    Timestamp startDate;
    Timestamp endDate;

    public MyCalendar(String name, Timestamp startDate, Timestamp endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "MyCalendar{}";
    }
}
