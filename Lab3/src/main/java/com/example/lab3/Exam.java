package com.example.lab3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam {
    private int id;
    private String name;
    private Date date;
    private int duration;

    public Exam(String name, Date date, int duration) {
        this.setName(name);
        this.setDate(date);
        this.setDuration(duration);
    }

    public Exam(int id, String name, Date date, int duration) {
        this.setId(id);
        this.setName(name);
        this.setDate(date);
        this.setDuration(duration);
    }

    public Exam() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public String getFormatedDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formatedDate = dateFormat.format(date);

        return formatedDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
