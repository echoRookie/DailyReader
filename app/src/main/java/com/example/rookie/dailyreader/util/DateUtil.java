package com.example.rookie.dailyreader.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rookie on 2017/6/11.
 */

public class DateUtil {
    public static String getDateNow(Integer count){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date nowDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.DAY_OF_YEAR,count);
        Date queryDate = calendar.getTime();
        String date = dateFormat.format(queryDate);
        return date;
    }
}
