package com.example.teamtracker.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;
    public static final int DAYS_IN_WEEK = 7;
    public static final int MILLI = 1000;


    public static String milliSecondToTimeFormat(Long duration) {
        String timeString = "";
        duration = duration / MILLI; // converting millisecond to second
        Long hour = duration / (SECONDS_IN_MINUTE * MINUTES_IN_HOUR); // calculating hour
        duration = duration % (SECONDS_IN_MINUTE * MINUTES_IN_HOUR); // remaining seconds
        Long minute = duration / SECONDS_IN_MINUTE; // calculating minute
        Long sec = duration % SECONDS_IN_MINUTE; //calculating second
        // making the time string
        if (hour != 0) timeString += hour + "h ";
        if (minute != 0) timeString += minute + "m ";
        if (sec != 0) timeString += sec + "s";
        else if (hour == 0 && sec == 0) timeString += sec + "s";
        return timeString;
    }

    public static String getDateFromEpoch(Long epoch) {
        DateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy hh:mm:ss a");
        Date date = new Date(epoch);
        String dateString = dateFormat.format(date);
        return dateString;
    }

    public static Long getStartEpochOfDay(Long epoch) {
        long secondInaDay = 60 * 60 * 24;
        long currentMilliSecond = epoch;
        long startEpochOfTheDay = currentMilliSecond - (currentMilliSecond %secondInaDay);
        return startEpochOfTheDay;
    }

    public static Long getEndEpochOfDay(Long epoch) {
        return (getStartEpochOfDay(epoch) + HOURS_IN_DAY*MINUTES_IN_HOUR*SECONDS_IN_MINUTE*MILLI);
    }

    public static Integer getDayFromEpoch(Long epoch) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date dateFormat = new java.util.Date(epoch);
        String weekDay = sdf.format(dateFormat);
        Integer day = -1;
        switch (weekDay) {
            case "Saturday":
                day = 0;
                break;
            case "Sunday":
                day = 1;
                break;
            case "Monday":
                day = 2;
                break;
            case "Tuesday":
                day = 3;
                break;
            case "Wednesday":
                day = 4;
                break;
            case "Thursday":
                day = 5;
                break;
            case "Friday":
                day = 6;
                break;
        }
        return day;
    }
}
