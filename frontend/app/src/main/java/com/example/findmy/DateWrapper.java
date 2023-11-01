package com.example.findmy;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateWrapper {

    public static final DateWrapper testDate = new DateWrapper(new Date(System.currentTimeMillis()));
    private final SimpleDateFormat isoFormat;
    private final Date date;

    public DateWrapper(Date date) {
        isoFormat =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        isoFormat.setTimeZone(TimeZone.getDefault());

        this.date = date;
    }

    public String getISOString() {
        return isoFormat.format(this.date.getTime());
    }

    @NonNull
    @Override
    public String toString() {
        return isoFormat.format(this.date.getTime());
    }
}

