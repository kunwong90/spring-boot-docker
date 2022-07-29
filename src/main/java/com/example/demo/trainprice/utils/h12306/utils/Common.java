package com.example.demo.trainprice.utils.h12306.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {

    public static String toTime(short sTime) {
        int h = sTime / 100;
        int m = sTime % 100;
        return (h < 10 ? "0" + h : Integer.valueOf(h)) + ":" + (m < 10 ? "0" + m : Integer.valueOf(m));
    }

    public static boolean RunToday(int today, int kxzq, int kxgl, int ksrq, int jsrq, int yxts) {
        int ksrqAtThisStation = DateUtils.dayOfTomorrow(ksrq, yxts);
        int jsrqAtThisStation = DateUtils.dayOfTomorrow(jsrq, yxts);
        if (today < ksrqAtThisStation || today > jsrqAtThisStation) {
            return false;
        }
        int daysBetween = daysBetween(Integer.toString(ksrqAtThisStation), Integer.toString(today));
        int dayInKXGL = daysBetween % kxzq;
        String strKXGL = addZeroForNum(Integer.toBinaryString(kxgl), kxzq).reverse().toString();
        return strKXGL.charAt(dayInKXGL) == '1';
    }

    public static int daysBetween(int beginDate, int endDate) {
        return daysBetween(String.valueOf(beginDate), String.valueOf(endDate));
    }

    public static int daysBetween(String beginDate, String endDate) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyyMMdd");
        try {
            Date d1 = sim.parse(beginDate);
            Date d2 = sim.parse(endDate);
            return (int) ((d2.getTime() - d1.getTime()) / 86400000);
        } catch (Exception e) {
            return -1;
        }
    }

    public static StringBuilder addZeroForNum(String str, int strLength) {
        int strLen = strLength - str.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strLen; i++) {
            sb.append('0');
        }
        sb.append(str);
        return sb;
    }


    public static String covert2StringTime(int minitues) {
        int h = minitues / 60;
        int m = minitues % 60;
        return (h < 10 ? "0" + h : Integer.valueOf(h)) + ":" + (m < 10 ? "0" + m : Integer.valueOf(m)) + "";
    }


    public static String readFile(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readFile(fis, "UTF-8");
    }

    public static String readFile(InputStream is, String encode) {
        StringBuffer buffer = new StringBuffer();
        try {
            InputStreamReader reader = new InputStreamReader(is, encode);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (true) {
                String str = bufferedReader.readLine();
                if (str == null) {
                    break;
                }
                buffer.append(str);
                buffer.append("\n");
            }
        } catch (Exception e) {
        }
        return buffer.toString();
    }

    public static int getMinites(int dzTimeIn, int dzTimeOut, int fzTimeOut, int betweenDays) {
        if (dzTimeOut <= 0) {
            dzTimeOut = dzTimeIn;
        }
        if (getMinites(dzTimeOut) < getMinites(dzTimeIn)) {
            betweenDays--;
        }
        return (getMinites(dzTimeIn) + ((betweenDays * 24) * 60)) - getMinites(fzTimeOut);
    }

    public static int getMinites(int intTime) {
        if (intTime >= 100) {
            int h = intTime / 100;
            int m = intTime % 100;
            int zfz = (h * 60) + m;
            return zfz;
        }
        return intTime;
    }


}
