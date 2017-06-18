package com.example.rookie.dailyreader.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rookie on 2017/6/11.
 */

public class DateUtil {
    /*知乎日报得到前一天的消息*/
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
    /*将数据库中存储的日期转换成字符串*/
    public static String  getCollctionDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MM.dd HH:mm");
        String nowDate = dateFormat.format(date);
        return nowDate;
    }
}
