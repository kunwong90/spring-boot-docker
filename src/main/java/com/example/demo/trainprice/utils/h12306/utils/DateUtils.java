package com.example.demo.trainprice.utils.h12306.utils;

import com.example.demo.trainprice.utils.h12306.pojo.SimpleDate;

import java.util.Calendar;

public class DateUtils {
    public static Calendar calendar;

    public static int dayOfTomorrow(int day, int afterDays) {
        String dateString = String.valueOf(day);
        int year = Integer.parseInt(dateString.substring(0, 4));
        int month = Integer.parseInt(dateString.substring(4, 6));
        int date = Integer.parseInt(dateString.substring(6, 8));
        SimpleDate simpleDate = dayOfTomorrow(new SimpleDate(year, month, date), afterDays);
        return Integer.parseInt(String.format("%d%02d%02d", simpleDate.getYear(), simpleDate.getMonth(), simpleDate.getDate()));
    }

    public static SimpleDate dayOfTomorrow(SimpleDate simpleDate, int afterDays) {
        if (afterDays != 0) {
            int year = simpleDate.getYear();
            int month = simpleDate.getMonth();
            int date = simpleDate.getDate();
            getCalendar().set(1, year);
            getCalendar().set(2, month - 1);
            getCalendar().set(5, date);
            getCalendar().add(5, afterDays);
            return new SimpleDate(getCalendar().get(1), getCalendar().get(2) + 1, getCalendar().get(5));
        }
        return simpleDate;
    }

    public static Calendar getCalendar() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        return calendar;
    }

    public void setCalendar(Calendar calendar2) {
        calendar = calendar2;
    }
}