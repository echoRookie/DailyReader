package com.example.rookie.dailyreader.gson;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2017/5/19.
 */

public class DuanziGson {
   @SerializedName("data")
   public List<DataInfo> dataInfos;
   public class DataInfo{
      @SerializedName("group")
      public GroupData groupData;
      public class  GroupData{
         public String text;
         public User user;
         public class User{
            @SerializedName("name")
            public String username;
            @SerializedName("avatar_url")
            public String icon;
         }
      }
   }
}
