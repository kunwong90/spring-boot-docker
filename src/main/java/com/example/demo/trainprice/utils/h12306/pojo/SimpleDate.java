package com.example.demo.trainprice.utils.h12306.pojo;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SimpleDate {
    int date;
    int month;
    private SimpleDateFormat simpleDateFormat;
    int year;

    public SimpleDate() {
    }

    public SimpleDate(long millis) {
        String[] p = getSimpleDateFormat().format(Long.valueOf(millis)).split("-");
        setYear(Integer.parseInt(p[0]));
        setMonth(Integer.parseInt(p[1]));
        setDate(Integer.parseInt(p[2]));
    }

    public SimpleDate(int year, int month, int date) {
        this.year = year;
        this.month = month;
        this.date = date;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return this.date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public long getTimeMillis() {
        try {
            long timeMillis = getSimpleDateFormat().parse(String.format("%d-%02d-%02d", Integer.valueOf(getYear()), Integer.valueOf(getMonth()), Integer.valueOf(getDate()))).getTime();
            return timeMillis;
        } catch (Exception e) {
            return 0L;
        }
    }

    private SimpleDateFormat getSimpleDateFormat() {
        if (this.simpleDateFormat == null) {
            this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        }
        return this.simpleDateFormat;
    }

    private void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }
}