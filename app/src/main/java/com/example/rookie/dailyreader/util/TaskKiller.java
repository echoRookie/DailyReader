package com.example.rookie.dailyreader.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rookie on 2017/6/23.
 */

public class TaskKiller {



    public static List<Activity> activityList = new ArrayList<>();



    public static void addActivity(Activity activity){

        activityList.add(activity);

    }



    public static void dropActivity(Activity activity){

        activityList.remove(activity);

    }



    public static void dropAllAcitivty(){

        for (Activity activity : activityList){

            if (!activity.isFinishing()){

                activity.finish();

            }

        }

    }



}