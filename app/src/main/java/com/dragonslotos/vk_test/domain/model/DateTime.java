package com.dragonslotos.vk_test.domain.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateTime {


    @Expose
    @SerializedName("dstActive")
    public boolean dstactive;
    @Expose
    @SerializedName("dayOfWeek")
    public String dayofweek;
    @Expose
    @SerializedName("timeZone")
    public String timezone;
    @Expose
    @SerializedName("time")
    public String time;
    @Expose
    @SerializedName("date")
    public String date;
    @Expose
    @SerializedName("dateTime")
    public String datetime;
    @Expose
    @SerializedName("milliSeconds")
    public int milliseconds;
    @Expose
    @SerializedName("seconds")
    public int seconds;
    @Expose
    @SerializedName("minute")
    public int minute;
    @Expose
    @SerializedName("hour")
    public int hour;
    @Expose
    @SerializedName("day")
    public int day;
    @Expose
    @SerializedName("month")
    public int month;
    @Expose
    @SerializedName("year")
    public int year;
}
