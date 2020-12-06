package com.example.time42.Object;


import java.util.Date;

public class Project {

    String name;
    Date start;
    Date end;
    int id;

    public Project(String name, Date start, Date end, int id) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}