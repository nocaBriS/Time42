package com.example.time42.Object;


import java.util.Date;

public class Project {

    String name;
    Date start;
    Date end;
    int id;
    String desc;
    String owner;

    public Project(String name, Date start, Date end, int id, String desc, String owner) {
        this.name = name;
        this.start = start;
        this.end = end;
        this.id = id;
        this.desc = desc;
        this.owner = owner;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
