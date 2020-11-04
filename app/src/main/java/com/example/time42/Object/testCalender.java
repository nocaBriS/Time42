package com.example.time42.Object;

public class testCalender {

    String name;
    String Datum1;
    String Datum2 = null;

    public testCalender(String name, String datum1, String datum2) {
        this.name = name;
        Datum1 = datum1;
        Datum2 = datum2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatum1() {
        return Datum1;
    }

    public void setDatum1(String datum1) {
        Datum1 = datum1;
    }

    public String getDatum2() {
        return Datum2;
    }

    public void setDatum2(String datum2) {
        Datum2 = datum2;
    }
}
