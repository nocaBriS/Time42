package com.example.time42.Object;

public class User {

    String vorname;
    String nachname;
    String email;
    String password;


    public User(String vorname, String nachname, String email, String password) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.password = password;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
