package com.example.time42.Object;

public class SpinnerProjectObject {
    String id;
    String name;

    public SpinnerProjectObject() {

    }

    public SpinnerProjectObject(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
