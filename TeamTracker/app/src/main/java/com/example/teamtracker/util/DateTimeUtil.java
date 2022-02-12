package com.example.teamtracker.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;
    private static final int MILLI = 1000;

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
}
