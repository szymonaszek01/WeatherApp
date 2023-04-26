package com.app.weather.app.dto;

import com.app.weather.app.model.Current;
import com.app.weather.app.model.Daily;
import com.google.gson.annotations.SerializedName;

public class OpenWeatherDataResponseDto {

    private double lat;

    private double lon;

    private String timezone;

    @SerializedName("timezone_offset")
    private int timezoneOffset;

    private Current current;

    @SerializedName("daily")
    private Daily[] dailyList;

    public OpenWeatherDataResponseDto(double lat, double lon, String timezone, int timezoneOffset, Current current, Daily[] dailyList) {
        this.lat = lat;
        this.lon = lon;
        this.timezone = timezone;
        this.timezoneOffset = timezoneOffset;
        this.current = current;
        this.dailyList = dailyList;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getTimezoneOffset() {
        return timezoneOffset;
    }

    public void setTimezoneOffset(int timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Daily[] getDailyList() {
        return dailyList;
    }

    public void setDailyList(Daily[] dailyList) {
        this.dailyList = dailyList;
    }
}
