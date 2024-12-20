package com.abdul.fatma.hamza.sahan.task;

import java.util.*;

public class ScheduledTask {    private String name;
    private int day, month, year;

    public ScheduledTask(String name, int day, int month, int year) {
        this.name = name;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}